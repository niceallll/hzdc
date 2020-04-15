package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;

public interface QueryAcctLogDAO {

	List<AcctLog> queryByPage(AcctLogQuery acctLogQuery) throws SQLException;

	public List<AcctLog> queryByExport(AcctLogQuery acctLogQuery) throws SQLException;

}