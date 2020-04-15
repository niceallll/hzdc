package com.hzdc.biz.dao;

import java.sql.SQLException;

import com.longan.biz.dataobject.AcctLog;

public interface AcctLogSDao {
    Long insert(AcctLog record) throws SQLException;
    
    AcctLog selectByPrimaryKey(Long id) throws SQLException;
}
