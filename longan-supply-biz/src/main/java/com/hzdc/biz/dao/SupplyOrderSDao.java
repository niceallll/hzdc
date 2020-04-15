package com.hzdc.biz.dao;

import java.sql.SQLException;

import com.longan.biz.dataobject.SupplyOrder;

public interface SupplyOrderSDao {
    Long insert(SupplyOrder supplyOrder) throws SQLException;

    SupplyOrder selectByPrimaryKey(Long id) throws SQLException;
}
