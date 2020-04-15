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
import com.longan.biz.core.OperationLogService;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("biz/stockDownload")
public class StockDownload extends BaseController {
    @Resource
    private ExcelExportService excelExportService;

    @Resource
    private OperationLogService operationLogService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView onRequest(@ModelAttribute("stockLogId") Long stockLogId, HttpSession session) {
	// 业务 权限判断
	UserInfo userInfo = super.getUserInfo(session);
	logger.warn("操作员:" + userInfo.getUserName() + "执行库存下载操作。");
	Result<List<Stock>> result = excelExportService.queryStockListByStockLogId(stockLogId, userInfo);

	Map<String, Object> model = new HashMap<String, Object>();
	if (!result.isSuccess()) {
	    logger.error("queryStockListByStockLogId error msg : " + result.getResultMsg());
	    model.put("errorMsg", result.getResultMsg());
	    return new ModelAndView("error/error", model);
	}

	model.put("stockList", result.getModule());
	String fileName = "库存导出";
	fileName = fileName + stockLogId;
	model.put("fileName", fileName);
	ModelAndView modelAndView = new ModelAndView("stockDownload", model);

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal("stockLogId : " + stockLogId, userInfo, map.get("moduleName"),
		null, map.get("loginIp"), Constants.OperationLog.TYPE_OTHER);

	operationLogService.createOperationLog(operationLog);
	return modelAndView;
    }
}
