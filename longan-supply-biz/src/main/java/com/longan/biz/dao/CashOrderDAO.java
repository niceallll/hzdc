package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.CashOrderQuery;
import com.longan.biz.sumobject.CashOrderAmount;

public interface CashOrderDAO {
    List<CashOrder> queryByPage(CashOrderQuery cashOrderQuery) throws SQLException;

    Long insert(CashOrder record) throws SQLException;

    CashOrder selectByPrimaryKey(Long id) throws SQLException;

    int updateByPrimaryKeySelective(CashOrder record) throws SQLException;

    int updateByPrimaryKeyAndStatus(CashOrder record) throws SQLException;

    CashOrderAmount querySumAmount(CashOrderQuery cashOrderQuery) throws SQLException;
}
