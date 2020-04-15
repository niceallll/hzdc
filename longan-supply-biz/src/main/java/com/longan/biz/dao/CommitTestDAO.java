package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.CommitTestDO;

public interface CommitTestDAO {
    public CommitTestDO forUpdateOneRow(Long id) throws SQLException;

    public List<CommitTestDO> forUpdateList() throws SQLException;

    public int update(Long id) throws SQLException;

    public void CommitTest() throws SQLException;

    public CommitTestDO selectOneRow() throws SQLException;

    public int updateOptimisticLock(Long id) throws SQLException;
}
