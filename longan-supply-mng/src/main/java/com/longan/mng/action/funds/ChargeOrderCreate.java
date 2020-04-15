package com.longan.mng.action.funds;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.ChargeOrderService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.UserService;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.ChargeOrderForm;

@Controller
@RequestMapping("funds/chargeOrderCreate")
public class ChargeOrderCreate extends BaseController {
    @Resource
    private ChargeOrderService chargeOrderService;

    @Resource
    private UserService userService;

    @Resource
    private Validator validator;

    @Resource
    private OperationLogService operationLogService;

    private static final String returnUrl = "funds/chargeOrderCreate";

    @RequestMapping(params = "type=createPre")
    public String onRequestCreatePre(Model model) {
	return "funds/chargeOrderCreatePre";
    }

    @RequestMapping(params = "type=queryUser")
    public String onRequestQueryUser(@RequestParam("loginId") String loginId, HttpSession session, Model model) {
	Result<UserInfo> result = userService.getUserInfo(loginId);
	if (result.isSuccess() && result.getModule() != null) {
	    UserInfo userInfo = result.getModule();
	    if (userInfo.getType() != null && userInfo.getType() == Constants.UserInfo.TYPE_DOWNSTREAM) {
		model.addAttribute("userInfo", result.getModule());
	    } else {
		alertError(model, "只能给下游代理商充值");
	    }
	} else {
	    alertError(model, result.getResultMsg());
	}
	return returnUrl;
    }

    @RequestMapping(params = "type=create", method = RequestMethod.POST)
    public String onPostCreate(@ModelAttribute("chargeOrderForm") ChargeOrderForm chargeOrderForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	validator.validate(chargeOrderForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    UserInfo user = new UserInfo();
	    user.setAcctId(Long.parseLong(chargeOrderForm.getAcctId()));
	    user.setId(Long.parseLong(chargeOrderForm.getUserId()));
	    user.setLoginId(chargeOrderForm.getLoginId());
	    user.setUserName(chargeOrderForm.getUserName());
	    model.addAttribute("userInfo", user);
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return returnUrl;
	}

	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行充值单 创建操作 ");
	OperationVO operationVO = new OperationVO();
	operationVO.setUserInfo(userInfo);

	ChargeOrder chargeOrder = formToChargeOrder(chargeOrderForm);
	chargeOrder.setPayType(Constants.ChargeOrder.PAY_TYPE_MAN); // 手动充值
	Result<ChargeOrder> result = chargeOrderService.createChargeOrder(operationVO, chargeOrder);
	if (result.isSuccess()) {
	    alertSuccess(model);
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(null, chargeOrder, userInfo, map.get("moduleName"), null,
		    map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	} else {
	    UserInfo user = new UserInfo();
	    user.setAcctId(Long.parseLong(chargeOrderForm.getAcctId()));
	    user.setId(Long.parseLong(chargeOrderForm.getUserId()));
	    user.setLoginId(chargeOrderForm.getLoginId());
	    user.setUserName(chargeOrderForm.getUserName());
	    model.addAttribute("userInfo", user);
	    alertError(model, result.getResultMsg());
	    return returnUrl;
	}
	return "redirect:queryChargeOrder.do";
    }

    private ChargeOrder formToChargeOrder(ChargeOrderForm chargeOrderForm) {
	ChargeOrder result = new ChargeOrder();
	result.setUserId(Long.parseLong(chargeOrderForm.getUserId()));
	result.setAcctId(Long.parseLong(chargeOrderForm.getAcctId()));
	result.setAmount(Long.parseLong(chargeOrderForm.getAmount()));
	result.setMemo(chargeOrderForm.getMemo());
	return result;
    }
}
