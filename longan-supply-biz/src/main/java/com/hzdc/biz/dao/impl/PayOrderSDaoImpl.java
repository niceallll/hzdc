package com.hzdc.biz.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.hzdc.biz.dao.PayOrderSDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dataobject.PayOrder;

public class PayOrderSDaoImpl implements PayOrderSDao {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(PayOrder record) throws SQLException {
	return (Long) sqlMapClient.insert("pay_order_hzdc.insert", record);
    }

    public int updateByPrimaryKeySelective(PayOrder record) throws SQLException {
	int rows = sqlMapClient.update("pay_order_hzdc.updateByPrimaryKeySelective", record);
	return rows;
    }

    public PayOrder selectByPrimaryKey(Long id) throws SQLException {
	PayOrder key = new PayOrder();
	key.setId(id);
	PayOrder record = (PayOrder) sqlMapClient.queryForObject("pay_order_hzdc.selectByPrimaryKey", key);
	return record;
    }
}
