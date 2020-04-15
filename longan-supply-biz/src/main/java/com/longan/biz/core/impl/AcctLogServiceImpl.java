package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.AcctLogService;
import com.longan.biz.core.BaseService;
import com.longan.biz.dao.AcctLogDAO;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.AcctLogAmount;

public class AcctLogServiceImpl extends BaseService implements AcctLogService {
    @Resource
    private AcctLogDAO acctLogDAO;

    @Override
    public Result<List<AcctLog>> queryAcctLog(AcctLogQuery acctLogQuery) {
	Result<List<AcctLog>> result = new Result<List<AcctLog>>();
	try {
	    List<AcctLog> queryResult = acctLogDAO.queryByPage(acctLogQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("资金流水查询失败    msg: " + e.getMessage());
	    logger.error("queryAcctLog error ", e);
	}
	return result;
    }

    @Override
    public Result<AcctLogAmount> queryAcctLogSum(AcctLogQuery acctLogQuery) {
	Result<AcctLogAmount> result = new Result<AcctLogAmount>();
	if (acctLogQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    AcctLogAmount amount = acctLogDAO.querySumAmount(acctLogQuery);
	    result.setSuccess(true);
	    result.setModule(amount);
	} catch (SQLException e) {
	    result.setResultMsg("账户流水统计失败    msg: " + e.getMessage());
	    logger.error("queryAcctLogSum error ", e);
	}
	return result;
    }

    @Override
    public Result<Integer> getCountInExport(AcctLogQuery acctLogQuery) {
	Result<Integer> result = new Result<Integer>();
	try {
	    Integer count = acctLogDAO.countByExport(acctLogQuery);
	    result.setSuccess(true);
	    result.setModule(count);
	} catch (SQLException e) {
	    result.setResultMsg("订单查询失败    msg: " + e.getMessage());
	    logger.error("getCountInExport error ", e);
	}
	return result;
    }

    @Override
    public Result<List<AcctLog>> queryAcctLogExport(AcctLogQuery acctLogQuery) {
	Result<List<AcctLog>> result = new Result<List<AcctLog>>();
	try {
	    List<AcctLog> queryResult = acctLogDAO.queryByExport(acctLogQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("账户资金流水查询失败    msg: " + e.getMessage());
	    logger.error("queryAcctLogPage error ", e);
	}
	return result;
    }

    @Override
    public Result<List<AcctLog>> getAcctLogExport(AcctLogQuery acctLogQuery) {
	Result<List<AcctLog>> result = new Result<List<AcctLog>>();
	try {
	    List<AcctLog> queryResult = acctLogDAO.selectByExport(acctLogQuery);
	    if (queryResult.size() > 0) {
		result.setSuccess(true);
		result.setModule(queryResult);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("账户资金流水查询失败    msg: " + e.getMessage());
	    logger.error("getAcctLogExport error ", e);
	}
	return result;
    }
}
