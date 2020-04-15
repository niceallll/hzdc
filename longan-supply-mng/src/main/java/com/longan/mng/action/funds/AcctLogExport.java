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

import com.longan.biz.core.AcctLogService;
import com.longan.biz.core.ExcelAsyncService;
import com.longan.biz.core.ExcelExportService;
import com.longan.biz.core.ExportService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.ExportTool;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("funds/acctLogExport")
public class AcctLogExport extends BaseController {
    @Resource
    private ExcelExportService excelExportService;

    @Resource
    private ExcelAsyncService excelAsyncService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private AcctLogService acctLogService;

    @Resource
    private ExportService exportService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onRequest(@ModelAttribute("acctLogQuery") AcctLogQuery acctLogQuery, HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	if (userInfo.isDownStreamUser()) {
	    acctLogQuery.setUserId(userInfo.getId());
	}

	Map<String, Object> model = new HashMap<String, Object>();
	Result<Integer> countResult = acctLogService.getCountInExport(acctLogQuery);
	if (!countResult.isSuccess()) {
	    logger.error("SupplyOrderExport error msg : " + countResult.getResultMsg());
	    model.put("errorMsg", countResult.getResultMsg());
	    return new ModelAndView("error/error", model);
	}

	logger.warn("操作员:" + userInfo.getUserName() + "执行账户资金流水导出操作。");
	ModelAndView modelAndView = null;
	if (countResult.getModule() > ONLINE_EXCEL_COUNT) {
	    String fileName = getExcelFileName(userInfo.getId(), acctLogQuery);
	    int pageCount = ExportTool.getTotalPage(countResult.getModule(), MAX_EXCEL_COUNT);
	    Export export = new Export();
	    export.setUserId(userInfo.getId());
	    export.setExportType(Constants.Export.TYPE_ACCT_LOG);
	    export.setBizId(Constants.BizInfo.CODE_ACCT_LOG);
	    export.setPageCount(pageCount);
	    export.setIsDownStream(userInfo.isDownStreamUser());
	    export.setIsPartner(userInfo.isPartner());
	    export.setFileName(fileName);
	    Result<Export> result = exportService.createExport(export, acctLogQuery);
	    if (!result.isSuccess()) {
		logger.error("AcctLogExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    acctLogQuery.setPageSize(MAX_EXCEL_COUNT);
	    excelAsyncService.acctLogExport(result.getModule(), acctLogQuery);
	    model.put("fileName", ExportTool.getFileName(fileName));
	    modelAndView = new ModelAndView("success/excelsucc", model);
	} else {
	    Result<List<AcctLog>> result = excelExportService.queryAcctLogExport(acctLogQuery, userInfo.isDownStreamUser());
	    if (!result.isSuccess()) {
		logger.error("AcctLogExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }
	    model.put("acctLogList", result.getModule());
	    model.put("fileName", "账户资金流水导出");
	    model.put("isDownStream", userInfo.isDownStreamUser());
	    model.put("isPartner", userInfo.isPartner());
	    modelAndView = new ModelAndView("acctLogExcel", model);
	}

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(null, null, userInfo, map.get("moduleName"), null,
		map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return modelAndView;
    }

    private String getExcelFileName(Long userId, AcctLogQuery acctLogQuery) {
	String path = ExportTool.getFilePath(userId, Constants.BizInfo.CODE_ACCT_LOG + "");
	File dir = new File(path);
	if (!dir.exists()) {
	    dir.mkdirs();
	}

	StringBuilder sb = new StringBuilder(path).append("from.");
	sb.append(DateTool.parseDate(acctLogQuery.getStartGmtCreate())).append(".to.");
	sb.append(DateTool.parseDate(acctLogQuery.getEndGmtCreate())).append(".");
	sb.append(System.currentTimeMillis()).append(".account.xlsx");
	return sb.toString();
    }
}
