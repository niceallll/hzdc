package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationInfoExample;
import com.longan.biz.dataobject.OperationInfoQuery;

public interface OperationInfoDAO {
    Integer insert(OperationInfo record) throws SQLException;

    int updateByPrimaryKeySelective(OperationInfo record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(OperationInfoExample example) throws SQLException;

    OperationInfo selectByPrimaryKey(Integer id) throws SQLException;

    int deleteByExample(OperationInfoExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int countByExample(OperationInfoExample example) throws SQLException;

    int updateByExampleSelective(OperationInfo record, OperationInfoExample example) throws SQLException;

    List<OperationInfo> selectOperationListByRole(Integer roleId, Integer type) throws SQLException;

    List<OperationInfo> queryOperationInfoByPage(OperationInfoQuery operationInfoQuery) throws SQLException;

    List<OperationInfo> queryOperationListByType(Integer type) throws SQLException;

    List<OperationInfo> queryAllOperationList() throws SQLException;
}