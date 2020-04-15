package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.OperationRoleRelation;
import com.longan.biz.dataobject.OperationRoleRelationExample;

public interface OperationRoleRelationDAO {
    void insert(OperationRoleRelation record) throws SQLException;

    int updateByPrimaryKeySelective(OperationRoleRelation record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(OperationRoleRelationExample example) throws SQLException;

    OperationRoleRelation selectByPrimaryKey(Integer id) throws SQLException;

    int deleteByExample(OperationRoleRelationExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int countByExample(OperationRoleRelationExample example) throws SQLException;

    int updateByExampleSelective(OperationRoleRelation record, OperationRoleRelationExample example) throws SQLException;

    Boolean batchInsert(List<OperationRoleRelation> operationRoleRelationList) throws Exception;

    int deleteByRoleId(Integer roleId) throws SQLException;
}