package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.CommitTestDAO;
import com.longan.biz.dataobject.CommitTestDO;

public class CommitTestDAOImpl implements CommitTestDAO {
    private SqlMapClient sqlMapClient;

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
	this.sqlMapClient = sqlMapClient;
    }

    @Override
    public CommitTestDO forUpdateOneRow(Long id) throws SQLException {
	return (CommitTestDO) sqlMapClient.queryForObject("commitTestDAO.forUpdateOneRow", id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CommitTestDO> forUpdateList() throws SQLException {
	return (List<CommitTestDO>) sqlMapClient.queryForList("commitTestDAO.forUpdateList");
    }

    @Override
    public int update(Long id) throws SQLException {
	return sqlMapClient.update("commitTestDAO.update", id);
    }

    public CommitTestDO selectOneRow() throws SQLException {
	return (CommitTestDO) sqlMapClient.queryForObject("commitTestDAO.selectOneRow");
    }

    public int updateOptimisticLock(Long id) throws SQLException {
	return sqlMapClient.update("commitTestDAO.updateOptimisticLock", id);
    }

    @Override
    public void CommitTest() throws SQLException {
	try {
	    // sqlMapClient.startTransaction();
	    // System.out.println(sqlMapClient.getCurrentConnection().getAutoCommit());
	    // System.out.println(sqlMapClient.getCurrentConnection().getTransactionIsolation());
	    // sqlMapClient.getCurrentConnection().setAutoCommit(false);

	    List<CommitTestDO> list = forUpdateList();
	    // forUpdateOneRow(1l);
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    // System.out.println("----------");
	    if (list.size() > 0) {
		update(list.get(0).getId());
	    } else {
	    }

	    // System.out.println(sqlMapClient.getCurrentConnection().getAutoCommit());
	    // throw new RuntimeException("test");
	    // sqlMapClient.getCurrentConnection().commit();
	    // System.out.println(sqlMapClient.getCurrentConnection().getAutoCommit());

	    // sqlMapClient.commitTransaction();
	    // sqlMapClient.getCurrentConnection().commit();
	    // throw new RuntimeException();
	} finally {
	    // try {
	    // System.out.println("if not commit  will rollback");
	    // System.out.println(sqlMapClient.getCurrentConnection().getAutoCommit());
	    // sqlMapClient.endTransaction();
	    // } catch (SQLException e) {
	    // e.printStackTrace();
	    // }
	}
    }
}
