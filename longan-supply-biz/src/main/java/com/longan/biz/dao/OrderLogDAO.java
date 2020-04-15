package com.longan.biz.dao;

import com.longan.biz.dataobject.OrderLog;
import com.longan.biz.dataobject.OrderLogExample;

import java.sql.SQLException;
import java.util.List;

public interface OrderLogDAO {
    void insert(OrderLog record) throws SQLException;

    int updateByPrimaryKeySelective(OrderLog record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(OrderLogExample example) throws SQLException;

    OrderLog selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(OrderLogExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(OrderLogExample example) throws SQLException;

    int updateByExampleSelective(OrderLog record, OrderLogExample example) throws SQLException;

}