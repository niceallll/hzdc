package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.MobileQblibDAO;
import com.longan.biz.dataobject.MobileQblib;
import com.longan.biz.dataobject.MobileQblibExample;

public class MobileQblibDAOImpl implements MobileQblibDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public String selectMobileByAreaCode(String areaCode) throws SQLException {
	String mobile = (String) sqlMapClient.queryForObject("mobile_qblib.abatorgenerated_mobileByAreaCode", areaCode);
	return mobile;
    }

    public void insert(MobileQblib record) throws SQLException {
	sqlMapClient.insert("mobile_qblib.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKey(MobileQblib record) throws SQLException {
	int rows = sqlMapClient.update("mobile_qblib.abatorgenerated_updateByPrimaryKey", record);
	return rows;
    }

    public int updateByPrimaryKeySelective(MobileQblib record) throws SQLException {
	int rows = sqlMapClient.update("mobile_qblib.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(MobileQblibExample example) throws SQLException {
	List list = sqlMapClient.queryForList("mobile_qblib.abatorgenerated_selectByExample", example);
	return list;
    }

    public MobileQblib selectByPrimaryKey(Long id) throws SQLException {
	MobileQblib key = new MobileQblib();
	key.setId(id);
	MobileQblib record = (MobileQblib) sqlMapClient.queryForObject("mobile_qblib.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(MobileQblibExample example) throws SQLException {
	int rows = sqlMapClient.delete("mobile_qblib.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	MobileQblib key = new MobileQblib();
	key.setId(id);
	int rows = sqlMapClient.delete("mobile_qblib.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(MobileQblibExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("mobile_qblib.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(MobileQblib record, MobileQblibExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("mobile_qblib.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends MobileQblibExample {
	private Object record;

	public UpdateByExampleParms(Object record, MobileQblibExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }
}
