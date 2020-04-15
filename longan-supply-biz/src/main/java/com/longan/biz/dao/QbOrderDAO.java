package com.longan.biz.dao;

import java.sql.SQLException;

import com.longan.biz.dataobject.QbOrder;

public interface QbOrderDAO {
    void insert(QbOrder record) throws SQLException;

    QbOrder selectBySerialNum(String serialNum) throws SQLException;
}
