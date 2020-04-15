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
import com.longan.biz.core.ExcelAsyncService;
import com.longan.biz.core.ExcelExportService;
import com.longan.biz.core.ExportService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.ExportTool;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("biz/supplyOrderExport")
public class SupplyOrderExport extends BaseController {
    @Resource
    private ExcelExportService excelExportService;

    @Resource
    private ExcelAsyncService excelAsyncService;

    @Resource
    private LocalCachedService localCachedService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private ExportService exportService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onRequest(@ModelAttribute("supplyOrderQuery") SupplyOrderQuery supplyOrderQuery, HttpSession session) {
	// 业务权限判断
	UserInfo userInfo = super.getUserInfo(session);
	if (supplyOrderQuery.getBizId() != null) {
	    if (!checkBizAuth(supplyOrderQuery.getBizId(), userInfo)) {
		return new ModelAndView("error/autherror");
	    }
	}

	if (userInfo.isDownStreamUser()) {
	    supplyOrderQuery.setUserId(userInfo.getId());
	}

	Map<String, Object> model = new HashMap<String, Object>();
	Result<Integer> countResult = supplyOrderService.getCountInExport(supplyOrderQuery);
	if (!countResult.isSuccess()) {
	    logger.error("SupplyOrderExport error msg : " + countResult.getResultMsg());
	    model.put("errorMsg", countResult.getResultMsg());
	    return new ModelAndView("error/error", model);
	}

	logger.warn("操作员:" + userInfo.getUserName() + "执行供货单导出操作。");
	ModelAndView modelAndView = null;
	if (countResult.getModule() > ONLINE_EXCEL_COUNT) {
	    String fileName = getExcelFileName(userInfo.getId(), supplyOrderQuery);
	    int pageCount = ExportTool.getTotalPage(countResult.getModule(), MAX_EXCEL_COUNT);
	    Export export = new Export();
	    export.setUserId(userInfo.getId());
	    export.setExportType(Constants.Export.TYPE_SUPPLY_ORDER);
	    export.setBizId(supplyOrderQuery.getBizId());
	    export.setPageCount(pageCount);
	    export.setIsPartner(userInfo.isPartner());
	    export.setFileName(fileName);
	    Result<Export> result = exportService.createExport(export, supplyOrderQuery);
	    if (!result.isSuccess()) {
		logger.error("SupplyOrderExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }

	    supplyOrderQuery.setPageSize(MAX_EXCEL_COUNT);
	    excelAsyncService.supplyOrderExport(result.getModule(), supplyOrderQuery);
	    model.put("fileName", ExportTool.getFileName(fileName));
	    modelAndView = new ModelAndView("success/excelsucc", model);
	} else {
	    Result<List<SupplyOrder>> result = excelExportService.querySupplyOrderExport(supplyOrderQuery);
	    if (!result.isSuccess()) {
		logger.error("SupplyOrderExport error msg : " + result.getResultMsg());
		model.put("errorMsg", result.getResultMsg());
		return new ModelAndView("error/error", model);
	    }
	    setUidArea(result.getModule());
	    model.put("supplyOrderList", result.getModule());
	    String fileName = "供货单导出";
	    if (supplyOrderQuery.getBizId() != null) {
		fileName = Constants.BIZ_MAP.get(supplyOrderQuery.getBizId()) + fileName;
	    }
	    model.put("fileName", fileName);
	    model.put("isPartner", userInfo.isPartner());
	    modelAndView = new ModelAndView("supplyOrderExcel", model);
	}

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(null, null, userInfo, map.get("moduleName"),
		supplyOrderQuery.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return modelAndView;
    }

    private String getExcelFileName(Long userId, SupplyOrderQuery supplyOrderQuery) {
	String path = ExportTool.getFilePath(userId, supplyOrderQuery.getBizId() + "");
	File dir = new File(path);
	if (!dir.exists()) {
	    dir.mkdirs();
	}

	StringBuilder sb = new StringBuilder(path).append("from.");
	sb.append(DateTool.parseDate(supplyOrderQuery.getStartGmtCreate())).append(".to.");
	sb.append(DateTool.parseDate(supplyOrderQuery.getEndGmtCreate())).append(".");
	sb.append(System.currentTimeMillis()).append(".supply.xlsx");
	return sb.toString();
    }

    private void setUidArea(List<SupplyOrder> supplyOrderList) {
	for (SupplyOrder supplyOrder : supplyOrderList) {
	    if (StringUtils.isNotBlank(supplyOrder.getProvinceCode())) {
		AreaInfo areaInfo = localCachedService.getProvinceByCode(supplyOrder.getProvinceCode());
		if (areaInfo != null) {
		    supplyOrder.setUidAreaInfo(areaInfo.getProvinceName());
		}
	    }
	}
    }
}
