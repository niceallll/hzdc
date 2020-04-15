package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.ChargeOrderService;
import com.longan.biz.core.PayService;
import com.longan.biz.dao.ChargeOrderDAO;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.ChargeOrderQuery;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.ChargeOrderAmount;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class ChargeOrderServiceImpl extends BaseService implements ChargeOrderService {
    @Resource
    private ChargeOrderDAO chargeOrderDAO;

    @Resource
    private PayService pagService;

    @Override
    public Result<List<ChargeOrder>> queryChargeOrder(ChargeOrderQuery chargeOrderQuery) {
	Result<List<ChargeOrder>> result = new Result<List<ChargeOrder>>();
	try {
	    List<ChargeOrder> queryResult = chargeOrderDAO.queryByPage(chargeOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("充值单查询失败    msg: " + e.getMessage());
	    logger.error("queryChargeOrder error ", e);
	}

	return result;
    }

    @Override
    public Result<ChargeOrderAmount> queryChargeOrderSum(ChargeOrderQuery chargeOrderQuery) {
	Result<ChargeOrderAmount> result = new Result<ChargeOrderAmount>();
	if (chargeOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    ChargeOrderAmount amount = chargeOrderDAO.querySumAmount(chargeOrderQuery);
	    result.setSuccess(true);
	    result.setModule(amount);
	} catch (SQLException e) {
	    result.setResultMsg("充值单统计失败    msg: " + e.getMessage());
	    logger.error("queryChargeOrderSum error ", e);
	}
	return result;
    }

    @Override
    public Result<ChargeOrder> getChargeOrder(Long id) {
	Result<ChargeOrder> result = new Result<ChargeOrder>();
	try {
	    ChargeOrder chargeOrder = chargeOrderDAO.selectByPrimaryKey(id);
	    result.setSuccess(true);
	    result.setModule(chargeOrder);
	} catch (SQLException e) {
	    result.setResultMsg("查询充值单异常 msg : " + e.getMessage());
	    logger.error("getChargeOrder error id : " + id, e);
	}
	return result;
    }

    @Override
    public Result<ChargeOrder> createChargeOrder(OperationVO operationVO, ChargeOrder chargeOrder) {
	Result<ChargeOrder> result = new Result<ChargeOrder>();
	try {
	    chargeOrder.setOperId(operationVO.getUserInfo().getId());
	    chargeOrder.setOperName(operationVO.getUserInfo().getUserName());
	    chargeOrder.setStatus(Constants.ChargeOrder.STATUS_INIT);
	    chargeOrder.setAcctDate(DateTool.parseDate8(new Date()));
	    Long id = chargeOrderDAO.insert(chargeOrder);
	    chargeOrder.setId(id);
	    result.setSuccess(true);
	    result.setModule(chargeOrder);
	} catch (SQLException e) {
	    result.setResultMsg("创建充值单异常 msg : " + e.getMessage());
	    logger.error("createChargeOrder error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> verifyChargeOrder(OperationVO operationVO, Long chargeOrderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	ChargeOrder chargeOrder = null;
	try {
	    chargeOrder = chargeOrderDAO.selectByPrimaryKey(chargeOrderId);
	    if (chargeOrder == null) {
		result.setResultMsg("没有该充值单");
		return result;
	    }
	} catch (SQLException e) {
	    result.setResultMsg("审核充值单异常,查询充值单出错 msg : " + e.getMessage());
	    logger.error("getChargeOrder error chargeOrderId : " + chargeOrderId, e);
	    return result;
	}

	if (chargeOrder.getStatus() != Constants.ChargeOrder.STATUS_INIT) {
	    result.setModule(false);
	    result.setResultMsg("充值单状态非正常");
	    return result;
	}

	chargeOrder.setVerifyOperName(operationVO.getUserInfo().getUserName());
	Result<Boolean> commitCharge = pagService.commitCharge(chargeOrder);
	if (commitCharge.isSuccess() && commitCharge.getModule()) {
	    result.setSuccess(true);
	    result.setModule(true);
	} else {
	    result.setResultMsg(commitCharge.getResultMsg());
	}

	return result;
    }

    @Override
    public Result<Boolean> cancelChargeOrder(OperationVO operationVO, Long chargeOrderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	ChargeOrder chargeOrder = null;
	try {
	    chargeOrder = chargeOrderDAO.selectByPrimaryKey(chargeOrderId);
	    if (chargeOrder == null) {
		result.setResultMsg("没有该充值单");
		return result;
	    }
	} catch (SQLException e) {
	    result.setResultMsg("取消充值单异常,查询充值单出错 msg : " + e.getMessage());
	    logger.error("getChargeOrder error chargeOrderId : " + chargeOrderId, e);
	    return result;
	}

	if (chargeOrder.getStatus() != Constants.ChargeOrder.STATUS_INIT) {
	    result.setResultMsg("充值单状态非正常");
	    return result;
	}

	chargeOrder.setOperId(operationVO.getUserInfo().getId());
	chargeOrder.setOperName(operationVO.getUserInfo().getUserName());
	chargeOrder.setStatus(Constants.ChargeOrder.STATUS_FAILED);
	try {
	    chargeOrderDAO.updateByPrimaryKeySelective(chargeOrder);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (SQLException e) {
	    result.setResultMsg("取消充值单异常,更新充值单出错 msg : " + e.getMessage());
	    logger.error("updateChargeOrder error chargeOrderId : " + chargeOrderId, e);
	    return result;
	}
	return result;
    }

    @Override
    public Result<Boolean> updateChargeOrderWhenException(Long chargeOrderId, String errorMsg) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    ChargeOrder chargeOrder = chargeOrderDAO.selectByPrimaryKey(chargeOrderId);
	    if (chargeOrder == null) {
		result.setResultMsg("没有该充值单");
		return result;
	    }
	    if (chargeOrder.getStatus() != Constants.ChargeOrder.STATUS_INIT) {
		logger.warn("updateChargeOrderWhenException error 支付单状态必须是创建中");
		result.setResultMsg("支付单状态必须是创建中");
		return result;
	    }
	    chargeOrder.setStatus(Constants.RefundOrder.STATUS_FAILED);
	    chargeOrder.setErrorMsg(errorMsg);
	    chargeOrderDAO.updateByPrimaryKeySelective(chargeOrder);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (SQLException e) {
	    logger.error("updateChargeOrderWhenException chargeOrderId : " + chargeOrderId, e);
	    result.setResultMsg("异常充值单更新失败 msg : " + e.getMessage());
	    return result;
	}
	return result;
    }
}
