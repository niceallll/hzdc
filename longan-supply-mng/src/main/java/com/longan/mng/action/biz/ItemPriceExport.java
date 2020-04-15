package com.longan.mng.action.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.longan.biz.core.ExcelExportService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.ItemPrice;
import com.longan.biz.dataobject.ItemPriceQuery;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("biz/itemPriceExport")
public class ItemPriceExport extends BaseController {
    @Resource
    private ExcelExportService excelExportService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onRequest(@ModelAttribute("itemPriceQuery") ItemPriceQuery itemPriceQuery, HttpSession session) {
	// 业务 权限判断
	UserInfo userInfo = super.getUserInfo(session);
	// 业务权限判断
	if (itemPriceQuery.getBizId() != null) {
	    if (!checkBizAuth(itemPriceQuery.getBizId(), userInfo)) {
		return new ModelAndView("error/autherror");
	    }
	}
	logger.warn("操作员:" + userInfo.getUserName() + "执行商品价格导出操作。");
	Result<List<ItemPrice>> result = excelExportService.queryItemPriceExport(itemPriceQuery);
	Map<String, Object> model = new HashMap<String, Object>();
	if (!result.isSuccess()) {
	    logger.error("ItemPriceExport error msg : " + result.getResultMsg());
	    model.put("errorMsg", result.getResultMsg());
	    return new ModelAndView("error/error", model);
	}
	List<ItemPrice> itemPriceList = result.getModule();
	if (itemPriceList != null) {
	    for (ItemPrice itemPrice : itemPriceList) {
		UserInfo supplyTrader = localCachedService.getUserInfo(itemPrice.getSupplyTraderId());
		if (supplyTrader != null) {
		    itemPrice.setSupplyTraderName(supplyTrader.getUserName());
		}
		if (itemPrice.isNationwide()) {
		    itemPrice.setItemSalesAreaDesc(Constants.Item.SALE_TYPE_NATIONWIDE_DESC);
		} else {
		    StringBuffer sb = new StringBuffer("");
		    for (String areaCode : itemPrice.getSalesAreaList()) {
			AreaInfo areaInfo = localCachedService.getProvinceByCode(areaCode);
			if (areaInfo != null) {
			    sb.append(areaInfo.getProvinceName()).append("、");
			}
		    }
		    itemPrice.setItemSalesAreaDesc(sb.toString().substring(0, sb.toString().length() - 1));
		}
	    }
	}
	model.put("itemPriceList", itemPriceList);
	String fileName = "商品价格导出";
	if (itemPriceQuery.getBizId() != null) {
	    fileName = Constants.BIZ_MAP.get(itemPriceQuery.getBizId()) + fileName;
	}
	model.put("fileName", fileName);
	ModelAndView modelAndView = new ModelAndView("itemPriceExcel", model);

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(null, null, userInfo, map.get("moduleName"), null,
		map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return modelAndView;
    }
}
