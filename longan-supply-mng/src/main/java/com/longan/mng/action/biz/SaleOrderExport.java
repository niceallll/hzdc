package com.longan.mng.action.biz;

import java.text.ParseException;
import java.util.Date;
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

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.core.ExcelExportService;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("biz/saleOrderExport")
public class SaleOrderExport extends BaseController {
    @Resource
    private ExcelExportService excelExportService;

    @Resource
    private LocalCachedService localCachedService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onRequest(@ModelAttribute("supplyOrderQuery") SupplyOrderQuery supplyOrderQuery, HttpSession session) {
	Map<String, Object> model = new HashMap<String, Object>();
	if (supplyOrderQuery.getSupplyTraderId() == null) {
	    model.put("errorMsg", "此操作必需选择一个上游供货商");
	    return new ModelAndView("error/error", model);
	}
	if (supplyOrderQuery.getItemId() == null) {
	    model.put("errorMsg", "此操作必需选择一个商品");
	    return new ModelAndView("error/error", model);
	}
	TraderInfo traderInfo = localCachedService.getTraderInfo(supplyOrderQuery.getSupplyTraderId());
	if (traderInfo.getSupplyType() != Constants.TraderInfo.TYPE_MAN) {
	    model.put("errorMsg", "此操作只能选择人工充值类型的上游供货商");
	    return new ModelAndView("error/error", model);
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (supplyOrderQuery.getStartGmtCreate() == null) {
	    supplyOrderQuery.setStartGmtCreate(date);
	}
	if (supplyOrderQuery.getEndGmtCreate() == null) {
	    supplyOrderQuery.setEndGmtCreate(date);
	}

	UserInfo userInfo = getUserInfo(session);
	logger.warn("操作员:" + userInfo.getUserName() + "执行红包导出操作。");
	Result<List<SupplyOrder>> result = excelExportService.querySaleOrderExport(supplyOrderQuery);
	if (!result.isSuccess()) {
	    logger.error("SaleOrderExport error msg : " + result.getResultMsg());
	    model.put("errorMsg", result.getResultMsg());
	    return new ModelAndView("error/error", model);
	}
	model.put("saleOrderList", result.getModule());
	String fileName = "红包导出";
	model.put("fileName", fileName);
	ModelAndView modelAndView = new ModelAndView("saleOrderExcel", model);

	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(null, null, userInfo, map.get("moduleName"),
		supplyOrderQuery.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return modelAndView;
    }
}
