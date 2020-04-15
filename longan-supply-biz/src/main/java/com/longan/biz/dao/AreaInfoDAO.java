package com.longan.biz.dao;

import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.AreaInfoExample;

import java.sql.SQLException;
import java.util.List;

public interface AreaInfoDAO {
    void insert(AreaInfo record) throws SQLException;

    int updateByPrimaryKey(AreaInfo record) throws SQLException;

    int updateByPrimaryKeySelective(AreaInfo record) throws SQLException;

    @SuppressWarnings("rawtypes")
	List selectByExample(AreaInfoExample example) throws SQLException;

    AreaInfo selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(AreaInfoExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(AreaInfoExample example) throws SQLException;

    int updateByExampleSelective(AreaInfo record, AreaInfoExample example) throws SQLException;
}