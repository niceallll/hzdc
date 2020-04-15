package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.SupplyBatch;
import com.longan.biz.dataobject.SupplyBatchExample;
import com.longan.biz.dataobject.SupplyBatchQuery;

public interface SupplyBatchDAO {
    Long insert(SupplyBatch record) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(SupplyBatchExample example) throws SQLException;

    int updateByPrimaryKeySelective(SupplyBatch record) throws SQLException;

    int updateByExampleSelective(SupplyBatch record, SupplyBatchExample example) throws SQLException;

    SupplyBatch selectByPrimaryKey(Long id) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(SupplyBatchExample example) throws SQLException;

    List<SupplyBatch> queryByPage(SupplyBatchQuery supplyBatchQuery) throws SQLException;
}
