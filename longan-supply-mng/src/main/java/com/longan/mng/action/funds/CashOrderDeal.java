package com.longan.mng.action.funds;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.CashOrderService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("funds/cashOrderDeal")
public class CashOrderDeal extends BaseController {
    @Resource
    private CashOrderService cashOrderService;

    @Resource
    private OperationLogService operationLogService;

    private static final String returnUrl = "funds/queryCashOrder";

    @RequestMapping(params = "type=pass")
    public String onRequestPass(@RequestParam("cashOrderId") Long cashOrderId, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	Result<CashOrder> cashOrderResult = cashOrderService.getCashOrder(cashOrderId);
	CashOrder older;
	if (!cashOrderResult.isSuccess()) {
	    super.alertError(model, cashOrderResult.getResultMsg());
	    return returnUrl;
	} else {
	    if (cashOrderResult.getModule() == null) {
		super.alertError(model, "提现单为空");
		return returnUrl;
	    }
	    older = cashOrderResult.getModule();
	}
	logger.warn(userInfo.getUserName() + "执行提现单审核通过操作 提现单号 : " + cashOrderId);

	OperationVO operationVO = new OperationVO();
	operationVO.setUserInfo(userInfo);
	operationVO.setOperationMemo("审核通过");
	Result<Boolean> result = cashOrderService.verifyCashOrder(operationVO, cashOrderId);
	if (result.isSuccess() && result.getModule()) {
	    alertSuccess(model, "queryCashOrder.do" + "?id=" + cashOrderId);
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, cashOrderService.getCashOrder(cashOrderId)
		    .getModule(), userInfo, map.get("moduleName") + "(通过)", null, map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	} else {
	    alertError(model, result.getResultMsg());
	}
	return returnUrl;
    }

    @RequestMapping(params = "type=cancel")
    public String onRequestCancel(@RequestParam("cashOrderId") Long cashOrderId, HttpSession session, Model model) {
	UserInfo userInfo = super.getUserInfo(session);
	CashOrder older = cashOrderService.getCashOrder(cashOrderId).getModule();
	logger.warn(userInfo.getUserName() + "执行提现单取消操作 提现单号 : " + cashOrderId);
	OperationVO operationVO = new OperationVO();
	operationVO.setUserInfo(userInfo);
	operationVO.setOperationMemo("审核不通过");
	Result<Boolean> result = cashOrderService.cancelCashOrder(operationVO, cashOrderId);
	if (result.isSuccess() && result.getModule()) {
	    alertSuccess(model, "queryCashOrder.do" + "?id=" + cashOrderId);
	    @SuppressWarnings("unchecked")
	    Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	    OperationLog operationLog = OperLogUtils.operationLogDeal(older, cashOrderService.getCashOrder(cashOrderId)
		    .getModule(), userInfo, map.get("moduleName") + "(未通过)", null, map.get("loginIp"));
	    operationLogService.createOperationLog(operationLog);
	} else {
	    alertError(model, result.getResultMsg());
	}
	return returnUrl;
    }

}
