package com.longan.biz.core.impl;

import javax.annotation.Resource;

import com.longan.biz.core.AcctService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.PayOrderService;
import com.longan.biz.core.PayService;
import com.longan.biz.core.RefundOrderService;
import com.longan.biz.dao.CommitPayDAO;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class PayServiceImpl extends BaseService implements PayService {
    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private RefundOrderService refundOrderService;

    @Resource
    private AcctService acctService;

    @Resource
    private CommitPayDAO commitPayDAO;

    @Override
    public Result<BizOrder> commitPay(BizOrder bizOrder) {
	Result<BizOrder> result = new Result<BizOrder>();
	if (bizOrder == null) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_PAYORDER);
	    result.setResultMsg("bizOrder is null");
	    return result;
	}

	// 创建支付单
	PayOrder payOrder = null;
	Result<PayOrder> createPayOrderResult = payOrderService.createPayOrder(bizOrder);
	if (createPayOrderResult.isSuccess()) {
	    payOrder = createPayOrderResult.getModule();
	} else {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_PAYORDER);
	    result.setResultMsg(createPayOrderResult.getResultMsg());
	    logger.error("commitPay error bizOrderId : " + bizOrder.getId() + " msg : " + createPayOrderResult.getResultMsg());
	    return result;
	}

	// 修改订单信息
	bizOrder.setPayOrderId(payOrder.getId());
	Result<Boolean> updateResult = bizOrderService.updateBizOrder(bizOrder);
	if (!updateResult.isSuccess()) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_PAYORDER);
	    result.setResultMsg(updateResult.getResultMsg());
	    logger.error("commitPay error bizOrderId : " + bizOrder.getId() + " msg : " + updateResult.getResultMsg());
	    return result;
	}

	// 支付
	Result<Boolean> deductMoneyResult = acctService.deductMoney(payOrder.getId());
	if (deductMoneyResult.isSuccess() && deductMoneyResult.getModule()) {
	    result.setSuccess(true);
	    result.setModule(bizOrder);
	} else {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_PAYORDER);
	    result.setResultMsg(deductMoneyResult.getResultMsg());
	    logger.error("commitPay error bizOrderId : " + bizOrder.getId() + " msg : " + deductMoneyResult.getResultMsg());
	    return result;
	}
	return result;
    }

    @Override
    public Result<RefundOrder> commitRefund(Long payOrderId, OperationVO operationVO) {
	Result<RefundOrder> result = new Result<RefundOrder>();
	PayOrder payOrder = null;
	Result<PayOrder> getPayOrderResult = payOrderService.getPayOrder(payOrderId);
	if (getPayOrderResult.isSuccess()) {
	    payOrder = getPayOrderResult.getModule();
	    if (payOrder == null) {
		// 设置resultCode 是为了调用的做判断
		result.setResultCode(Constants.PayOrder.CODE_NO_NEED_REFUND);
		result.setResultMsg("退款失败，没有该支付单");
		return result;
	    }
	    if (payOrder.getStatus() == null || payOrder.getStatus() != Constants.PayOrder.STATUS_SUCCESS) {
		// 设置resultCode 是为了调用的做判断
		result.setResultCode(Constants.PayOrder.CODE_NO_NEED_REFUND);
		result.setResultMsg("退款对应支付单状态非正常。");
		return result;
	    }
	} else {
	    result.setResultMsg(getPayOrderResult.getResultMsg());
	    return result;
	}

	Result<RefundOrder> createRefundOrderResult = refundOrderService.createRefundOrder(payOrder, operationVO);
	if (!createRefundOrderResult.isSuccess()) {
	    logger.error("createRefundOrder error msg : " + createRefundOrderResult.getResultMsg());
	    return result;
	}
	RefundOrder refundOrder = createRefundOrderResult.getModule();

	// 退款
	Result<Boolean> refundMoneyResult = acctService.refundMoney(refundOrder.getId());
	if (refundMoneyResult.isSuccess()) {
	    result.setSuccess(true);
	    result.setModule(refundOrder);
	} else {
	    result.setResultMsg(refundMoneyResult.getResultMsg());
	    logger.error("refundMoney error payOrderId " + payOrderId);
	}
	return result;
    }

    @Override
    public Result<Boolean> commitCharge(ChargeOrder chargeOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	Result<Boolean> addMoneyResult = acctService.addMoney(chargeOrder);
	if (addMoneyResult.isSuccess()) {
	    result.setSuccess(true);
	    result.setModule(true);
	} else {
	    result.setResultMsg(addMoneyResult.getResultMsg());
	}

	return result;
    }

    /*
     * 对成功未到帐的订单，进行调账处理，即退款给代理商。
     */
    @Override
    public Result<Boolean> adjustPayOrder(Long payOrderId, OperationVO operationVO) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	PayOrder payOrder = null;
	Result<PayOrder> getPayOrderResult = payOrderService.getPayOrder(payOrderId);
	if (!getPayOrderResult.isSuccess()) {
	    result.setResultMsg(getPayOrderResult.getResultMsg());
	    return result;
	}

	payOrder = getPayOrderResult.getModule();
	if (payOrder == null) {
	    result.setResultMsg("调账失败，没有该支付单");
	    return result;
	}
	if (payOrder.getStatus() == null || payOrder.getStatus() != Constants.PayOrder.STATUS_SUCCESS) {
	    result.setResultMsg("调账对应支付单状态非正常。");
	    return result;
	}
	Result<RefundOrder> createRefundOrderResult = refundOrderService.createRefundOrder(payOrder, operationVO);
	if (!createRefundOrderResult.isSuccess()) {
	    logger.error("createRefundOrder error msg : " + createRefundOrderResult.getResultMsg());
	    return result;
	}
	RefundOrder refundOrder = createRefundOrderResult.getModule();
	// 调账
	Result<Boolean> adjustPayOrderResult = null;
	try {
	    adjustPayOrderResult = commitPayDAO.adjustPayOrder(refundOrder.getId(), operationVO);
	} catch (Exception e) {
	    result.setResultMsg(e.getMessage());
	    logger.error("adjustPayOrder error payOrderId " + payOrderId + " msg : " + e.getMessage());
	    return result;
	}
	if (!adjustPayOrderResult.isSuccess()) {
	    result.setResultMsg(adjustPayOrderResult.getResultMsg());
	    logger.error("adjustPayOrder error payOrderId " + payOrderId);
	    return result;
	}

	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    public Result<Boolean> commitCash(CashOrder cashOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	Result<Boolean> deductMoneyResult = acctService.subMoney(cashOrder);
	if (deductMoneyResult.isSuccess()) {
	    result.setSuccess(true);
	    result.setModule(true);
	} else {
	    result.setResultMsg(deductMoneyResult.getResultMsg());
	}

	return result;
    }
}
