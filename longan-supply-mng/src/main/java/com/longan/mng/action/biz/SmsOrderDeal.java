package com.longan.mng.action.biz;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.longan.biz.core.SmsOrderService;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;
import com.longan.client.remote.service.CallBackService;
import com.longan.mng.action.common.BaseController;

@Controller
public class SmsOrderDeal extends BaseController {
    @Resource
    private SmsOrderService smsOrderService;

    @Resource
    private CallBackService callBackService;

    @RequestMapping(value = "biz/smsOrderDeal", params = "type=callback")
    public String onRequestCallback(@RequestParam("smsOrderId") Long smsOrderId, HttpSession session, Model model) {
	String returnUrl = "biz/querySmsOrder";
	Result<SmsOrder> orderResult = smsOrderService.getSmsOrderById(smsOrderId);
	if (!orderResult.isSuccess() || orderResult.getModule() == null) {
	    alertError(model, "没有该订单");
	    return returnUrl;
	}

	SmsOrder smsOrder = orderResult.getModule();
	if (!smsOrder.canCallback()) {
	    alertError(model, "该订单不允许做此操作");
	    return returnUrl;
	}

	Result<List<SmsSupply>> supplyResult = smsOrderService.getSmsSupplyList(smsOrder.getId());
	if (!supplyResult.isSuccess() || supplyResult.getModule() == null) {
	    alertError(model, "没有该订单");
	    return returnUrl;
	}

	List<SmsSupply> supplyList = supplyResult.getModule();
	try {
	    callBackService.callBackAsync(smsOrder, supplyList);
	} catch (Exception e) {
	    logger.error("callBackAsync error", e);
	}

	String successUrl = "querySmsOrder.do?bizId=" + smsOrder.getBizId() + "&id=" + smsOrderId;
	alertSuccess(model, successUrl);
	return returnUrl;
    }

    @RequestMapping(value = "biz/smsSupplyDeal", params = "type=makeUp")
    public String onRequestMakeUp(@RequestParam("smsSupplyId") Long smsSupplyId, HttpSession session, Model model) {
	String returnUrl = "biz/querySmsSupply";
	Result<SmsSupply> supplyResult = smsOrderService.getSmsSupplyById(smsSupplyId);
	if (!supplyResult.isSuccess() || supplyResult.getModule() == null) {
	    alertError(model, "没有该供货单");
	    return returnUrl;
	}

	SmsSupply smsSupply = supplyResult.getModule();
	if (!smsSupply.canDeal()) {
	    alertError(model, "该供货单不允许做此操作");
	    return returnUrl;
	}

	smsSupply.setUpstreamMemo("客服处理成功");
	Result<Boolean> confirmSupplyResult = smsOrderService.confirmSmsSupply(smsSupply);
	if (!confirmSupplyResult.isSuccess()) {
	    alertError(model, confirmSupplyResult.getResultMsg());
	    return returnUrl;
	}

	Long smsOrderId = smsSupply.getBizOrderId();
	Result<List<SmsSupply>> allSupplyResult = smsOrderService.getSmsSupplyList(smsOrderId);
	if (!allSupplyResult.isSuccess() || allSupplyResult.getModule() == null) {
	    alertError(model, allSupplyResult.getResultMsg());
	    return returnUrl;
	}

	boolean finalOrder = true;
	List<SmsSupply> supplyList = allSupplyResult.getModule();
	for (SmsSupply supply : supplyList) {
	    if (!supply.isOver()) {
		finalOrder = false;
		break;
	    }
	}

	if (finalOrder) {
	    Result<SmsOrder> orderResult = smsOrderService.getSmsOrderById(smsOrderId);
	    if (!orderResult.isSuccess() || orderResult.getModule() == null) {
		alertError(model, "没有该订单");
		return returnUrl;
	    }

	    SmsOrder smsOrder = orderResult.getModule();
	    if (smsOrder.canDeal()) {
		Integer statusOrder = Constants.SupplyOrder.STATUS_SUCCESS;
		for (SmsSupply supply : supplyList) {
		    Integer supplyStatus = supply.getSupplyStatus();
		    if (supplyStatus == Constants.SupplyOrder.STATUS_FAILED) {
			statusOrder = Constants.BizOrder.STATUS_PARTS;
			break;
		    }
		}

		smsOrder.setStatus(statusOrder);
		smsOrder.setUpstreamMemo("客服处理成功");
		Result<Boolean> confirmOrderResult = smsOrderService.confirmSmsOrder(smsOrder);
		if (!confirmOrderResult.isSuccess()) {
		    alertError(model, confirmOrderResult.getResultMsg());
		    return returnUrl;
		}

		try {
		    callBackService.callBackAsync(smsOrder, supplyList);
		} catch (Exception ex) {
		    logger.error("callBackAsync error", ex);
		}
	    }
	}

	String successUrl = "querySmsSupply.do?bizId=" + smsSupply.getBizId() + "&id=" + smsSupplyId;
	alertSuccess(model, successUrl);
	return returnUrl;
    }
}
