package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.RefundOrderService;
import com.longan.biz.dao.RefundOrderDAO;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.RefundOrderAmount;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class RefundOrderServiceImpl extends BaseService implements RefundOrderService {
    @Resource
    private RefundOrderDAO refundOrderDAO;

    @Override
    public Result<RefundOrder> createRefundOrder(PayOrder payOrder, OperationVO operationVO) {
	Result<RefundOrder> result = new Result<RefundOrder>();
	if (payOrder == null) {
	    result.setResultMsg("payOrder is null");
	    return result;
	}

	RefundOrder refundOrder = new RefundOrder();
	refundOrder.setAcctDate(DateTool.parseDate8(new Date()));
	refundOrder.setAcctId(payOrder.getAcctId());
	refundOrder.setAmount(payOrder.getAmount());
	refundOrder.setAmountDummy(payOrder.getAmountDummy());
	refundOrder.setBizId(payOrder.getBizId());
	refundOrder.setBizOrderId(payOrder.getBizOrderId());
	refundOrder.setItemId(payOrder.getItemId());
	refundOrder.setPayOrderId(payOrder.getId());
	refundOrder.setStatus(Constants.RefundOrder.STATUS_INIT);
	refundOrder.setUserId(payOrder.getUserId());
	refundOrder.setSupplyItemId(payOrder.getSupplyItemId());
	refundOrder.setSupplyTraderId(payOrder.getSupplyTraderId());

	if (operationVO != null) {
	    // 管理员操作
	    refundOrder.setOperId(operationVO.getUserInfo().getId());
	    refundOrder.setOperName(operationVO.getUserInfo().getUserName());
	    refundOrder.setMemo(operationVO.getOperationMemo());
	    refundOrder.setPayType(Constants.RefundOrder.PAY_TYPE_OPERATOR);
	} else {
	    refundOrder.setPayType(Constants.RefundOrder.PAY_TYPE_SYSTEM);
	}
	try {
	    Long refundOrderId = refundOrderDAO.insert(refundOrder);
	    refundOrder.setId(refundOrderId);
	    result.setSuccess(true);
	    result.setModule(refundOrder);
	} catch (SQLException e) {
	    result.setResultMsg("创建支付单失败 msg : " + e.getMessage());
	    logger.error("createRefundOrder error payOrderId = " + payOrder.getId() + " msg : " + e.getMessage());
	}
	return result;
    }

    @Override
    public Result<RefundOrder> getRefundOrder(Long refundOrderId) {
	Result<RefundOrder> result = new Result<RefundOrder>();
	try {
	    RefundOrder refundOrder = refundOrderDAO.selectByPrimaryKey(refundOrderId);
	    result.setSuccess(true);
	    result.setModule(refundOrder);
	} catch (SQLException e) {
	    result.setResultMsg("查询退款单异常 msg : " + e.getMessage());
	    logger.error("getRefundOrder error refundOrderId = " + refundOrderId, e);
	}
	return result;
    }

    @Override
    public Result<List<RefundOrder>> queryRefundOrder(RefundOrderQuery refundOrderQuery) {
	Result<List<RefundOrder>> result = new Result<List<RefundOrder>>();
	try {
	    List<RefundOrder> queryResult = refundOrderDAO.queryByPage(refundOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("退款单查询失败    msg: " + e.getMessage());
	    logger.error("queryRefundOrder error ", e);
	}

	return result;
    }

    @Override
    public Result<Boolean> updateRefundOrderWhenException(Long refundOrderId, String errorMsg) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    RefundOrder refundOrder = refundOrderDAO.selectByPrimaryKey(refundOrderId);
	    if (refundOrder == null) {
		result.setResultMsg("没有该退款单");
		return result;
	    }

	    if (refundOrder.getStatus() != Constants.RefundOrder.STATUS_INIT) {
		logger.warn("updateRefundOrderWhenException error 退款单状态非正常");
		result.setResultMsg("退款单状态非正常");
		return result;
	    }
	    refundOrder.setStatus(Constants.RefundOrder.STATUS_FAILED);
	    refundOrder.setErrorMsg(errorMsg);
	    refundOrderDAO.updateByPrimaryKeySelective(refundOrder);
	    result.setSuccess(true);
	    result.setModule(true);

	} catch (SQLException e) {
	    logger.error("updateRefundOrderWhenException refundOrderId : " + refundOrderId, e);
	    result.setResultMsg("异常退款单更新失败 msg : " + e.getMessage());
	}
	return result;
    }

    @Override
    public Result<Integer> getCountInExport(RefundOrderQuery refundOrderQuery) {
	Result<Integer> result = new Result<Integer>();
	try {
	    Integer count = refundOrderDAO.countByExport(refundOrderQuery);
	    result.setSuccess(true);
	    result.setModule(count);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("getCountInExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<RefundOrder>> queryRefundOrderExport(RefundOrderQuery refundOrderQuery) {
	Result<List<RefundOrder>> result = new Result<List<RefundOrder>>();
	try {
	    List<RefundOrder> queryResult = refundOrderDAO.queryByExport(refundOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("退款单查询失败    msg: " + e.getMessage());
	    logger.error("queryRefundOrder error ", e);
	}
	return result;
    }

    @Override
    public Result<List<RefundOrder>> getRefundOrderExport(RefundOrderQuery refundOrderQuery) {
	Result<List<RefundOrder>> result = new Result<List<RefundOrder>>();
	try {
	    List<RefundOrder> queryResult = refundOrderDAO.selectByExport(refundOrderQuery);
	    if (queryResult.size() > 0) {
		result.setSuccess(true);
		result.setModule(queryResult);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("退款单查询失败    msg: " + e.getMessage());
	    logger.error("getRefundOrderExport error ", e);
	}
	return result;
    }

    @Override
    public Result<RefundOrderAmount> queryRefundOrderSum(RefundOrderQuery refundOrderQuery) {
	Result<RefundOrderAmount> result = new Result<RefundOrderAmount>();
	if (refundOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    RefundOrderAmount amount = refundOrderDAO.querySumAmount(refundOrderQuery);
	    result.setSuccess(true);
	    result.setModule(amount);
	} catch (SQLException e) {
	    result.setResultMsg("退款单统计失败    msg: " + e.getMessage());
	    logger.error("queryRefundOrderSum error ", e);
	}
	return result;
    }
}
