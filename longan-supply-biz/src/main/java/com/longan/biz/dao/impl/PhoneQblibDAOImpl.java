package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.PhoneQblibDAO;
import com.longan.biz.dataobject.PhoneQblib;
import com.longan.biz.dataobject.PhoneQblibExample;

public class PhoneQblibDAOImpl implements PhoneQblibDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public String selectPhoneByAreaCode(String areaCode) throws SQLException {
	String phone = (String) sqlMapClient.queryForObject("phone_qblib.abatorgenerated_phoneByAreaCode", areaCode);
	return phone;
    }

    public void insert(PhoneQblib record) throws SQLException {
	sqlMapClient.insert("phone_qblib.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKey(PhoneQblib record) throws SQLException {
	int rows = sqlMapClient.update("phone_qblib.abatorgenerated_updateByPrimaryKey", record);
	return rows;
    }

    public int updateByPrimaryKeySelective(PhoneQblib record) throws SQLException {
	int rows = sqlMapClient.update("phone_qblib.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(PhoneQblibExample example) throws SQLException {
	List list = sqlMapClient.queryForList("phone_qblib.abatorgenerated_selectByExample", example);
	return list;
    }

    public PhoneQblib selectByPrimaryKey(Long id) throws SQLException {
	PhoneQblib key = new PhoneQblib();
	key.setId(id);
	PhoneQblib record = (PhoneQblib) sqlMapClient.queryForObject("phone_qblib.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(PhoneQblibExample example) throws SQLException {
	int rows = sqlMapClient.delete("phone_qblib.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	PhoneQblib key = new PhoneQblib();
	key.setId(id);
	int rows = sqlMapClient.delete("phone_qblib.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(PhoneQblibExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("phone_qblib.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(PhoneQblib record, PhoneQblibExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("phone_qblib.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends PhoneQblibExample {
	private Object record;

	public UpdateByExampleParms(Object record, PhoneQblibExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }
}
