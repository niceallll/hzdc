package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.AcctInfoDAO;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AcctInfoExample;

public class AcctInfoDAOImpl implements AcctInfoDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
	public Long insert(AcctInfo record) throws SQLException {
	return (Long) sqlMapClient.insert("acct_info.abatorgenerated_insert", record);
    }

    @Override
	public int updateByPrimaryKeySelective(AcctInfo record) throws SQLException {
	int rows = sqlMapClient.update("acct_info.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @Override
	@SuppressWarnings("rawtypes")
    public List selectByExample(AcctInfoExample example) throws SQLException {
	List list = sqlMapClient.queryForList("acct_info.abatorgenerated_selectByExample", example);
	return list;
    }

    public AcctInfo selectByPrimaryKey(Long id) throws SQLException {
	AcctInfo key = new AcctInfo();
	key.setId(id);
	AcctInfo record = (AcctInfo) sqlMapClient.queryForObject("acct_info.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(AcctInfoExample example) throws SQLException {
	int rows = sqlMapClient.delete("acct_info.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	AcctInfo key = new AcctInfo();
	key.setId(id);
	int rows = sqlMapClient.delete("acct_info.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(AcctInfoExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("acct_info.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(AcctInfo record, AcctInfoExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("acct_info.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends AcctInfoExample {
	private Object record;

	public UpdateByExampleParms(Object record, AcctInfoExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @Override
    public AcctInfo selectForUpdate(Long id) throws SQLException {
	return (AcctInfo) sqlMapClient.queryForObject("acct_info.forUpdateOneRow", id);
    }
}