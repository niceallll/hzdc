package com.hzdc.biz.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.hzdc.biz.dao.BizOrderSDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dataobject.BizOrder;

public class BizOrderSDaoImpl implements BizOrderSDao {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(BizOrder record) throws SQLException {
	return (Long) sqlMapClient.insert("biz_order_hzdc.insert", record);
    }

    public int updateByPrimaryKeySelective(BizOrder record) throws SQLException {
	int rows = sqlMapClient.update("biz_order_hzdc.updateByPrimaryKeySelective", record);
	return rows;
    }

    public BizOrder selectByPrimaryKey(Long id) throws SQLException {
	BizOrder key = new BizOrder();
	key.setId(id);
	BizOrder record = (BizOrder) sqlMapClient.queryForObject("biz_order_hzdc.selectByPrimaryKey", key);
	return record;
    }
}
