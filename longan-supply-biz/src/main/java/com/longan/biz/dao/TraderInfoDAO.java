package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.TraderInfoExample;

public interface TraderInfoDAO {
    long insert(TraderInfo record) throws SQLException;

    int updateByPrimaryKeySelective(TraderInfo record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(TraderInfoExample example) throws SQLException;

    TraderInfo selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(TraderInfoExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(TraderInfoExample example) throws SQLException;

    int updateByExampleSelective(TraderInfo record, TraderInfoExample example) throws SQLException;

    TraderInfo selectByUserId(Long userId) throws SQLException;

    TraderInfo selectForUpdate(Long userId) throws SQLException;
}