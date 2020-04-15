package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.OperationLogQuery;

public interface OperationLogDAO {
    Long insert(OperationLog record) throws SQLException;

    List<OperationLog> queryByPage(OperationLogQuery operationLogQuery) throws SQLException;
}
