package com.longan.mng.action.biz;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.ExcelAsyncService;
import com.longan.biz.core.ExcelExportService;
import com.longan.biz.core.ExportService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;
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
@RequestMapping("biz/bizOrderExport")
public class BizOrderExport extends BaseController {
    @Resource
    private ExcelExportService excelExportService;

    @Resource
    private ExcelAsyncService excelAsyncService;

    @Resource
    private LocalCachedService localCachedService;

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private ExportService exportService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onRequest(@ModelAttribute("bizOrderQuery") BizOrderQuery bizOrderQuery, HttpSession session) {
	// 业务 权限判断
	UserInfo userInfo = super.getUserInfo(session);
	if (bizOrderQuery.getBizId() != null) {
	    if (!checkBizAuth(bizOrderQuery.getBizId(), userInfo)) {
		return new ModelAndView("error/autherror");
	    }
	}
	bizOrderQuery = DateTool.dateFilter(bizOrderQuery);
	if (userInfo.isDownStreamUser()) {
	    bizOrderQuery.setUserId(userInfo.getId());
	}

	Map<String, Object> model = new HashMap<String, Object>();
	//统计记录
	Result<Integer> countResult = bizOrderService.getCountInExport(bizOrderQuery);

	if (!countResult.isSuccess()) {
	    logger.error("BizOrderExport error msg : " + countResult.getResultMsg());
	    model.put("errorMsg", countResult.getResultMsg());
	    return new ModelAndView("error/error", model);
	}

	logger.warn("操作员:" + userInfo.getUserName() + "执行订单导出操作。");
	ModelAndView modelAndView = null;
	if (countResult.getModule() > ONLINE_EXCEL_COUNT) {
	    String fileName = getExcelFileName(userInfo.getId(), bizOrderQuery);
	    int pageCount = ExportTool.getTotalPage(countResult.getModule(), MAX_EXCEL_COUNT);
	    Export export = new Export();
	    export.setUserId(userInfo.getId());
	    export.setExportType(Constants.Export.TYPE_BIZ_ORDER);
	    export.setBizId(bizOrderQuery.getBizId());
	    export.setPageCount(pageCount);
	    export.setIsDownStream(userInfo.isDownStreamUser());
	    export.setIsPartner(userInfo.isPartner());
	    export.setFileName(fileName);
	    Result<Export> result = exportService.createExport(export, bizOrderQuery);
	    if (!result.isSuccess()) {
		logger.error("BizOrderExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    bizOrderQuery.setPageSize(MAX_EXCEL_COUNT);
	    excelAsyncService.bizOrderExport(result.getModule(), bizOrderQuery);
	    model.put("fileName", ExportTool.getFileName(fileName));
	    modelAndView = new ModelAndView("success/excelsucc", model);
	} else {
	    Result<List<BizOrder>> result = excelExportService.queryBizOrderExport(bizOrderQuery, userInfo.isDownStreamUser());
	    if (!result.isSuccess()) {
		logger.error("BizOrderExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    setUidArea(result.getModule());
	    model.put("bizOrderList", result.getModule());
	    String fileName = "订单导出";
	    if (bizOrderQuery.getBizId() != null) {
		fileName = Constants.BIZ_MAP.get(bizOrderQuery.getBizId()) + fileName;
	    }
	    model.put("fileName", fileName);
	    model.put("isDownStream", userInfo.isDownStreamUser());
	    model.put("isPartner", userInfo.isPartner());
	    modelAndView = new ModelAndView("bizOrderExcel", model);
	}

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(null, null, userInfo, map.get("moduleName"),
		bizOrderQuery.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return modelAndView;
    }

    private String getExcelFileName(Long userId, BizOrderQuery bizOrderQuery) {
	String path = ExportTool.getFilePath(userId, bizOrderQuery.getBizId() + "");
	File dir = new File(path);
	if (!dir.exists()) {
	    dir.mkdirs();
	}

	StringBuilder sb = new StringBuilder(path).append("from.");
	sb.append(DateTool.parseDate(bizOrderQuery.getStartGmtCreate())).append(".to.");
	sb.append(DateTool.parseDate(bizOrderQuery.getEndGmtCreate())).append(".");
	sb.append(System.currentTimeMillis()).append(".order.xlsx");
	return sb.toString();
    }

    private void setUidArea(List<BizOrder> bizOrderList) {
	for (BizOrder bizOrder : bizOrderList) {
	    if (StringUtils.isNotBlank(bizOrder.getProvinceCode())) {
		AreaInfo areaInfo = localCachedService.getProvinceByCode(bizOrder.getProvinceCode());
		if (areaInfo != null) {
		    bizOrder.setUidAreaInfo(areaInfo.getProvinceName());
		}
	    }
	}
    }
}
