package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.CashOrderService;
import com.longan.biz.core.PayService;
import com.longan.biz.dao.CashOrderDAO;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.CashOrderQuery;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.CashOrderAmount;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class CashOrderServiceImpl extends BaseService implements CashOrderService {
    @Resource
    private CashOrderDAO cashOrderDAO;

    @Resource
    private PayService pagService;

    @Override
    public Result<List<CashOrder>> queryCashOrder(CashOrderQuery cashOrderQuery) {
	Result<List<CashOrder>> result = new Result<List<CashOrder>>();
	try {
	    List<CashOrder> queryResult = cashOrderDAO.queryByPage(cashOrderQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("提现单查询失败    msg: " + e.getMessage());
	    logger.error("queryCashOrder error ", e);
	}

	return result;
    }

    @Override
    public Result<CashOrderAmount> queryCashOrderSum(CashOrderQuery cashOrderQuery) {
	Result<CashOrderAmount> result = new Result<CashOrderAmount>();
	if (cashOrderQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    CashOrderAmount amount = cashOrderDAO.querySumAmount(cashOrderQuery);
	    result.setSuccess(true);
	    result.setModule(amount);
	} catch (SQLException e) {
	    result.setResultMsg("提现单统计失败    msg: " + e.getMessage());
	    logger.error("queryCashOrderSum error ", e);
	}
	return result;
    }

    @Override
    public Result<CashOrder> createCashOrder(OperationVO operationVO, CashOrder cashOrder) {
	Result<CashOrder> result = new Result<CashOrder>();
	try {
	    cashOrder.setOperId(operationVO.getUserInfo().getId());
	    cashOrder.setOperName(operationVO.getUserInfo().getUserName());
	    cashOrder.setStatus(Constants.CashOrder.STATUS_INIT);
	    cashOrder.setAcctDate(DateTool.parseDate8(new Date()));
	    Long id = cashOrderDAO.insert(cashOrder);
	    cashOrder.setId(id);
	    result.setSuccess(true);
	    result.setModule(cashOrder);
	} catch (SQLException e) {
	    result.setResultMsg("创建提现单异常 msg : " + e.getMessage());
	    logger.error("createCashOrder error ", e);
	}
	return result;
    }

    @Override
    public Result<CashOrder> getCashOrder(Long id) {
	Result<CashOrder> result = new Result<CashOrder>();
	try {
	    CashOrder cashOrder = cashOrderDAO.selectByPrimaryKey(id);
	    result.setSuccess(true);
	    result.setModule(cashOrder);
	} catch (SQLException e) {
	    result.setResultMsg("查询提现单异常 msg : " + e.getMessage());
	    logger.error("getCashOrder error id : " + id, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> verifyCashOrder(OperationVO operationVO, Long cashOrderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	CashOrder cashOrder = null;
	try {
	    cashOrder = cashOrderDAO.selectByPrimaryKey(cashOrderId);
	    if (cashOrder == null) {
		result.setResultMsg("没有该提现单");
		return result;
	    }
	} catch (SQLException e) {
	    result.setResultMsg("审核提现单异常,查询提现单出错 msg : " + e.getMessage());
	    logger.error("getCashOrder error cashOrderId : " + cashOrderId, e);
	    return result;
	}

	if (cashOrder.getStatus() != Constants.CashOrder.STATUS_INIT) {
	    result.setModule(false);
	    result.setResultMsg("提现单状态非正常");
	    return result;
	}

	cashOrder.setVerifyOperName(operationVO.getUserInfo().getUserName());

	Result<Boolean> commitCash = pagService.commitCash(cashOrder);
	if (commitCash.isSuccess() && commitCash.getModule()) {
	    result.setSuccess(true);
	    result.setModule(true);
	} else {
	    result.setResultMsg(commitCash.getResultMsg());
	}

	return result;
    }

    @Override
    public Result<Boolean> cancelCashOrder(OperationVO operationVO, Long cashOrderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	CashOrder cashOrder = null;
	try {
	    cashOrder = cashOrderDAO.selectByPrimaryKey(cashOrderId);
	    if (cashOrder == null) {
		result.setResultMsg("没有该提现单");
		return result;
	    }
	} catch (SQLException e) {
	    result.setResultMsg("取消提现单异常,查询提现单出错 msg : " + e.getMessage());
	    logger.error("getCashOrder error cashOrderId : " + cashOrderId, e);
	    return result;
	}

	if (cashOrder.getStatus() != Constants.CashOrder.STATUS_INIT) {
	    result.setResultMsg("提现单状态非正常");
	    return result;
	}

	cashOrder.setOperId(operationVO.getUserInfo().getId());
	cashOrder.setOperName(operationVO.getUserInfo().getUserName());
	cashOrder.setStatus(Constants.CashOrder.STATUS_FAILED);
	try {
	    cashOrderDAO.updateByPrimaryKeySelective(cashOrder);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (SQLException e) {
	    result.setResultMsg("取消提现单异常,更新提现单出错 msg : " + e.getMessage());
	    logger.error("updateCashOrder error cashOrderId : " + cashOrderId, e);
	    return result;
	}
	return result;
    }
}
