package com.longan.mng.action.funds;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.core.PayOrderService;
import com.longan.biz.core.PayService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.OperLogUtils;
import com.longan.mng.action.common.BaseController;

@Controller
@RequestMapping("funds/payOrderDeal")
public class PayOrderDeal extends BaseController {
    @Resource
    private PayService payService;

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private OperationLogService operationLogService;

    @RequestMapping(method = RequestMethod.GET)
    public String onRequest(@RequestParam("payOrderId") Long payOrderId, @RequestParam("reason") String reason, Model model,
	    HttpSession session) {
	UserInfo userInfo = super.getUserInfo(session);
	Result<PayOrder> payOrderResult = payOrderService.getPayOrder(payOrderId);
	PayOrder older;
	if (!payOrderResult.isSuccess()) {
	    super.alertError(model, payOrderResult.getResultMsg());
	    return "funds/queryPayOrder";
	} else {
	    if (payOrderResult.getModule() == null) {
		super.alertError(model, "支付单为空");
		return "funds/queryPayOrder";
	    }
	    older = payOrderResult.getModule();
	}
	logger.warn(userInfo.getUserName() + "执行调账退款操作支付单号 : " + payOrderId);
	OperationVO operationVO = new OperationVO();
	operationVO.setUserInfo(userInfo);
	try {
	    reason = new String(reason.getBytes("ISO-8859-1"), "UTF-8");
	} catch (UnsupportedEncodingException e) {
	}
	operationVO.setOperationMemo(reason);

	Result<BizOrder> bizOrderResult = bizOrderService.getBizOrderByPayOrder(payOrderId);
	if (!bizOrderResult.isSuccess()) {
	    alertError(model, bizOrderResult.getResultMsg());
	    return "funds/queryPayOrder";
	}

	if (!bizOrderResult.getModule().canAdjust()) {
	    alertError(model, "调账失败，支付单对应的订单状态必须是成功或者创建中");
	    return "funds/queryPayOrder";
	}

	Result<Boolean> result = payService.adjustPayOrder(payOrderId, operationVO);
	if (!result.isSuccess()) {
	    alertError(model, result.getResultMsg());
	    return "funds/queryPayOrder";
	}
	alertSuccess(model);
	@SuppressWarnings("unchecked")
	Map<String, String> map = (HashMap<String, String>) session.getAttribute("requestInfoMap");
	OperationLog operationLog = OperLogUtils.operationLogDeal(older, payOrderService.getPayOrder(payOrderId).getModule(),
		userInfo, map.get("moduleName"), older.getBizId(), map.get("loginIp"));
	operationLogService.createOperationLog(operationLog);
	return "funds/queryPayOrder";
    }
}
