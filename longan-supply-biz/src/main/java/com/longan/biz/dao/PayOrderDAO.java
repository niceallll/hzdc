package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderExample;
import com.longan.biz.dataobject.PayOrderQuery;
import com.longan.biz.sumobject.PayOrderAmount;

public interface PayOrderDAO {
    Long insert(PayOrder record) throws SQLException;

    int updateByPrimaryKeySelective(PayOrder record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(PayOrderExample example) throws SQLException;

    PayOrder selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(PayOrderExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(PayOrderExample example) throws SQLException;

    int updateByExampleSelective(PayOrder record, PayOrderExample example) throws SQLException;

    List<PayOrder> queryByPage(PayOrderQuery payOrderQuery) throws SQLException;

    PayOrderAmount querySumAmount(PayOrderQuery payOrderQuery) throws SQLException;
}