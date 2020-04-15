package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.AcctLogDAO;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogExample;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.sumobject.AcctLogAmount;

public class AcctLogDAOImpl implements AcctLogDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(AcctLog record) throws SQLException {
	return (Long) sqlMapClient.insert("acct_log.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(AcctLog record) throws SQLException {
	int rows = sqlMapClient.update("acct_log.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(AcctLogExample example) throws SQLException {
	List list = sqlMapClient.queryForList("acct_log.abatorgenerated_selectByExample", example);
	return list;
    }

    public AcctLog selectByPrimaryKey(Long id) throws SQLException {
	AcctLog key = new AcctLog();
	key.setId(id);
	AcctLog record = (AcctLog) sqlMapClient.queryForObject("acct_log.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(AcctLogExample example) throws SQLException {
	int rows = sqlMapClient.delete("acct_log.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	AcctLog key = new AcctLog();
	key.setId(id);
	int rows = sqlMapClient.delete("acct_log.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(AcctLogExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("acct_log.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(AcctLog record, AcctLogExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("acct_log.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends AcctLogExample {
	private Object record;

	public UpdateByExampleParms(Object record, AcctLogExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AcctLog> queryByPage(AcctLogQuery acctLogQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("acct_log.queryForPageCount", acctLogQuery);
	acctLogQuery.setTotalItem(count);
	return (List<AcctLog>) sqlMapClient.queryForList("acct_log.queryForPage", acctLogQuery);
    }

    @Override
    public int countByExport(AcctLogQuery acctLogQuery) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("acct_log.countByExport", acctLogQuery);
	return count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AcctLog> queryByExport(AcctLogQuery acctLogQuery) throws SQLException {
	return (List<AcctLog>) sqlMapClient.queryForList("acct_log.queryByExport", acctLogQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AcctLog> selectByExport(AcctLogQuery acctLogQuery) throws SQLException {
	return (List<AcctLog>) sqlMapClient.queryForList("acct_log.selectByExport", acctLogQuery);
    }

    @Override
    public AcctLogAmount querySumAmount(AcctLogQuery acctLogQuery) throws SQLException {
	return (AcctLogAmount) sqlMapClient.queryForObject("acct_log.querySumAmount", acctLogQuery);
    }
}