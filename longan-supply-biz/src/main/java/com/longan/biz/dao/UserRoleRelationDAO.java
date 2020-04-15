package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoQuery;
import com.longan.biz.dataobject.UserRoleRelation;
import com.longan.biz.dataobject.UserRoleRelationExample;

public interface UserRoleRelationDAO {
    void insert(UserRoleRelation record) throws SQLException;

    int updateByPrimaryKeySelective(UserRoleRelation record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(UserRoleRelationExample example) throws SQLException;

    UserRoleRelation selectByPrimaryKey(Integer id) throws SQLException;

    int deleteByExample(UserRoleRelationExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int countByExample(UserRoleRelationExample example) throws SQLException;

    int updateByExampleSelective(UserRoleRelation record, UserRoleRelationExample example) throws SQLException;

    Boolean batchInsert(List<UserRoleRelation> userRoleRelationList) throws Exception;

    int deleteByUserId(Long userId) throws SQLException;

    List<UserInfo> queryUserRoleRelationByPage(UserInfoQuery userInfoQuery) throws SQLException;
}