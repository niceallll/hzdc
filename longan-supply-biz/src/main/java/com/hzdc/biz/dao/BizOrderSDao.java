package com.hzdc.biz.dao;

import java.sql.SQLException;

import com.longan.biz.dataobject.BizOrder;

public interface BizOrderSDao {
    Long insert(BizOrder record) throws SQLException;

    int updateByPrimaryKeySelective(BizOrder record) throws SQLException;

    BizOrder selectByPrimaryKey(Long id) throws SQLException;
}
