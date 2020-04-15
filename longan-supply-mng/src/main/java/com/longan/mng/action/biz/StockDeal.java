package com.longan.mng.action.biz;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.StockService;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
public class StockDeal extends BaseController {
    @Resource
    private StockService stockService;

    @Resource
    private OperationLogService operationLogService;

    @RequestMapping(value = "biz/stockDeal", params = "type=returnToStorage")
    public String returnToStorage(@RequestParam("stockId") Long stockId, @RequestParam("bizId") Long bizId, Model model,
	    HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行退还库存 库存号 : " + stockId);
	Result<Stock> stockResult = stockService.getStockById(stockId);
	if (!stockResult.isSuccess()) {
	    super.alertMsgRedirect(model, stockResult.getResultMsg(), "queryStock.do?bizId=" + bizId + "&id=" + stockId);
	    return "biz/queryStock";
	}
	Stock stock = stockResult.getModule();
	if (stock == null) {
	    super.alertMsgRedirect(model, "没有该库存", "queryStock.do?bizId=" + bizId + "&id=" + stockId);
	    return "biz/queryStock";
	}

	Result<Boolean> result = stockService.returnToStorage(stock);
	if (!result.isSuccess()) {
	    super.alertMsgRedirect(model, result.getResultMsg(), "queryStock.do?bizId=" + bizId + "&id=" + stockId);
	    return "biz/queryStock";
	}

	super.alertSuccess(model, "queryStock.do?bizId=" + bizId + "&id=" + stockId);
	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(stock, stockService.getStockById(stockId).getModule(),
		userInfo, map.get("moduleName") + "(返还)", stock.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return "biz/queryStock";
    }

    @RequestMapping(value = "biz/stockDeal", params = "type=setStorageInvalid")
    public String setStorageInvalid(@RequestParam("stockId") Long stockId, @RequestParam("bizId") Long bizId, Model model,
	    HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	Result<Stock> stockResult = stockService.getStockById(stockId);
	Stock older;
	if (!stockResult.isSuccess()) {
	    super.alertError(model, stockResult.getResultMsg());
	    return "biz/queryStock";
	} else {
	    if (stockResult.getModule() == null) {
		super.alertError(model, "库存为空");
		return "biz/queryStock";
	    }
	    older = stockResult.getModule();
	}

	logger.warn(userInfo.getUserName() + "执行作废库存 库存号 : " + stockId);
	Result<Boolean> result = stockService.setStorageInvalid(stockId);
	if (!result.isSuccess()) {
	    super.alertMsgRedirect(model, result.getResultMsg(), "queryStock.do?bizId=" + bizId + "&id=" + stockId);
	    return "biz/queryStock";
	}

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(older, stockService.getStockById(stockId).getModule(),
		userInfo, map.get("moduleName") + "(作废)", older.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	super.alertSuccess(model, "queryStock.do?bizId=" + bizId + "&id=" + stockId);
	return "biz/queryStock";
    }

    @RequestMapping(value = "biz/stockDeal", params = "type=deliveryFromStorage")
    public String deliveryFromStorage(@RequestParam("itemSupplyId") Long itemSupplyId, @RequestParam("bizId") Long bizId,
	    @RequestParam("count") Integer count, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
	UserInfo userInfo = super.getUserInfo(session);
	final String returnStr = "biz/queryItemSupply";
	if (count <= 0) {
	    super.alertError(model, "出库库存数必须大于0");
	    return returnStr;
	}
	Result<StockLog> createOutStockLogResult = stockService.createOutStockLog(itemSupplyId, count, userInfo.getId());
	if (!createOutStockLogResult.isSuccess()) {
	    super.alertError(model, createOutStockLogResult.getResultMsg());
	    return returnStr;
	}

	StockLog stockLog = createOutStockLogResult.getModule();
	Result<StockLog> deliveryFromStorageByStockLogResult = stockService.deliveryFromStorageByStockLog(stockLog.getId());
	if (!deliveryFromStorageByStockLogResult.isSuccess()) {
	    super.alertError(model, deliveryFromStorageByStockLogResult.getResultMsg());
	    return returnStr;
	}

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(null, stockLog, userInfo, map.get("moduleName") + "(提取)", null,
		map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);

	// super.alertMsgRedirect(model, "操作成功，提取库存数： "
	// + deliveryFromStorageByStockLogResult.getModule().getStockCount(),
	// "queryStockLog.do?bizId=" + bizId + "&id="
	// + deliveryFromStorageByStockLogResult.getModule().getId());

	redirectAttributes.addFlashAttribute("stockLogId", deliveryFromStorageByStockLogResult.getModule().getId());
	return "redirect:stockDownload.do";
    }
}
