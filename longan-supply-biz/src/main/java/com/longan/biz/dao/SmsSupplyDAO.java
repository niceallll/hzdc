package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.sumobject.SmsOrderCount;

public interface SmsSupplyDAO {
    List<SmsSupply> queryByPage(SmsSupplyQuery smsSupplyQuery) throws SQLException;

    SmsOrderCount querySumCount(SmsSupplyQuery smsSupplyQuery) throws SQLException;

    int countByExport(SmsSupplyQuery smsSupplyQuery) throws SQLException;

    List<SmsSupply> queryByExport(SmsSupplyQuery smsSupplyQuery) throws SQLException;

    List<SmsSupply> selectByExport(SmsSupplyQuery smsSupplyQuery) throws SQLException;

    Long insert(SmsSupply smsSupply) throws SQLException;

    int updateByPrimaryKeySelective(SmsSupply smsSupply) throws SQLException;

    boolean updateSmsSupplyCheckStatus(SmsSupply smsSupply, List<Integer> statusList) throws SQLException;

    SmsSupply selectByPrimaryKey(Long id) throws SQLException;

    SmsSupply selectBySerialno(Long supplyTraderId, String upstreamSerialno) throws SQLException;

    List<SmsSupply> selectBySmsOrderId(Long smsOrderId) throws SQLException;
}
