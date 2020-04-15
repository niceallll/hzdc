package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.PayOrderDAO;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderExample;
import com.longan.biz.dataobject.PayOrderQuery;
import com.longan.biz.sumobject.PayOrderAmount;

public class PayOrderDAOImpl implements PayOrderDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(PayOrder record) throws SQLException {
	return (Long) sqlMapClient.insert("pay_order.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(PayOrder record) throws SQLException {
	int rows = sqlMapClient.update("pay_order.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(PayOrderExample example) throws SQLException {
	List list = sqlMapClient.queryForList("pay_order.abatorgenerated_selectByExample", example);
	return list;
    }

    public PayOrder selectByPrimaryKey(Long id) throws SQLException {
	PayOrder key = new PayOrder();
	key.setId(id);
	PayOrder record = (PayOrder) sqlMapClient.queryForObject("pay_order.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(PayOrderExample example) throws SQLException {
	int rows = sqlMapClient.delete("pay_order.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	PayOrder key = new PayOrder();
	key.setId(id);
	int rows = sqlMapClient.delete("pay_order.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(PayOrderExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("pay_order.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(PayOrder record, PayOrderExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("pay_order.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends PayOrderExample {
	private Object record;

	public UpdateByExampleParms(Object record, PayOrderExample example) {
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
    public List<PayOrder> queryByPage(PayOrderQuery payOrderQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("pay_order.queryByPageCount", payOrderQuery);
	payOrderQuery.setTotalItem(count);
	return (List<PayOrder>) sqlMapClient.queryForList("pay_order.queryByPage", payOrderQuery);
    }

    @Override
    public PayOrderAmount querySumAmount(PayOrderQuery payOrderQuery) throws SQLException {
	return (PayOrderAmount) sqlMapClient.queryForObject("pay_order.querySumAmount", payOrderQuery);
    }
}