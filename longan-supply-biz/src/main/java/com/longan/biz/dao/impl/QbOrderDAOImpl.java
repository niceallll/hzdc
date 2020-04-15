package com.longan.biz.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.QbOrderDAO;
import com.longan.biz.dataobject.QbOrder;

public class QbOrderDAOImpl implements QbOrderDAO {
    @Resource
    private SqlMapClient qbCoinSqlMapClient;

    @Override
    public void insert(QbOrder record) throws SQLException {
	qbCoinSqlMapClient.insert("qb_order.insert", record);
    }

    @Override
    public QbOrder selectBySerialNum(String serialNum) throws SQLException {
	QbOrder key = new QbOrder();
	key.setSerialNum(serialNum);
	QbOrder record = (QbOrder) qbCoinSqlMapClient.queryForObject("qb_order.selectBySerialNum", key);
	return record;
    }

}
