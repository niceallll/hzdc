package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.QueryAcctLogDAO;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;

public class QueryAcctLogDAOImpl implements QueryAcctLogDAO {

    @Resource
    private SqlMapClient querySqlMapClient;

    @SuppressWarnings("unchecked")
    @Override
    public List<AcctLog> queryByPage(AcctLogQuery acctLogQuery) throws SQLException {
	int count = (Integer) querySqlMapClient.queryForObject("acct_log.queryForPageCount", acctLogQuery);
	acctLogQuery.setTotalItem(count);
	return (List<AcctLog>) querySqlMapClient.queryForList("acct_log.queryForPage", acctLogQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AcctLog> queryByExport(AcctLogQuery acctLogQuery) throws SQLException {
	return (List<AcctLog>) querySqlMapClient.queryForList("acct_log.queryByExport", acctLogQuery);
    }
}