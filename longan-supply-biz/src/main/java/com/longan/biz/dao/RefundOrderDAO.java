package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderExample;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.sumobject.RefundOrderAmount;

public interface RefundOrderDAO {
    Long insert(RefundOrder record) throws SQLException;

    int updateByPrimaryKeySelective(RefundOrder record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(RefundOrderExample example) throws SQLException;

    RefundOrder selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(RefundOrderExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(RefundOrderExample example) throws SQLException;

    int updateByExampleSelective(RefundOrder record, RefundOrderExample example) throws SQLException;

    List<RefundOrder> queryByPage(RefundOrderQuery refundOrderQuery) throws SQLException;

    int countByExport(RefundOrderQuery refundOrderQuery) throws SQLException;

    List<RefundOrder> queryByExport(RefundOrderQuery refundOrderQuery) throws SQLException;

    List<RefundOrder> selectByExport(RefundOrderQuery refundOrderQuery) throws SQLException;

    RefundOrderAmount querySumAmount(RefundOrderQuery refundOrderQuery) throws SQLException;
}