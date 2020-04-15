package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderExample;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.sumobject.SmsOrderCount;

public interface SmsOrderDAO {
    List<SmsOrder> queryByPage(SmsOrderQuery smsOrderQuery) throws SQLException;

    SmsOrderCount querySumCount(SmsOrderQuery smsOrderQuery) throws SQLException;

    int countByExport(SmsOrderQuery smsOrderQuery) throws SQLException;

    List<SmsOrder> queryByExport(SmsOrderQuery smsOrderQuery) throws SQLException;

    List<SmsOrder> selectByExport(SmsOrderQuery smsOrderQuery) throws SQLException;

    Long insert(SmsOrder smsOrder) throws SQLException;

    int updateByPrimaryKeySelective(SmsOrder smsOrder) throws SQLException;

    boolean updateSmsOrderCheckStatus(SmsOrder smsOrder, List<Integer> statusList) throws SQLException;

    SmsOrder selectByPrimaryKey(Long id) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(SmsOrderExample example) throws SQLException;
}
