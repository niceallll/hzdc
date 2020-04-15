package com.longan.mng.api.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.MimiMonitorService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.Utils;
import com.longan.client.remote.service.CallBackService;
import com.longan.mng.action.common.BaseController;
import com.longan.mng.api.request.CallbackBizOrderRequest;
import com.longan.mng.api.request.LockBizOrderRequest;
import com.longan.mng.api.response.CallbackBizOrderResponse;
import com.longan.mng.api.response.LockBizOrderResponse;
import com.longan.mng.api.response.MiniBizOrderResponse;

@Controller
public class MiniBizOrder extends BaseController {
    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private Validator validator;

    @Resource
    private MimiMonitorService mimiMonitorService;

    @Resource
    private CallBackService callBackService;

    private static final String STATUS_FAILED = "0";
    private static final String STATUS_SUCCESS = "1";
    private static final String STATUS_TIMEOUT = "2";
    private static final String STATUS_BUY_CARD_SUCCESS = "3";
    private static final long ROBOT_ID = Utils.getLong("robotId");

    @RequestMapping(value = "api/lockBizOrder")
    public @ResponseBody
    MiniBizOrderResponse onRequestLock(@ModelAttribute("lockBizOrderRequest") LockBizOrderRequest lockBizOrderRequest,
	    BindingResult bindingResult, HttpSession session, Model model, HttpServletRequest request) {
	LockBizOrderResponse lockBizOrderResponse = new LockBizOrderResponse();
	logger.warn("接受: ip : " + super.getRemoteIp(request) + " robot锁定订单请求 : " + lockBizOrderRequest);
	validator.validate(lockBizOrderRequest, bindingResult);
	if (bindingResult.hasErrors()) {
	    return getErrorResponse(bindingResult);
	}

	// TODO checkSign

	// 心跳一下
	mimiMonitorService.heartbeat(lockBizOrderRequest.getPcId(), lockBizOrderRequest.getPosId());

	if (!mimiMonitorService.checkLimit(lockBizOrderRequest.getPosId())) {
	    lockBizOrderResponse.setMsg("已经达到充值限制");
	    logger.warn("返回: " + lockBizOrderResponse);
	    return lockBizOrderResponse;
	}

	Result<BizOrder> result = bizOrderService.robotLockBizOrder(Long.parseLong(lockBizOrderRequest.getSupplyTraderId()),
		lockBizOrderRequest.getPosId(), lockBizOrderRequest.getPcId(), ROBOT_ID);
	if (!result.isSuccess()) {
	    lockBizOrderResponse.setMsg(result.getResultMsg());
	    logger.warn("返回: " + lockBizOrderResponse);
	    return lockBizOrderResponse;
	}
	BizOrder bizOrder = result.getModule();
	if (bizOrder == null) {
	    lockBizOrderResponse.setMsg("锁单失败，可能没有订单或抢单失败");
	    logger.warn("返回: " + lockBizOrderResponse);
	    return lockBizOrderResponse;
	}
	lockBizOrderResponse.setSuccess();
	lockBizOrderResponse.setBizOrderId(bizOrder.getId());
	lockBizOrderResponse.setTelephone(bizOrder.getItemUid());
	lockBizOrderResponse.setItemFacePrice(bizOrder.getItemFacePrice());
	logger.warn("返回: " + lockBizOrderResponse);
	return lockBizOrderResponse;
    }

    @RequestMapping(value = "api/callbackBizOrder")
    public @ResponseBody
    MiniBizOrderResponse onRequestCallBack(
	    @ModelAttribute("callbackBizOrderRequest") CallbackBizOrderRequest callbackBizOrderRequest,
	    BindingResult bindingResult, HttpSession session, Model model, HttpServletRequest request) {
	CallbackBizOrderResponse callbackBizOrderResponse = new CallbackBizOrderResponse();
	logger.warn("接受: ip : " + super.getRemoteIp(request) + " robot结果返回请求 : " + callbackBizOrderRequest);
	validator.validate(callbackBizOrderRequest, bindingResult);
	if (bindingResult.hasErrors()) {
	    return getErrorResponse(bindingResult);
	}

	Long bizOrderId = Long.parseLong(callbackBizOrderRequest.getBizOrderId());
	Result<BizOrder> queryResult = bizOrderService.getBizOrderById(bizOrderId);

	if (!queryResult.isSuccess()) {
	    callbackBizOrderResponse.setMsg(queryResult.getResultMsg());
	    logger.warn("返回: " + callbackBizOrderResponse);
	    return callbackBizOrderResponse;
	}

	BizOrder bizOrder = queryResult.getModule();
	if (bizOrder == null) {
	    callbackBizOrderResponse.setMsg("没有改订单,订单编号:" + bizOrderId);
	    logger.warn("返回: " + callbackBizOrderResponse);
	    return callbackBizOrderResponse;
	}

	// 设置处理人
	bizOrder.setDealOperId(ROBOT_ID);
	if (STATUS_SUCCESS.equals(callbackBizOrderRequest.getStatus())) {
	    bizOrder.setUpstreamSerialno(callbackBizOrderRequest.getNo());
	    Result<Boolean> result = bizOrderService.manualConfirmBizOrder(bizOrder);
	    if (!result.isSuccess()) {
		callbackBizOrderResponse.setMsg(result.getResultMsg());
		logger.warn("返回: " + callbackBizOrderResponse);
		return callbackBizOrderResponse;
	    }

	    callbackBizOrderResponse.setSuccess();
	    mimiMonitorService.countByPosId(bizOrder.getItemPosId(), bizOrder.getAmount());

	    bizOrder.setStatus(Constants.BizOrder.STATUS_SUCCESS);
	    callback(bizOrder);

	    logger.warn("返回: " + callbackBizOrderResponse);
	    return callbackBizOrderResponse;
	} else if (STATUS_BUY_CARD_SUCCESS.equals(callbackBizOrderRequest.getStatus())) {
	    bizOrder.setItemCard(callbackBizOrderRequest.getCard());
	    bizOrder.setUpstreamSerialno(callbackBizOrderRequest.getNo());
	    bizOrder.setItemCardPwd(callbackBizOrderRequest.getPwd());
	    Result<Boolean> result = bizOrderService.manualUnConfirmBizOrder(bizOrder);
	    if (!result.isSuccess()) {
		callbackBizOrderResponse.setMsg(result.getResultMsg());
		logger.warn("返回: " + callbackBizOrderResponse);
		return callbackBizOrderResponse;
	    }

	    callbackBizOrderResponse.setSuccess();
	    mimiMonitorService.countByPosId(bizOrder.getItemPosId(), bizOrder.getAmount());

	    logger.warn("返回: " + callbackBizOrderResponse);
	    return callbackBizOrderResponse;

	} else if (STATUS_TIMEOUT.equals(callbackBizOrderRequest.getStatus())) {
	    Result<Boolean> result = bizOrderService.manualUnConfirmBizOrder(bizOrder);
	    if (!result.isSuccess()) {
		callbackBizOrderResponse.setMsg(result.getResultMsg());
		logger.warn("返回: " + callbackBizOrderResponse);
		return callbackBizOrderResponse;
	    }
	    mimiMonitorService.countByPosId(bizOrder.getItemPosId(), bizOrder.getAmount());

	    callbackBizOrderResponse.setSuccess();
	    logger.warn("返回: " + callbackBizOrderResponse);
	    return callbackBizOrderResponse;
	} else if (STATUS_FAILED.equals(callbackBizOrderRequest.getStatus())) {
	    Result<Boolean> result = bizOrderService.manualCancelBizOrder(bizOrder);
	    if (!result.isSuccess()) {
		callbackBizOrderResponse.setMsg(result.getResultMsg());
		logger.warn("返回: " + callbackBizOrderResponse);
		return callbackBizOrderResponse;
	    }

	    bizOrder.setStatus(Constants.BizOrder.STATUS_FAILED);
	    callback(bizOrder);

	    callbackBizOrderResponse.setSuccess();
	    logger.warn("返回: " + callbackBizOrderResponse);
	    return callbackBizOrderResponse;
	}

	callbackBizOrderResponse.setMsg("非法状态");
	return callbackBizOrderResponse;
    }

    private MiniBizOrderResponse getErrorResponse(BindingResult bindingResult) {
	MiniBizOrderResponse result = new MiniBizOrderResponse();
	StringBuffer sb = new StringBuffer();
	for (ObjectError objectError : bindingResult.getAllErrors()) {
	    sb.append(objectError.getDefaultMessage()).append(",");
	}
	result.setMsg(sb.toString());
	logger.warn("返回: " + result);
	return result;
    }

    private void callback(BizOrder bizOrder) {
	// 通知下游
	try {
	    Result<Boolean> callBackResult = callBackService.callBackAsync(bizOrder);
	    if (!callBackResult.isSuccess() || !callBackResult.getModule()) {
		logger.error("callback failed bizOrderId : " + bizOrder.getId(), callBackResult.getResultMsg());
	    }
	} catch (Exception e) {
	    logger.error("callback failed bizOrderId : " + bizOrder.getId(), e);
	}
    }
}
