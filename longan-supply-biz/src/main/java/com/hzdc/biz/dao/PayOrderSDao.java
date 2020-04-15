package com.hzdc.biz.dao;

import java.sql.SQLException;

import com.longan.biz.dataobject.PayOrder;

public interface PayOrderSDao {
    Long insert(PayOrder record) throws SQLException;

    int updateByPrimaryKeySelective(PayOrder record) throws SQLException;

    PayOrder selectByPrimaryKey(Long id) throws SQLException;
}
