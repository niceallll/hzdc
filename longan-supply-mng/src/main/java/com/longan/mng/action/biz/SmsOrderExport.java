package com.longan.mng.action.biz;

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
import com.longan.biz.core.SmsOrderService;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.ExportTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class SmsOrderExport extends BaseController {
    @Resource
    private SmsOrderService smsOrderService;

    @Resource
    private ExcelAsyncService excelAsyncService;

    @Resource
    private ExcelExportService excelExportService;

    @Resource
    private ExportService exportService;

    @RequestMapping(value = "biz/smsOrderExport", method = RequestMethod.POST)
    public ModelAndView smsOrderExport(@ModelAttribute("smsOrderQuery") SmsOrderQuery smsOrderQuery, HttpSession session) {
	// 业务 权限判断
	UserInfo userInfo = getUserInfo(session);
	if (smsOrderQuery.getBizId() != null) {
	    if (!checkBizAuth(smsOrderQuery.getBizId(), userInfo)) {
		return new ModelAndView("error/autherror");
	    }
	}

	if (userInfo.isDownStreamUser()) {
	    smsOrderQuery.setUserId(userInfo.getId());
	}

	Map<String, Object> model = new HashMap<String, Object>();
	Result<Integer> countResult = smsOrderService.getCountInExport(smsOrderQuery);
	if (!countResult.isSuccess()) {
	    logger.error("SmsOrderExport error msg : " + countResult.getResultMsg());
	    model.put("errorMsg", countResult.getResultMsg());
	    return new ModelAndView("error/error", model);
	}

	logger.warn("操作员:" + userInfo.getUserName() + "执行订单导出操作。");
	ModelAndView modelAndView = null;
	if (countResult.getModule() > ONLINE_EXCEL_COUNT) {
	    String fileName = getExcelFileName(userInfo.getId(), smsOrderQuery);
	    int pageCount = ExportTool.getTotalPage(countResult.getModule(), MAX_EXCEL_COUNT);
	    Export export = new Export();
	    export.setUserId(userInfo.getId());
	    export.setExportType(Constants.Export.TYPE_SMS_ORDER);
	    export.setBizId(smsOrderQuery.getBizId());
	    export.setPageCount(pageCount);
	    export.setIsDownStream(userInfo.isDownStreamUser());
	    export.setIsPartner(userInfo.isPartner());
	    export.setFileName(fileName);
	    Result<Export> result = exportService.createExport(export, smsOrderQuery);
	    if (!result.isSuccess()) {
		logger.error("SmsOrderExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    smsOrderQuery.setPageSize(MAX_EXCEL_COUNT);
	    excelAsyncService.smsOrderExport(result.getModule(), smsOrderQuery);
	    model.put("fileName", ExportTool.getFileName(fileName));
	    modelAndView = new ModelAndView("success/excelsucc", model);
	} else {
	    Result<List<SmsOrder>> result = excelExportService.querySmsOrderExport(smsOrderQuery, userInfo.isDownStreamUser());
	    if (!result.isSuccess()) {
		logger.error("SmsOrderExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    model.put("smsOrderList", result.getModule());
	    String fileName = "订单导出";
	    if (smsOrderQuery.getBizId() != null) {
		fileName = Constants.BIZ_MAP.get(smsOrderQuery.getBizId()) + fileName;
	    }
	    model.put("fileName", fileName);
	    model.put("isDownStream", userInfo.isDownStreamUser());
	    modelAndView = new ModelAndView("smsOrderExcel", model);
	}
	return modelAndView;
    }

    @RequestMapping(value = "biz/smsSupplyExport", method = RequestMethod.POST)
    public ModelAndView smsSupplyExport(@ModelAttribute("smsSupplyQuery") SmsSupplyQuery smsSupplyQuery, HttpSession session) {
	// 业务权限判断
	UserInfo userInfo = getUserInfo(session);
	if (userInfo.isDownStreamUser()) {
	    return new ModelAndView("error/autherror");
	}

	if (smsSupplyQuery.getBizId() != null) {
	    if (!checkBizAuth(smsSupplyQuery.getBizId(), userInfo)) {
		return new ModelAndView("error/autherror");
	    }
	}

	Map<String, Object> model = new HashMap<String, Object>();
	Result<Integer> countResult = smsOrderService.getCountInExport(smsSupplyQuery);
	if (!countResult.isSuccess()) {
	    logger.error("SmsSupplyExport error msg : " + countResult.getResultMsg());
	    model.put("errorMsg", countResult.getResultMsg());
	    return new ModelAndView("error/error", model);
	}

	logger.warn("操作员:" + userInfo.getUserName() + "执行供货单导出操作。");
	ModelAndView modelAndView = null;
	if (countResult.getModule() > ONLINE_EXCEL_COUNT) {
	    String fileName = getExcelFileName(userInfo.getId(), smsSupplyQuery);
	    int pageCount = ExportTool.getTotalPage(countResult.getModule(), MAX_EXCEL_COUNT);
	    Export export = new Export();
	    export.setUserId(userInfo.getId());
	    export.setExportType(Constants.Export.TYPE_SMS_SUPPLY);
	    export.setBizId(smsSupplyQuery.getBizId());
	    export.setPageCount(pageCount);
	    export.setIsPartner(userInfo.isPartner());
	    export.setFileName(fileName);
	    Result<Export> result = exportService.createExport(export, smsSupplyQuery);
	    if (!result.isSuccess()) {
		logger.error("SmsSupplyExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    smsSupplyQuery.setPageSize(MAX_EXCEL_COUNT);
	    excelAsyncService.smsSupplyExport(result.getModule(), smsSupplyQuery);
	    model.put("fileName", ExportTool.getFileName(fileName));
	    modelAndView = new ModelAndView("success/excelsucc", model);
	} else {
	    Result<List<SmsSupply>> result = excelExportService.querySmsSupplyExport(smsSupplyQuery);
	    if (!result.isSuccess()) {
		logger.error("SmsSupplyExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    model.put("smsSupplyList", result.getModule());
	    String fileName = "供货单导出";
	    if (smsSupplyQuery.getBizId() != null) {
		fileName = Constants.BIZ_MAP.get(smsSupplyQuery.getBizId()) + fileName;
	    }
	    model.put("fileName", fileName);
	    modelAndView = new ModelAndView("smsSupplyExcel", model);
	}
	return modelAndView;
    }

    private String getExcelFileName(Long userId, SmsOrderQuery smsOrderQuery) {
	String path = ExportTool.getFilePath(userId, smsOrderQuery.getBizId() + "");
	File dir = new File(path);
	if (!dir.exists()) {
	    dir.mkdirs();
	}

	StringBuilder sb = new StringBuilder(path).append("from.");
	sb.append(DateTool.parseDate(smsOrderQuery.getStartGmtCreate())).append(".to.");
	sb.append(DateTool.parseDate(smsOrderQuery.getEndGmtCreate())).append(".");
	sb.append(System.currentTimeMillis()).append(".order.xlsx");
	return sb.toString();
    }

    private String getExcelFileName(Long userId, SmsSupplyQuery smsSupplyQuery) {
	String path = ExportTool.getFilePath(userId, smsSupplyQuery.getBizId() + "");
	File dir = new File(path);
	if (!dir.exists()) {
	    dir.mkdirs();
	}

	StringBuilder sb = new StringBuilder(path).append("from.");
	sb.append(DateTool.parseDate(smsSupplyQuery.getStartGmtCreate())).append(".to.");
	sb.append(DateTool.parseDate(smsSupplyQuery.getEndGmtCreate())).append(".");
	sb.append(System.currentTimeMillis()).append(".supply.xlsx");
	return sb.toString();
    }
}
