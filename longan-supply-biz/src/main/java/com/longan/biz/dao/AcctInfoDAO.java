package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AcctInfoExample;

public interface AcctInfoDAO {
    Long insert(AcctInfo record) throws SQLException;

    int updateByPrimaryKeySelective(AcctInfo record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(AcctInfoExample example) throws SQLException;

    AcctInfo selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(AcctInfoExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(AcctInfoExample example) throws SQLException;

    int updateByExampleSelective(AcctInfo record, AcctInfoExample example) throws SQLException;

    AcctInfo selectForUpdate(Long id) throws SQLException;
}