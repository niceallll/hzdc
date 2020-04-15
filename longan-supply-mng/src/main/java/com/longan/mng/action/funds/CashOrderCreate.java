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

import com.longan.biz.core.CashOrderService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.UserService;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.form.CashOrderForm;

@Controller
@RequestMapping("funds/cashOrderCreate")
public class CashOrderCreate extends BaseController {
    @Resource
    private CashOrderService cashOrderService;

    @Resource
    private UserService userService;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private Validator validator;

    private static final String returnUrl = "funds/cashOrderCreate";

    @RequestMapping(params = "type=createPre")
    public String onRequestCreatePre(Model model) {
	return "funds/cashOrderCreatePre";
    }

    @RequestMapping(params = "type=queryUser")
    public String onRequestQueryUser(@RequestParam("loginId") String loginId, HttpSession session, Model model) {
	Result<UserInfo> result = userService.getUserInfo(loginId);
	if (result.isSuccess() && result.getModule() != null) {
	    UserInfo userInfo = result.getModule();
	    if (userInfo.getType() != null && userInfo.getType() == Constants.UserInfo.TYPE_DOWNSTREAM) {
		model.addAttribute("userInfo", result.getModule());
	    } else {
		alertError(model, "只能给下游代理商提现");
	    }
	} else {
	    alertError(model, result.getResultMsg());
	}
	return returnUrl;
    }

    @RequestMapping(params = "type=create", method = RequestMethod.POST)
    public String onPostCreate(@ModelAttribute("cashOrderForm") CashOrderForm cashOrderForm, BindingResult bindingResult,
	    HttpSession session, Model model) {
	validator.validate(cashOrderForm, bindingResult);
	if (bindingResult.hasErrors()) {
	    UserInfo user = new UserInfo();
	    user.setAcctId(Long.parseLong(cashOrderForm.getAcctId()));
	    user.setId(Long.parseLong(cashOrderForm.getUserId()));
	    user.setLoginId(cashOrderForm.getLoginId());
	    user.setUserName(cashOrderForm.getUserName());
	    model.addAttribute("userInfo", user);
	    model.addAttribute("errorList", bindingResult.getAllErrors());
	    return returnUrl;
	}

	UserInfo userInfo = super.getUserInfo(session);
	logger.warn(userInfo.getUserName() + "执行提现单 创建操作 ");
	OperationVO operationVO = new OperationVO();
	operationVO.setUserInfo(userInfo);

	CashOrder cashOrder = formToCashOrder(cashOrderForm);
	cashOrder.setPayType(Constants.CashOrder.PAY_TYPE_MAN); // 手动充值
	Result<CashOrder> result = cashOrderService.createCashOrder(operationVO, cashOrder);
	if (result.isSuccess()) {
	    alertSuccess(model);
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(null, cashOrder, userInfo, map.get("moduleName"), null,
		    map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	} else {
	    UserInfo user = new UserInfo();
	    user.setAcctId(Long.parseLong(cashOrderForm.getAcctId()));
	    user.setId(Long.parseLong(cashOrderForm.getUserId()));
	    user.setLoginId(cashOrderForm.getLoginId());
	    user.setUserName(cashOrderForm.getUserName());
	    model.addAttribute("userInfo", user);
	    alertError(model, result.getResultMsg());
	    return returnUrl;
	}
	return "redirect:queryCashOrder.do";
    }

    private CashOrder formToCashOrder(CashOrderForm cashOrderForm) {
	CashOrder result = new CashOrder();
	result.setUserId(Long.parseLong(cashOrderForm.getUserId()));
	result.setAcctId(Long.parseLong(cashOrderForm.getAcctId()));
	result.setAmount(Long.parseLong(cashOrderForm.getAmount()));
	result.setMemo(cashOrderForm.getMemo());
	return result;
    }
}
