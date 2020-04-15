package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.SmsNotify;
import com.longan.biz.dataobject.SmsNotifyQuery;

public interface SmsNotifyDAO {
    List<SmsNotify> queryByPage(SmsNotifyQuery smsNotifyQuery) throws SQLException;

    Long insert(SmsNotify smsNotify) throws SQLException;

    SmsNotify selectByPrimaryKey(Long id) throws SQLException;
}
