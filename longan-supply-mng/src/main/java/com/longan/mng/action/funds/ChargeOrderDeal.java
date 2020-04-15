package com.longan.mng.action.funds;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.ChargeOrderService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("funds/chargeOrderDeal")
public class ChargeOrderDeal extends BaseController {
    @Resource
    private ChargeOrderService chargeOrderService;

    @Resource
    private OperationLogService operationLogService;

    private static final String returnUrl = "funds/queryChargeOrder";

    @RequestMapping(params = "type=pass")
    public String onRequestPass(@RequestParam("chargeOrderId") Long chargeOrderId, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	Result<ChargeOrder> chargeOrderResult = chargeOrderService.getChargeOrder(chargeOrderId);
	ChargeOrder older;
	if (!chargeOrderResult.isSuccess()) {
	    super.alertError(model, chargeOrderResult.getResultMsg());
	    return returnUrl;
	} else {
	    if (chargeOrderResult.getModule() == null) {
		super.alertError(model, "充值单为空");
		return returnUrl;
	    }
	    older = chargeOrderResult.getModule();
	}
	logger.warn(userInfo.getUserName() + "执行充值单审核通过操作 充值单号 : " + chargeOrderId);

	OperationVO operationVO = new OperationVO();
	operationVO.setUserInfo(userInfo);
	operationVO.setOperationMemo("审核通过");
	Result<Boolean> result = chargeOrderService.verifyChargeOrder(operationVO, chargeOrderId);
	if (result.isSuccess() && result.getModule()) {
	    alertSuccess(model, "queryChargeOrder.do" + "?id=" + chargeOrderId);
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, chargeOrderService.getChargeOrder(chargeOrderId)
		    .getModule(), userInfo, map.get("moduleName") + "(通过)", null, map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	} else {
	    alertError(model, result.getResultMsg());
	}
	return returnUrl;
    }

    @RequestMapping(params = "type=cancel")
    public String onRequestCancel(@RequestParam("chargeOrderId") Long chargeOrderId, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	ChargeOrder older = chargeOrderService.getChargeOrder(chargeOrderId).getModule();
	logger.warn(userInfo.getUserName() + "执行充值单取消操作 充值单号 : " + chargeOrderId);
	OperationVO operationVO = new OperationVO();
	operationVO.setUserInfo(userInfo);
	operationVO.setOperationMemo("审核不通过");
	Result<Boolean> result = chargeOrderService.cancelChargeOrder(operationVO, chargeOrderId);
	if (result.isSuccess() && result.getModule()) {
	    alertSuccess(model, "queryChargeOrder.do" + "?id=" + chargeOrderId);
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, chargeOrderService.getChargeOrder(chargeOrderId)
		    .getModule(), userInfo, map.get("moduleName") + "(未通过)", null, map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	} else {
	    alertError(model, result.getResultMsg());
	}
	return returnUrl;
    }

    @RequestMapping(params = "type=createPre")
    public String onPostCreatePre(@RequestParam("loginId") Long loginId) {
	return "funds/createChargeOrderPre";
    }
}
