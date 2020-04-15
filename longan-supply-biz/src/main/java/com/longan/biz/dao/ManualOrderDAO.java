package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.ManualOrder;

public interface ManualOrderDAO {
    Long insert(ManualOrder manualOrder) throws SQLException;

    int updateByPrimaryKeySelective(ManualOrder manualOrder) throws SQLException;

    ManualOrder selectByPrimaryKey(Long id) throws SQLException;

    ManualOrder selectBySerialno(String serialno) throws SQLException;

    List<ManualOrder> selectByLogId(Long logId) throws SQLException;
}
