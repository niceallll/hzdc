package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.ManualLog;
import com.longan.biz.dataobject.ManualLogQuery;

public interface ManualLogDAO {
    Long insert(ManualLog manualLog) throws SQLException;

    int updateByPrimaryKeySelective(ManualLog manualLog) throws SQLException;

    List<ManualLog> queryByPage(ManualLogQuery manualLogQuery) throws SQLException;

    ManualLog selectForUpdate(Long id) throws SQLException;
}
