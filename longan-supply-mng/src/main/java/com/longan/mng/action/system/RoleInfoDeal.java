package com.longan.mng.action.system;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.longan.biz.core.RoleInfoService;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.RoleInfoForm;

@Controller
public class RoleInfoDeal extends BaseController {
    @Resource
    private RoleInfoService roleInfoService;

    @Resource
    private Validator validator;

    @RequestMapping(value = "system/roleInfoEdit", method = RequestMethod.GET)
    public void onEditIndex(@RequestParam("id") Integer id, Model model) {
	model.addAttribute("id", id);
	Result<RoleInfo> result = roleInfoService.getRoleInfoById(id);
	if (result.isSuccess()) {
	    RoleInfo roleInfo = result.getModule();
	    model.addAttribute("roleInfo", roleInfo);
	} else {
	    super.alertError(model, result.getResultMsg());
	}
    }

    @RequestMapping(value = "system/roleInfoEdit", method = RequestMethod.POST)
    public void onPostEdit(@ModelAttribute("roleInfoForm") RoleInfoForm roleInfoForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	if (roleInfoForm.getId() == null) {
	    super.alertError(model, "操作编号不能为空");
	    return;
	}
	Result<RoleInfo> roleInfoResult = roleInfoService.getRoleInfoById(Integer.parseInt(roleInfoForm.getId()));
	if (!roleInfoResult.isSuccess()) {
	    super.alertError(model, roleInfoResult.getResultMsg());
	    return;
	} else {
	    RoleInfo roleInfo = roleInfoResult.getModule();
	    if (roleInfo == null) {
		super.alertError(model, "角色信息为空");
		return;
	    }
	    model.addAttribute("roleInfo", roleInfo);
	}
	validator.validate(roleInfoForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return;
	}
	RoleInfo older = roleInfoResult.getModule();
	RoleInfo roleInfo = formToRoleInfo(roleInfoForm);
	Result<Boolean> result = roleInfoService.updateRoleInfo(roleInfo);
	if (result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, roleInfo, userInfo, map.get("moduleName"), null,
		    map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    super.alertSuccess(model, "queryRoleInfo.do");
	    return;
	} else {
	    super.alertError(model, result.getResultMsg());
	    return;
	}
    }

    @RequestMapping(value = "system/roleInfoAdd", method = RequestMethod.GET)
    public void onAddIndex(Model model) {
    }

    @RequestMapping(value = "system/roleInfoAdd", method = RequestMethod.POST)
    public void onPostAdd(@ModelAttribute("roleInfoForm") RoleInfoForm roleInfoForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	validator.validate(roleInfoForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return;
	}
	RoleInfo roleInfo = formToRoleInfo(roleInfoForm);
	Result<Boolean> result = null;
	result = roleInfoService.createRoleInfo(roleInfo);
	if (result != null && result.isSuccess()) {
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(null, roleInfo, userInfo, map.get("moduleName"), null,
		    map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	    super.alertSuccess(model, "queryRoleInfo.do");
	} else {
	    super.alertError(model, result.getResultMsg());
	}
    }

    private RoleInfo formToRoleInfo(RoleInfoForm roleInfoForm) {
	RoleInfo result = new RoleInfo();
	if (StringUtils.isNotEmpty(roleInfoForm.getId())) {
	    result.setId(Integer.parseInt(roleInfoForm.getId()));
	}
	result.setRoleName(roleInfoForm.getRoleName());
	result.setRoleDesc(roleInfoForm.getRoleDesc());
	if (StringUtils.isNotEmpty(roleInfoForm.getStatus())) {
	    result.setStatus(Integer.parseInt(roleInfoForm.getStatus()));
	}
	return result;
    }

    @ModelAttribute("statusList")
    public Map<String, String> statusList() {
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put(Constants.RoleInfo.STATUS_NORMAL + "", Constants.RoleInfo.STATUS_NORMAL_DESC);
	map.put(Constants.RoleInfo.STATUS_CANCEL + "", Constants.RoleInfo.STATUS_CANCEL_DESC);
	map.put(Constants.RoleInfo.STATUS_DEL + "", Constants.RoleInfo.STATUS_DEL_DESC);
	return map;
    }
}
