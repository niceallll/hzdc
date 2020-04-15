package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.AreaInfoDAO;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.AreaInfoExample;

public class AreaInfoDAOImpl implements AreaInfoDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public void insert(AreaInfo record) throws SQLException {
	sqlMapClient.insert("area_info.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKey(AreaInfo record) throws SQLException {
	int rows = sqlMapClient.update("area_info.abatorgenerated_updateByPrimaryKey", record);
	return rows;
    }

    public int updateByPrimaryKeySelective(AreaInfo record) throws SQLException {
	int rows = sqlMapClient.update("area_info.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(AreaInfoExample example) throws SQLException {
	List list = sqlMapClient.queryForList("area_info.abatorgenerated_selectByExample", example);
	return list;
    }

    public AreaInfo selectByPrimaryKey(Long id) throws SQLException {
	AreaInfo key = new AreaInfo();
	key.setId(id);
	AreaInfo record = (AreaInfo) sqlMapClient.queryForObject("area_info.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(AreaInfoExample example) throws SQLException {
	int rows = sqlMapClient.delete("area_info.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	AreaInfo key = new AreaInfo();
	key.setId(id);
	int rows = sqlMapClient.delete("area_info.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(AreaInfoExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("area_info.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(AreaInfo record, AreaInfoExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("area_info.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends AreaInfoExample {
	private Object record;

	public UpdateByExampleParms(Object record, AreaInfoExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }
}