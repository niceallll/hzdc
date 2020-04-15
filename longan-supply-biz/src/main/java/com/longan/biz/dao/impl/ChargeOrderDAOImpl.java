package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.ChargeOrderDAO;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.ChargeOrderExample;
import com.longan.biz.dataobject.ChargeOrderQuery;
import com.longan.biz.sumobject.ChargeOrderAmount;

public class ChargeOrderDAOImpl implements ChargeOrderDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(ChargeOrder record) throws SQLException {
	return (Long) sqlMapClient.insert("charge_order.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(ChargeOrder record) throws SQLException {
	int rows = sqlMapClient.update("charge_order.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(ChargeOrderExample example) throws SQLException {
	List list = sqlMapClient.queryForList("charge_order.abatorgenerated_selectByExample", example);
	return list;
    }

    public ChargeOrder selectByPrimaryKey(Long id) throws SQLException {
	ChargeOrder key = new ChargeOrder();
	key.setId(id);
	ChargeOrder record = (ChargeOrder) sqlMapClient.queryForObject("charge_order.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(ChargeOrderExample example) throws SQLException {
	int rows = sqlMapClient.delete("charge_order.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	ChargeOrder key = new ChargeOrder();
	key.setId(id);
	int rows = sqlMapClient.delete("charge_order.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(ChargeOrderExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("charge_order.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(ChargeOrder record, ChargeOrderExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("charge_order.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends ChargeOrderExample {
	private Object record;

	public UpdateByExampleParms(Object record, ChargeOrderExample example) {
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
    public List<ChargeOrder> queryByPage(ChargeOrderQuery chargeOrderQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("charge_order.queryByPageCount", chargeOrderQuery);
	chargeOrderQuery.setTotalItem(count);
	return (List<ChargeOrder>) sqlMapClient.queryForList("charge_order.queryByPage", chargeOrderQuery);
    }

    @Override
    public ChargeOrderAmount querySumAmount(ChargeOrderQuery chargeOrderQuery) throws SQLException {
	return (ChargeOrderAmount) sqlMapClient.queryForObject("charge_order.querySumAmount", chargeOrderQuery);
    }
}