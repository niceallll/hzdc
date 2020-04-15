package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.ChargeOrderExample;
import com.longan.biz.dataobject.ChargeOrderQuery;
import com.longan.biz.sumobject.ChargeOrderAmount;

public interface ChargeOrderDAO {
    Long insert(ChargeOrder record) throws SQLException;

    int updateByPrimaryKeySelective(ChargeOrder record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(ChargeOrderExample example) throws SQLException;

    ChargeOrder selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(ChargeOrderExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(ChargeOrderExample example) throws SQLException;

    int updateByExampleSelective(ChargeOrder record, ChargeOrderExample example) throws SQLException;

    List<ChargeOrder> queryByPage(ChargeOrderQuery chargeOrderQuery) throws SQLException;

    ChargeOrderAmount querySumAmount(ChargeOrderQuery chargeOrderQuery) throws SQLException;
}