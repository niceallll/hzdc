package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.MobileQblib;
import com.longan.biz.dataobject.MobileQblibExample;

public interface MobileQblibDAO {
    String selectMobileByAreaCode(String areaCode) throws SQLException;

    void insert(MobileQblib record) throws SQLException;

    int updateByPrimaryKey(MobileQblib record) throws SQLException;

    int updateByPrimaryKeySelective(MobileQblib record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(MobileQblibExample example) throws SQLException;

    MobileQblib selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(MobileQblibExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(MobileQblibExample example) throws SQLException;

    int updateByExampleSelective(MobileQblib record, MobileQblibExample example) throws SQLException;

}
