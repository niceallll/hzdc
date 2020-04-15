package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.MobileBelongDAO;
import com.longan.biz.dataobject.MobileBelong;
import com.longan.biz.dataobject.MobileBelongExample;

public class MobileBelongDAOImpl implements MobileBelongDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public void insert(MobileBelong record) throws SQLException {
	sqlMapClient.insert("mobile_belong.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKey(MobileBelong record) throws SQLException {
	int rows = sqlMapClient.update("mobile_belong.abatorgenerated_updateByPrimaryKey", record);
	return rows;
    }

    public int updateByPrimaryKeySelective(MobileBelong record) throws SQLException {
	int rows = sqlMapClient.update("mobile_belong.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(MobileBelongExample example) throws SQLException {
	List list = sqlMapClient.queryForList("mobile_belong.abatorgenerated_selectByExample", example);
	return list;
    }

    public MobileBelong selectByPrimaryKey(Long id) throws SQLException {
	MobileBelong key = new MobileBelong();
	key.setId(id);
	MobileBelong record = (MobileBelong) sqlMapClient.queryForObject("mobile_belong.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(MobileBelongExample example) throws SQLException {
	int rows = sqlMapClient.delete("mobile_belong.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	MobileBelong key = new MobileBelong();
	key.setId(id);
	int rows = sqlMapClient.delete("mobile_belong.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(MobileBelongExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("mobile_belong.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(MobileBelong record, MobileBelongExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("mobile_belong.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends MobileBelongExample {
	private Object record;

	public UpdateByExampleParms(Object record, MobileBelongExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }
}