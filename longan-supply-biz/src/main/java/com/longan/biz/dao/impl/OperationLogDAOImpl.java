package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.OperationLogDAO;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.OperationLogQuery;

public class OperationLogDAOImpl implements OperationLogDAO {
	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public Long insert(OperationLog record) throws SQLException {
		return (Long) sqlMapClient.insert("operation_log.abatorgenerated_insert", record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationLog> queryByPage(OperationLogQuery operationLogQuery) throws SQLException {
		int count = (Integer) sqlMapClient.queryForObject("operation_log.queryByPageCount",
				operationLogQuery);
		operationLogQuery.setTotalItem(count);
		return (List<OperationLog>) sqlMapClient.queryForList("operation_log.queryByPage",
				operationLogQuery);
	}

}
