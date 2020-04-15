package com.longan.mng.action.biz;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.StockService;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
public class StockLogDeal extends BaseController {
	@Resource
	private StockService stockService;

	@Resource
	private OperationLogService operationLogService;

	@RequestMapping(value = "biz/stockLogDeal", params = "type=activate")
	public String activate(@RequestParam("stockLogId") Long stockLogId,
			@RequestParam("bizId") Long bizId, Model model, HttpSession session) {
		UserInfo userInfo = super.getUserInfo(session);
		Result<StockLog> stockLogResult = stockService.getStockLog(stockLogId);
		StockLog older;
		if (!stockLogResult.isSuccess()) {
			super.alertError(model, stockLogResult.getResultMsg());
			return "biz/queryStockLog";
		} else {
			if (stockLogResult.getModule() == null) {
				super.alertError(model, "库存日志为空");
				return "biz/queryStockLog";
			}
			older = stockLogResult.getModule();
		}
		logger.warn(userInfo.getUserName() + "执行激活库存操作  入库日志编号 : " + stockLogId);

		Result<Integer> result = stockService.activateStorageByStockLog(stockLogId);

		if (!result.isSuccess()) {
			super.alertMsgRedirect(model, result.getResultMsg(), "queryStockLog.do?bizId=" + bizId
					+ "&id=" + stockLogId);
			return "biz/queryStockLog";
		}
		@SuppressWarnings("unchecked")
		Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
		OperationLog operationLog = OperLogUtils.operationLogDeal(older,
				stockService.getStockLog(stockLogId).getModule(), userInfo, map.get("moduleName")
						+ "(激活)", older.getBizId(), map.get("loginIp"));
		operationLogService.createOperationLog(operationLog);
		super.alertMsgRedirect(model, "操作成功 激活库存数： " + result.getModule(),
				"queryStockLog.do?bizId=" + bizId + "&id=" + stockLogId);
		return "biz/queryStockLog";
	}

	@RequestMapping(value = "biz/stockLogDeal", params = "type=invalid")
	public String invalid(@RequestParam("stockLogId") Long stockLogId,
			@RequestParam("bizId") Long bizId, Model model, HttpSession session) {
		UserInfo userInfo = super.getUserInfo(session);
		Result<StockLog> stockLogResult = stockService.getStockLog(stockLogId);
		StockLog older;
		if (!stockLogResult.isSuccess()) {
			super.alertError(model, stockLogResult.getResultMsg());
			return "biz/queryStockLog";
		} else {
			if (stockLogResult.getModule() == null) {
				super.alertError(model, "库存日志为空");
				return "biz/queryStockLog";
			}
			older = stockLogResult.getModule();
		}
		logger.warn(userInfo.getUserName() + "执行作废库存操作  入库编号 : " + stockLogId);

		Result<Integer> result = stockService.invalidStorageByStockLog(stockLogId);
		if (!result.isSuccess()) {
			super.alertMsgRedirect(model, result.getResultMsg(), "queryStockLog.do?bizId=" + bizId
					+ "&id=" + stockLogId);
			return "biz/queryStockLog";
		}
		@SuppressWarnings("unchecked")
		Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
		OperationLog operationLog = OperLogUtils.operationLogDeal(older,
				stockService.getStockLog(stockLogId).getModule(), userInfo, map.get("moduleName")
						+ "(作废)", older.getBizId(), map.get("loginIp"));
		operationLogService.createOperationLog(operationLog);
		super.alertMsgRedirect(model, "操作成功    作废库存数： " + result.getModule(),
				"queryStockLog.do?bizId=" + bizId + "&id=" + stockLogId);
		return "biz/queryStockLog";
	}
}
