package com.longan.mng.action.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.longan.biz.core.ItemService;
import com.longan.biz.core.StockService;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.InStockForm;
import com.longan.mng.utils.Constants;

@Controller
@RequestMapping("biz/inStock")
public class InStock extends BaseController {
    @Resource
    private StockService stockService;

    @Resource
    private Validator validator;

    @Resource
    private ItemService itemService;

    @Resource
    private CommonsMultipartResolver multipartResolver;

    private final static String SPLIT = ","; // 分割符

    @RequestMapping(method = RequestMethod.GET)
    public String index(@ModelAttribute("inStockForm") InStockForm inStockForm, @RequestParam("bizId") Integer bizId,
	    Model model, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	if (!checkBizAuth(bizId, userInfo)) {
	    return "error/autherror";
	}

	inStockForm.setBizId(bizId.toString());
	setSelectValue(model, bizId);
	return "biz/inStock";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onPost(HttpServletRequest request, HttpSession session, @ModelAttribute("inStockForm") InStockForm inStockForm,
	    BindingResult bindingResult, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	if (!checkBizAuth(Integer.parseInt(inStockForm.getBizId()), userInfo)) {
	    return "error/autherror";
	}

	setSelectValue(model, Integer.parseInt(inStockForm.getBizId()));
	validator.validate(inStockForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return "biz/inStock";
	}
	if (!checkFile(request, multipartResolver)) {
	    alertError(model, "上传文件不能是空");
	    return "biz/inStock";
	}

	Result<StockLog> stockLogResult = stockService.createInStockLog(Long.parseLong(inStockForm.getItemSupplyId()),
		Integer.parseInt(inStockForm.getItemCostPrice()), Integer.parseInt(inStockForm.getBizId()), userInfo.getId());
	if (!stockLogResult.isSuccess()) {
	    alertError(model, stockLogResult.getResultMsg());
	    return "biz/inStock";
	}

	StockLog stockLog = stockLogResult.getModule();
	// upload
	File file = uploadFile(request, stockLog);
	if (file == null) {
	    logger.error("上传文件为空 stockLogId : " + stockLog.getId());
	    alertError(model, "上传文件不能是空");
	    StockLog stockLogUpdate = new StockLog();
	    stockLogUpdate.setId(stockLog.getId());
	    stockLogUpdate.setStatus(com.longan.biz.utils.Constants.StockLog.STATUS_FAILED);
	    stockService.updateStockLog(stockLogUpdate);
	    return "biz/inStock";
	}

	// 卡券信息
	stockLog.setCardFinalDate(inStockForm.getCardFinalDate());
	stockLog.setCardNote(inStockForm.getCardNote());

	// parse
	List<Stock> stockList = null;
	try {
	    stockList = parseFile(file, stockLog);
	} catch (Exception e) {
	    logger.error("解析文件失败 stockLogId : " + stockLog.getId(), e);
	    alertError(model, "解析文件失败  msg : " + e.getMessage());
	    StockLog stockLogUpdate = new StockLog();
	    stockLogUpdate.setId(stockLog.getId());
	    stockLogUpdate.setStatus(com.longan.biz.utils.Constants.StockLog.STATUS_FAILED);
	    stockService.updateStockLog(stockLogUpdate);
	    return "biz/inStock";
	}

	// in storage
	Result<Boolean> putInStorageResult = stockService.putInStorage(stockList, stockLog);
	if (!putInStorageResult.isSuccess()) {
	    alertError(model, putInStorageResult.getResultMsg());
	    return "biz/inStock";
	}

	alertSuccess(model, "queryStockLog.do?bizId=" + inStockForm.getBizId() + "&id=" + stockLog.getId());
	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(null, stockLog, userInfo, map.get("moduleName"),
		stockLog.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return "biz/inStock";
    }

    private File uploadFile(HttpServletRequest request, StockLog stockLog) {
	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	File file = null;
	if (multipartResolver.isMultipart(multipartRequest)) {// 判断request是否有文件上传
	    // srcfname是指文件上传标签的name=值
	    MultiValueMap<String, MultipartFile> multfiles = multipartRequest.getMultiFileMap();
	    for (String srcfname : multfiles.keySet()) {
		MultipartFile mfile = multfiles.getFirst(srcfname);
		try {
		    file = new File(Constants.UPLOAD_PATH + stockLog.getId() + ".txt");
		    if (mfile.isEmpty()) {
			return null;
		    }
		    mfile.transferTo(file);
		} catch (IllegalStateException e) {
		    logger.error("uploadFile error stockLogId:" + stockLog.getId(), e);
		    return null;
		} catch (IOException e) {
		    logger.error("uploadFile error stockLogId:" + stockLog.getId(), e);
		    return null;
		}
	    }
	} else {
	    logger.error("uploadFile error file is null");
	    return null;
	}
	return file;
    }

    private List<Stock> parseFile(File file, StockLog stockLog) throws Exception {
	List<Stock> result = new ArrayList<Stock>();
	BufferedReader br = null;
	Item item = localCachedService.getItem(stockLog.getItemId());
	if (item == null) {
	    logger.error("parse upload File . itemInfo is null ");
	    return result;
	}

	try {
	    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
	    String str = null;
	    while ((str = br.readLine()) != null && !("").equals(str)) {
		String[] strArray = str.split(SPLIT);
		Stock stock = new Stock();
		stock.setCardSerialNo(strArray[0].trim());
		stock.setCardId(strArray[1].trim());
		stock.setCardPwd(strArray[2].trim());
		// 积分兑换券、有效期、范围
		if (strArray.length > 3) {
		    stock.setCardFinalDate(strArray[3]);
		    stock.setCardNote(strArray[4]);
		} else {
		    stock.setCardFinalDate(stockLog.getCardFinalDate());
		    stock.setCardNote(stockLog.getCardNote());
		}
		stock.setInSerialno(stockLog.getId());
		stock.setItemSupplyId(stockLog.getItemSupplyId());
		stock.setItemId(item.getId());
		stock.setItemName(item.getItemName());
		stock.setItemFacePrice(item.getItemFacePrice());
		stock.setSupplyTraderId(stockLog.getSupplyTraderId());
		stock.setBizId(stockLog.getBizId());
		stock.setItemCostPrice(stockLog.getItemCostPrice());
		stock.setStatus(com.longan.biz.utils.Constants.Stock.STATUS_INIT);
		result.add(stock);
	    }
	} catch (FileNotFoundException e) {
	    logger.error("parse upload File error", e);
	    return result;
	} catch (IOException e) {
	    logger.error("parse upload File error", e);
	    return result;
	} finally {
	    try {
		if (br != null) {
		    br.close();
		}
	    } catch (IOException e) {
		logger.error("io close error ", e);
		return result;
	    }
	}
	return result;
    }

    private void setSelectValue(Model model, Integer bizId) {
	Result<List<ItemSupply>> itemSupplyResult = itemService.queryStockItemSupplyByBiz(bizId);
	if (itemSupplyResult.isSuccess() && itemSupplyResult.getModule() != null) {
	    Map<Long, String> map = new HashMap<Long, String>();
	    for (ItemSupply itemSupply : itemSupplyResult.getModule()) {
		Item item = localCachedService.getItem(itemSupply.getItemId());
		if (item == null) {
		    continue;
		}
		UserInfo supplyTraderInfo = localCachedService.getUserInfo(itemSupply.getSupplyTraderId());
		map.put(itemSupply.getId(), item.getItemName() + "(" + supplyTraderInfo.getUserName() + ")");
	    }
	    model.addAttribute("itemSupplyList", map);
	}
    }
}
