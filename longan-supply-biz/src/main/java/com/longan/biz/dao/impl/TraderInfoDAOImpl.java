package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.TraderInfoDAO;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.TraderInfoExample;

public class TraderInfoDAOImpl implements TraderInfoDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public long insert(TraderInfo record) throws SQLException {
	return (Long) sqlMapClient.insert("trader_info.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(TraderInfo record) throws SQLException {
	int rows = sqlMapClient.update("trader_info.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(TraderInfoExample example) throws SQLException {
	List list = sqlMapClient.queryForList("trader_info.abatorgenerated_selectByExample", example);
	return list;
    }

    public TraderInfo selectByPrimaryKey(Long id) throws SQLException {
	TraderInfo key = new TraderInfo();
	key.setId(id);
	TraderInfo record = (TraderInfo) sqlMapClient.queryForObject("trader_info.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(TraderInfoExample example) throws SQLException {
	int rows = sqlMapClient.delete("trader_info.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	TraderInfo key = new TraderInfo();
	key.setId(id);
	int rows = sqlMapClient.delete("trader_info.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(TraderInfoExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("trader_info.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(TraderInfo record, TraderInfoExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("trader_info.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends TraderInfoExample {
	private Object record;

	public UpdateByExampleParms(Object record, TraderInfoExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @Override
    public TraderInfo selectByUserId(Long userId) throws SQLException {
	TraderInfo key = new TraderInfo();
	key.setUserId(userId);
	TraderInfo record = (TraderInfo) sqlMapClient.queryForObject("trader_info.abatorgenerated_selectByUserId", key);
	return record;
    }

    @Override
    public TraderInfo selectForUpdate(Long userId) throws SQLException {
	return (TraderInfo) sqlMapClient.queryForObject("trader_info.forUpdateOneRow", userId);
    }
}