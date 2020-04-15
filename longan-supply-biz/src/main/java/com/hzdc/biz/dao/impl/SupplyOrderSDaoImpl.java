package com.hzdc.biz.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.hzdc.biz.dao.SupplyOrderSDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dataobject.SupplyOrder;

public class SupplyOrderSDaoImpl implements SupplyOrderSDao {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(SupplyOrder supplyOrder) throws SQLException {
	return (Long) sqlMapClient.insert("supply_order_hzdc.insert", supplyOrder);
    }

    @Override
    public SupplyOrder selectByPrimaryKey(Long id) throws SQLException {
	SupplyOrder key = new SupplyOrder();
	key.setId(id);
	SupplyOrder record = (SupplyOrder) sqlMapClient.queryForObject("supply_order_hzdc.selectByPrimaryKey", key);
	return record;
    }
}
