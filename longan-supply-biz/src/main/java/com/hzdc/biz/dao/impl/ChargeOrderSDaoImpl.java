package com.hzdc.biz.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.hzdc.biz.dao.ChargeOrderSDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dataobject.ChargeOrder;

public class ChargeOrderSDaoImpl implements ChargeOrderSDao {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(ChargeOrder record) throws SQLException {
	return (Long) sqlMapClient.insert("charge_order_hzdc.insert", record);
    }

    public int updateByPrimaryKeySelective(ChargeOrder record) throws SQLException {
	int rows = sqlMapClient.update("charge_order_hzdc.updateByPrimaryKeySelective", record);
	return rows;
    }

    public ChargeOrder selectByPrimaryKey(Long id) throws SQLException {
	ChargeOrder key = new ChargeOrder();
	key.setId(id);
	ChargeOrder record = (ChargeOrder) sqlMapClient.queryForObject("charge_order_hzdc.selectByPrimaryKey", key);
	return record;
    }
}
