package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.OperationLogService;
import com.longan.biz.dao.OperationLogDAO;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.OperationLogQuery;
import com.longan.biz.domain.Result;

public class OperationLogServiceImpl extends BaseService implements OperationLogService {
    @Resource
    private OperationLogDAO operationLogDAO;

    @Override
    public Result<OperationLog> createOperationLog(OperationLog operationLog) {
	Result<OperationLog> result = new Result<OperationLog>();
	try {
	    Long id = operationLogDAO.insert(operationLog);
	    if (id != null) {
		operationLog.setId(id);
		result.setSuccess(true);
		result.setModule(operationLog);
	    } else {
		result.setResultMsg("新增操作日志失败");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("新增操作日志失败 msg :" + e.getMessage());
	    logger.error("createNewOperationLog error description : " + operationLog.getDescription(), e);
	}
	return result;
    }

    @Override
    public Result<List<OperationLog>> queryOperationLogList(OperationLogQuery operationLogQuery) {
	Result<List<OperationLog>> result = new Result<List<OperationLog>>();
	try {
	    List<OperationLog> queryResult = operationLogDAO.queryByPage(operationLogQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("操作日志查询失败    msg: " + e.getMessage());
	    logger.error("queryOperationLogList error ", e);
	}
	return result;
    }
}
