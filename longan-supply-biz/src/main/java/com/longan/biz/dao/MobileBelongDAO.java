package com.longan.biz.dao;

import com.longan.biz.dataobject.MobileBelong;
import com.longan.biz.dataobject.MobileBelongExample;

import java.sql.SQLException;
import java.util.List;

public interface MobileBelongDAO {
    void insert(MobileBelong record) throws SQLException;

    int updateByPrimaryKey(MobileBelong record) throws SQLException;

    int updateByPrimaryKeySelective(MobileBelong record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(MobileBelongExample example) throws SQLException;

    MobileBelong selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(MobileBelongExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(MobileBelongExample example) throws SQLException;

    int updateByExampleSelective(MobileBelong record, MobileBelongExample example) throws SQLException;
}