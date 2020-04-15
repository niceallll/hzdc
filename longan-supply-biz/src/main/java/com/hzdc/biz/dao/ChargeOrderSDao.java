package com.hzdc.biz.dao;

import java.sql.SQLException;

import com.longan.biz.dataobject.ChargeOrder;

public interface ChargeOrderSDao {
    Long insert(ChargeOrder record) throws SQLException;

    int updateByPrimaryKeySelective(ChargeOrder record) throws SQLException;

    ChargeOrder selectByPrimaryKey(Long id) throws SQLException;
}
