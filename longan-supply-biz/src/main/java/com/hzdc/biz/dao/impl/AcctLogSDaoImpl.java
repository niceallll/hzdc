package com.hzdc.biz.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.hzdc.biz.dao.AcctLogSDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dataobject.AcctLog;

public class AcctLogSDaoImpl implements AcctLogSDao {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(AcctLog record) throws SQLException {
	return (Long) sqlMapClient.insert("acct_log_hzdc.insert", record);
    }

    public AcctLog selectByPrimaryKey(Long id) throws SQLException {
	AcctLog key = new AcctLog();
	key.setId(id);
	AcctLog record = (AcctLog) sqlMapClient.queryForObject("acct_log_hzdc.selectByPrimaryKey", key);
	return record;
    }
}
