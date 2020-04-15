package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.PayOrderService;
import com.longan.biz.core.UserService;
import com.longan.biz.dao.PayOrderDAO;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.PayOrderAmount;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class PayOrderServiceImpl extends BaseService implements PayOrderService {
    @Resource
    private PayOrderDAO payOrderDAO;

    @Resource
    private UserService userService;

    @Override
    public Result<PayOrder> createPayOrder(BizOrder bizOrder) {
	Result<PayOrder> result = new Result<PayOrder>();
	if (bizOrder == null) {
	    result.setResultMsg("bizOrder is null");
	    return result;
	}

	Result<UserInfo> userInfoResult = userService.getUserInfo(bizOrder.getUserId());
	UserInfo userInfo = null;
	if (userInfoResult.isSuccess()) {
	    userInfo = userInfoResult.getModule();
	} else {
	    result.setResultMsg(userInfoResult.getResultMsg());
	    return result;
	}
	Result<Boolean> checkUserResult = userService.checkUserInfo(userInfo);
	if (!checkUserResult.isSuccess() || !checkUserResult.getModule()) {
	    result.setResultMsg(checkUserResult.getResultMsg());
	    return result;
	}

	PayOrder payOrder = new PayOrder();
	payOrder.setAcctDate(DateTool.parseDate8(new Date()));
	payOrder.setAcctId(userInfo.getAcctId());
	payOrder.setAmount(bizOrder.getAmount());
	payOrder.setAmountDummy(bizOrder.getAmountDummy());
	payOrder.setBizId(bizOrder.getBizId());
	payOrder.setBizOrderId(bizOrder.getId());
	payOrder.setChannle(bizOrder.getChannel());
	payOrder.setItemId(bizOrder.getItemId());
	payOrder.setPayMode(Constants.PayOrder.MODE_TRUST_PAY); // 目前只有trustpay
	payOrder.setPayType(Constants.PayOrder.TYPE_PAY_BALANCE);
	payOrder.setUserId(userInfo.getId());
	payOrder.setStatus(Constants.PayOrder.STATUS_INIT);
	if (StringUtils.isNotBlank(bizOrder.getUpstreamId()))
	    payOrder.setSupplyTraderId(Integer.parseInt(bizOrder.getUpstreamId()));
	try {
	    Long payOrderId = payOrderDAO.insert(payOrder);
	    result.setSuccess(true);
	    payOrder.setId(payOrderId);
	    result.setModule(payOrder);
	} catch (SQLException e) {
	    logger.error("payOrder insert error! bizOrderId = " + bizOrder.getId(), e);
	    result.setResultMsg("支付单创建失败  msg : " + e.getMessage());
	}
	return result;
    }

    @Override
    public Result<Boolean> updatePayOrder(PayOrder payOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (payOrder == null) {
	    result.setResultMsg("payOrder is null");
	    return result;
	}
	try {
	    payOrderDAO.updateByPrimaryKeySelective(payOrder);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (SQLException e) {
	    result.setResultMsg("payOrder update error msg : " + e.getMessage());
	    logger.error("payOrder update error! payOrderId = " + payOrder.getId(), e);
	}
	return result;
    }

    @Override
    public Result<PayOrder> getPayOrder(Long payOrderId) {
	Result<PayOrder> result = new Result<PayOrder>();
	try {
	    PayOrder payOrder = payOrderDAO.selectByPrimaryKey(payOrderId);
	    result.setSuccess(true);
	    result.setModule(payOrder);
	} catch (SQLException e) {
	    result.setResultMsg("getPayOrder error msg : " + e.getMessage());
	    logger.error("getPayOrder error! payOrderId = " + payOrderId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> updatePayOrderWhenException(Long payOrderId, String errorMsg) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	try {
	    PayOrder payOrder = payOrderDAO.selectByPrimaryKey(payOrderId);
	    if (payOrder == null) {
		result.setResultMsg("没有该支付单");
		return result;
	    }
	    if (payOrder.getStatus() != Constants.PayOrder.STATUS_INIT) {
		logger.warn("updatePayOrderWhenException error 支付单状态非正常");
		result.setResultMsg("支付单状态非正常");
		return result;
	    }

	    payOrder.setStatus(Constants.PayOrder.STATUS_FAILED);
	    payOrder.setErrorMsg(errorMsg);
	    payOrderDAO.updateByPrimaryKeySelective(payOrder);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (SQLException e) {
	    logger.error("updatePayOrderWhenException payOrderId : " + payOrderId, e);
	    result.setResultMsg("异常支付单更新失败 msg : " + e.getMessage());
	}
	return result;
    }

    @Override
    public Result<List<PayOrder>> queryPayOrder(PayOrderQuery payOrderQuery) {
	Result<List<PayOrder>> result = new Result<List<PayOrder>>();
	try {
	    List<PayOrder> queryResult = payOrderDAO.queryByPage(payOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("支付单查询失败    msg: " + e.getMessage());
	    logger.error("queryPayOrder error ", e);
	}
	return result;
    }

    @Override
    public Result<PayOrderAmount> queryPayOrderSum(PayOrderQuery payOrderQuery) {
	Result<PayOrderAmount> result = new Result<PayOrderAmount>();
	if (payOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    PayOrderAmount amount = payOrderDAO.querySumAmount(payOrderQuery);
	    result.setSuccess(true);
	    result.setModule(amount);
	} catch (SQLException e) {
	    result.setResultMsg("支付单统计失败    msg: " + e.getMessage());
	    logger.error("queryChargeOrderSum error ", e);
	}
	return result;
    }
}
