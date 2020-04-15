package com.longan.mng.action.system;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.OperationInfoService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.OperationInfoForm;

@Controller
public class OperationInfoDeal extends BaseController {
    @Resource
    private OperationInfoService operationInfoService;

    @Resource
    private Validator validator;

    @Resource
    private OperationLogService operationLogService;

    @RequestMapping(value = "system/operationInfoEdit", method = RequestMethod.GET)
    public void onEditIndex(@RequestParam("id") Integer id, Model model) {
	model.addAttribute("id", id);
	Result<OperationInfo> result = operationInfoService.getOperationInfoById(id);
	if (result.isSuccess()) {
	    OperationInfo operationInfo = result.getModule();
	    model.addAttribute("operationInfo", operationInfo);
	} else {
	    super.alertError(model, result.getResultMsg());
	}
    }

    @RequestMapping(value = "system/operationInfoEdit", method = RequestMethod.POST)
    public void onPostEdit(@ModelAttribute("operationInfoForm") OperationInfoForm operationInfoForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	if (operationInfoForm.getId() == null) {
	    super.alertError(model, "操作编号不能为空");
	    return;
	}
	Result<OperationInfo> operationInfoResult = operationInfoService.getOperationInfoById(Integer.parseInt(operationInfoForm
		.getId()));
	if (!operationInfoResult.isSuccess()) {
	    super.alertError(model, operationInfoResult.getResultMsg());
	    return;
	} else {
	    OperationInfo operationInfo = operationInfoResult.getModule();
	    if (operationInfo == null) {
		super.alertError(model, "操作信息为空");
		return;
	    }
	    model.addAttribute("operationInfo", operationInfo);
	}
	OperationInfo older = operationInfoResult.getModule();
	validator.validate(operationInfoForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return;
	}
	OperationInfo operationInfo = formToOperationInfo(operationInfoForm);
	Result<Boolean> result = operationInfoService.updateOperationInfo(operationInfo);
	if (result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, operationInfo, userInfo, map.get("moduleName"),
		    null, map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    super.alertSuccess(model, "queryOperationInfo.do");
	    return;
	} else {
	    super.alertError(model, result.getResultMsg());
	    return;
	}
    }

    @RequestMapping(value = "system/operationInfoAdd", method = RequestMethod.GET)
    public void onAddIndex(Model model) {
    }

    @RequestMapping(value = "system/operationInfoAdd", method = RequestMethod.POST)
    public void onPostAdd(@ModelAttribute("operationInfoForm") OperationInfoForm operationInfoForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	validator.validate(operationInfoForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return;
	}
	OperationInfo operationInfo = formToOperationInfo(operationInfoForm);
	Result<Boolean> result = null;
	result = operationInfoService.createOperationInfo(operationInfo);
	if (result != null && result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(null, operationInfo, userInfo, map.get("moduleName"), null,
		    map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    super.alertSuccess(model, "queryOperationInfo.do");
	} else {
	    super.alertError(model, result.getResultMsg());
	}
    }

    private OperationInfo formToOperationInfo(OperationInfoForm operationInfoForm) {
	OperationInfo result = new OperationInfo();
	if (StringUtils.isNotEmpty(operationInfoForm.getId())) {
	    result.setId(Integer.parseInt(operationInfoForm.getId()));
	}
	if (StringUtils.isNotEmpty(operationInfoForm.getBelongMenu())) {
	    result.setBelongMenu(Integer.parseInt(operationInfoForm.getBelongMenu()));
	}
	if (StringUtils.isNotEmpty(operationInfoForm.getBizId())) {
	    result.setBizId(Integer.parseInt(operationInfoForm.getBizId()));
	}
	if (StringUtils.isNotEmpty(operationInfoForm.getOperationCode())) {
	    result.setOperationCode(operationInfoForm.getOperationCode());
	}
	result.setOperationName(operationInfoForm.getOperationName());
	if (StringUtils.isNotEmpty(operationInfoForm.getOperationUrl())) {
	    result.setOperationUrl(operationInfoForm.getOperationUrl());
	}
	if (StringUtils.isNotEmpty(operationInfoForm.getStatus())) {
	    result.setStatus(Integer.parseInt(operationInfoForm.getStatus()));
	}
	result.setType(Integer.parseInt(operationInfoForm.getType()));
	return result;
    }

    @ModelAttribute("firstMenuList")
    public Map<Integer, String> firstMenuList() {
	Map<Integer, String> map = new HashMap<Integer, String>();
	Result<List<OperationInfo>> result = operationInfoService.queryOperationInfoByType(Constants.OperationInfo.TYPE_CATALOG);
	if (result.isSuccess() && result.getModule() != null) {
	    for (OperationInfo operationInfo : result.getModule()) {
		map.put(operationInfo.getId(), operationInfo.getOperationName());
	    }
	}
	return map;
    }

    @ModelAttribute("typeList")
    public Map<String, String> typeList() {
	Map<String, String> mapType = new HashMap<String, String>();
	mapType.put(Constants.OperationInfo.TYPE_CATALOG + "", Constants.OperationInfo.TYPE_CATALOG_DESC);
	mapType.put(Constants.OperationInfo.TYPE_URL + "", Constants.OperationInfo.TYPE_URL_DESC);
	mapType.put(Constants.OperationInfo.TYPE_BIZ + "", Constants.OperationInfo.TYPE_BIZ_DESC);
	return mapType;
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.OperationInfo.STATUS_NORMAL + "", Constants.OperationInfo.STATUS_NORMAL_DESC);
	map.put(Constants.OperationInfo.STATUS_CANCEL + "", Constants.OperationInfo.STATUS_CANCEL_DESC);
	map.put(Constants.OperationInfo.STATUS_DEL + "", Constants.OperationInfo.STATUS_DEL_DESC);
	return map;
    }
}
