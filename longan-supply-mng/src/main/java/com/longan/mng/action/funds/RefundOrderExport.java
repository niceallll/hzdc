package com.longan.mng.action.funds;

import java.io.File;
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

import com.longan.biz.core.ExcelAsyncService;
import com.longan.biz.core.ExcelExportService;
import com.longan.biz.core.ExportService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.RefundOrderService;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.ExportTool;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("funds/refundOrderExport")
public class RefundOrderExport extends BaseController {
    @Resource
    private ExcelExportService excelExportService;

    @Resource
    private ExcelAsyncService excelAsyncService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private RefundOrderService refundOrderService;

    @Resource
    private ExportService exportService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onRequest(@ModelAttribute("refundOrderQuery") RefundOrderQuery refundOrderQuery, HttpSession session) {
	// 业务 权限判断
	UserInfo userInfo = super.getUserInfo(session);
	if (userInfo.isDownStreamUser()) {
	    refundOrderQuery.setUserId(userInfo.getId());
	}

	Map<String, Object> model = new HashMap<String, Object>();
	Result<Integer> countResult = refundOrderService.getCountInExport(refundOrderQuery);
	if (!countResult.isSuccess()) {
	    logger.error("RefundOrderExport error msg : " + countResult.getResultMsg());
	    model.put("errorMsg", countResult.getResultMsg());
	    return new ModelAndView("error/error", model);
	}

	logger.warn("操作员:" + userInfo.getUserName() + "执行订单导出操作。");
	ModelAndView modelAndView = null;
	if (countResult.getModule() > ONLINE_EXCEL_COUNT) {
	    String fileName = getExcelFileName(userInfo.getId(), refundOrderQuery);
	    int pageCount = ExportTool.getTotalPage(countResult.getModule(), MAX_EXCEL_COUNT);
	    Export export = new Export();
	    export.setUserId(userInfo.getId());
	    export.setExportType(Constants.Export.TYPE_REFUND_ORDER);
	    export.setBizId(Constants.BizInfo.CODE_REFUND_ORDER);
	    export.setPageCount(pageCount);
	    export.setIsDownStream(userInfo.isDownStreamUser());
	    export.setFileName(fileName);
	    Result<Export> result = exportService.createExport(export, refundOrderQuery);
	    if (!result.isSuccess()) {
		logger.error("RefundOrderExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    refundOrderQuery.setPageSize(MAX_EXCEL_COUNT);
	    excelAsyncService.refundOrderExport(result.getModule(), refundOrderQuery);
	    model.put("fileName", ExportTool.getFileName(fileName));
	    modelAndView = new ModelAndView("success/excelsucc", model);
	} else {
	    Result<List<RefundOrder>> result = excelExportService.queryRefundOrderExport(refundOrderQuery,
		    userInfo.isDownStreamUser());
	    if (!result.isSuccess()) {
		logger.error("RefundOrderExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }
	    model.put("refundOrderList", result.getModule());
	    model.put("fileName", "退款单导出");
	    model.put("isDownStream", userInfo.isDownStreamUser());
	    modelAndView = new ModelAndView("refundOrderExcel", model);
	}

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(null, null, userInfo, map.get("moduleName"), null,
		map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return modelAndView;
    }

    private String getExcelFileName(Long userId, RefundOrderQuery refundOrderQuery) {
	String path = ExportTool.getFilePath(userId, Constants.BizInfo.CODE_REFUND_ORDER + "");
	File dir = new File(path);
	if (!dir.exists()) {
	    dir.mkdirs();
	}

	StringBuilder sb = new StringBuilder(path).append("from.");
	sb.append(DateTool.parseDate(refundOrderQuery.getStartGmtCreate())).append(".to.");
	sb.append(DateTool.parseDate(refundOrderQuery.getEndGmtCreate())).append(".");
	sb.append(System.currentTimeMillis()).append(".refund.xlsx");
	return sb.toString();
    }
}
