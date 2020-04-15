package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoExample;
import com.longan.biz.dataobject.UserInfoQuery;

public interface UserInfoDAO {
    Long insert(UserInfo record) throws SQLException;

    int updateByPrimaryKeySelective(UserInfo record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(UserInfoExample example) throws SQLException;

    UserInfo selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(UserInfoExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(UserInfoExample example) throws SQLException;

    int updateByExampleSelective(UserInfo record, UserInfoExample example) throws SQLException;

    List<UserInfo> queryByPage(UserInfoQuery userInfoQuery) throws SQLException;

    List<UserInfo> queryDownStreamByPage(UserInfoQuery userInfoQuery) throws SQLException;

    UserInfo selectDownStreamByPrimaryKey(Long id) throws SQLException;
}