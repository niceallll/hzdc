package com.longan.mng.action.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.UserService;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.ModifyPwdForm;

@Controller
@RequestMapping("user/userModifyPwd")
public class UserModifyPwd extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private Validator validator;

    @Resource
    OperationLogService operationLogService;

    @RequestMapping(method = RequestMethod.GET)
    public void index(HttpSession session, Model model) {
	UserInfo user = getUserInfo(session);
	if (user != null) {
	    model.addAttribute("userId", user.getId());
	}
    }

    @RequestMapping(method = RequestMethod.POST)
    public void onRequest(@ModelAttribute("modifyPwdForm") ModifyPwdForm modifyPwdForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	validator.validate(modifyPwdForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return;
	}
	UserInfo user = getUserInfo(session);
	Result<Boolean> result = userService.modifyPwd(user.getId(), modifyPwdForm.getOldPwd(), modifyPwdForm.getNewPwd());

	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	    return;
	}
	UserInfo newer = userService.getUserInfoById(user.getId()).getModule();
	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(user, newer, user, map.get("moduleName"), null,
		map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	alertSuccess(model, "userInfoShow.do?id=" + user.getId());
    }
}
