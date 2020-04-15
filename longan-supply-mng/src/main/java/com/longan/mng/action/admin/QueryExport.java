package com.longan.mng.action.admin;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longan.biz.core.QueryService;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.ExportQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.mng.action.common.BaseController;

@Controller
public class QueryExport extends BaseController {
    @Resource
    private QueryService queryService;

    @RequestMapping("biz/queryBizOrderExport")
    public String bizOrderExport(@ModelAttribute("exportQuery") ExportQuery exportQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(exportQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (exportQuery.getEndGmtCreate() == null) {
	    exportQuery.setEndGmtCreate(date);
	}
	if (exportQuery.getStartGmtCreate() == null) {
	    exportQuery.setStartGmtCreate(date);
	}

	exportQuery.setUserId(userInfo.getId());
	exportQuery.setExportType(Constants.Export.TYPE_BIZ_ORDER);
	model.addAttribute("userInfo", userInfo);
	setSelectValue(userInfo, model, exportQuery);
	Result<List<Export>> result = queryService.queryExport(exportQuery);
	if (result.isSuccess()) {
	    model.addAttribute("exportList", result.getModule());
	} else {
	    setError(model, result.getResultMsg());
	}
	return "biz/queryBizOrderExport";
    }

    @RequestMapping("biz/querySupplyOrderExport")
    public String supplyOrderExport(@ModelAttribute("exportQuery") ExportQuery exportQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	// 业务权限判断
	if (!checkBizAuth(exportQuery.getBizId(), userInfo)) {
	    return "error/autherror";
	}

	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (exportQuery.getEndGmtCreate() == null) {
	    exportQuery.setEndGmtCreate(date);
	}
	if (exportQuery.getStartGmtCreate() == null) {
	    exportQuery.setStartGmtCreate(date);
	}

	exportQuery.setUserId(userInfo.getId());
	exportQuery.setExportType(Constants.Export.TYPE_SUPPLY_ORDER);
	model.addAttribute("userInfo", userInfo);
	setSelectValue(userInfo, model, exportQuery);
	Result<List<Export>> result = queryService.queryExport(exportQuery);
	if (result.isSuccess()) {
	    model.addAttribute("exportList", result.getModule());
	} else {
	    setError(model, result.getResultMsg());
	}
	return "biz/querySupplyOrderExport";
    }

    @RequestMapping("funds/queryRefundOrderExport")
    public String refundOrderExport(@ModelAttribute("exportQuery") ExportQuery exportQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (exportQuery.getEndGmtCreate() == null) {
	    exportQuery.setEndGmtCreate(date);
	}
	if (exportQuery.getStartGmtCreate() == null) {
	    exportQuery.setStartGmtCreate(date);
	}

	exportQuery.setUserId(userInfo.getId());
	exportQuery.setExportType(Constants.Export.TYPE_REFUND_ORDER);
	exportQuery.setBizId(500);
	model.addAttribute("userInfo", userInfo);
	setSelectValue(userInfo, model, exportQuery);
	Result<List<Export>> result = queryService.queryExport(exportQuery);
	if (result.isSuccess()) {
	    model.addAttribute("exportList", result.getModule());
	} else {
	    setError(model, result.getResultMsg());
	}
	return "funds/queryRefundOrderExport";
    }

    @RequestMapping("funds/queryAcctLogExport")
    public String acctLogExport(@ModelAttribute("exportQuery") ExportQuery exportQuery, Model model, HttpSession session) {
	UserInfo userInfo = getUserInfo(session);
	Date date = null;
	try {
	    date = DateTool.strintToDate(DateTool.parseDate(new Date()));
	} catch (ParseException e) {
	}
	if (exportQuery.getEndGmtCreate() == null) {
	    exportQuery.setEndGmtCreate(date);
	}
	if (exportQuery.getStartGmtCreate() == null) {
	    exportQuery.setStartGmtCreate(date);
	}

	exportQuery.setUserId(userInfo.getId());
	exportQuery.setExportType(Constants.Export.TYPE_ACCT_LOG);
	exportQuery.setBizId(501);
	model.addAttribute("userInfo", userInfo);
	setSelectValue(userInfo, model, exportQuery);
	Result<List<Export>> result = queryService.queryExport(exportQuery);
	if (result.isSuccess()) {
	    model.addAttribute("exportList", result.getModule());
	} else {
	    setError(model, result.getResultMsg());
	}
	return "funds/queryAcctLogExport";
    }

    private void setSelectValue(UserInfo userInfo, Model model, ExportQuery exportQuery) {
	model.addAttribute("bizName", Constants.BIZ_MAP.get(exportQuery.getBizId()));
	model.addAttribute("bizId", exportQuery.getBizId());
	if (userInfo.isAdminOrPartner()) {
	    // Result<List<UserInfo>> dsUserResult = userService.queryUserInfoDownStream();
	    // if (dsUserResult.isSuccess() && dsUserResult.getModule() != null) {
	    // Map<Long, String> map = new HashMap<Long, String>();
	    // for (UserInfo user : dsUserResult.getModule()) {
	    // map.put(user.getId(), user.getUserName());
	    // }
	    // model.addAttribute("downStreamUser", map);
	    // }
	    model.addAttribute("downStreamUser", localCachedService.getDownStreamUser());

	    // Result<List<UserInfo>> usUserResult = userService.queryUserInfoUpStream();
	    // if (usUserResult.isSuccess() && usUserResult.getModule() != null) {
	    // Map<Long, String> map = new HashMap<Long, String>();
	    // for (UserInfo user : usUserResult.getModule()) {
	    // map.put(user.getId(), user.getUserName());
	    // }
	    // model.addAttribute("upStreamUser", map);
	    // }
	    model.addAttribute("upStreamUser", localCachedService.getUpStreamUser());
	}

	// Result<List<Item>> itemResult = itemService.queryItemList(exportQuery.getBizId());
	// if (itemResult.isSuccess() && itemResult.getModule() != null) {
	// Map<Integer, String> map = new HashMap<Integer, String>();
	// for (Item item : itemResult.getModule()) {
	// map.put(item.getId(), item.getItemName());
	// }
	// model.addAttribute("itemList", map);
	// }
	model.addAttribute("itemList", localCachedService.getItem());
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new HashMap<String, String>();
	map.put(Constants.Export.STATUS_EXPORT + "", Constants.Export.STATUS_EXPORT_DESC);
	map.put(Constants.Export.STATUS_SUCCESS + "", Constants.Export.STATUS_SUCCESS_DESC);
	map.put(Constants.Export.STATUS_FAILED + "", Constants.Export.STATUS_FAILED_DESC);
	map.put(Constants.Export.STATUS_EXCEPTION + "", Constants.Export.STATUS_EXCEPTION_DESC);
	return map;
    }
}
