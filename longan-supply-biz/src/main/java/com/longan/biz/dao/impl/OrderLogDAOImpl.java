package com.longan.biz.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.OrderLogDAO;
import com.longan.biz.dataobject.OrderLog;
import com.longan.biz.dataobject.OrderLogExample;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

public class OrderLogDAOImpl implements OrderLogDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public void insert(OrderLog record) throws SQLException {
	sqlMapClient.insert("order_log.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(OrderLog record) throws SQLException {
	int rows = sqlMapClient.update("order_log.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(OrderLogExample example) throws SQLException {
	List list = sqlMapClient.queryForList("order_log.abatorgenerated_selectByExample", example);
	return list;
    }

    public OrderLog selectByPrimaryKey(Long id) throws SQLException {
	OrderLog key = new OrderLog();
	key.setId(id);
	OrderLog record = (OrderLog) sqlMapClient.queryForObject("order_log.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(OrderLogExample example) throws SQLException {
	int rows = sqlMapClient.delete("order_log.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	OrderLog key = new OrderLog();
	key.setId(id);
	int rows = sqlMapClient.delete("order_log.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(OrderLogExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("order_log.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(OrderLog record, OrderLogExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("order_log.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends OrderLogExample {
	private Object record;

	public UpdateByExampleParms(Object record, OrderLogExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }
}