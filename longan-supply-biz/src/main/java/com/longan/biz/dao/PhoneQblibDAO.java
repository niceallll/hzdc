package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.PhoneQblib;
import com.longan.biz.dataobject.PhoneQblibExample;

public interface PhoneQblibDAO {
    String selectPhoneByAreaCode(String areaCode) throws SQLException;

    void insert(PhoneQblib record) throws SQLException;

    int updateByPrimaryKey(PhoneQblib record) throws SQLException;

    int updateByPrimaryKeySelective(PhoneQblib record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(PhoneQblibExample example) throws SQLException;

    PhoneQblib selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(PhoneQblibExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(PhoneQblibExample example) throws SQLException;

    int updateByExampleSelective(PhoneQblib record, PhoneQblibExample example) throws SQLException;

}
