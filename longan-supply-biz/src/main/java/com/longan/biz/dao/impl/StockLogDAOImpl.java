package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.StockLogDAO;
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.StockLogExample;
import com.longan.biz.dataobject.StockLogQuery;

public class StockLogDAOImpl implements StockLogDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(StockLog record) throws SQLException {
	return (Long) sqlMapClient.insert("stock_log.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(StockLog record) throws SQLException {
	int rows = sqlMapClient.update("stock_log.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(StockLogExample example) throws SQLException {
	List list = sqlMapClient.queryForList("stock_log.abatorgenerated_selectByExample", example);
	return list;
    }

    public StockLog selectByPrimaryKey(Long id) throws SQLException {
	StockLog key = new StockLog();
	key.setId(id);
	StockLog record = (StockLog) sqlMapClient.queryForObject("stock_log.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(StockLogExample example) throws SQLException {
	int rows = sqlMapClient.delete("stock_log.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	StockLog key = new StockLog();
	key.setId(id);
	int rows = sqlMapClient.delete("stock_log.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(StockLogExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("stock_log.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(StockLog record, StockLogExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("stock_log.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends StockLogExample {
	private Object record;

	public UpdateByExampleParms(Object record, StockLogExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StockLog> queryByPage(StockLogQuery stockLogQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("stock_log.queryByPageCount", stockLogQuery);
	stockLogQuery.setTotalItem(count);
	return (List<StockLog>) sqlMapClient.queryForList("stock_log.queryByPage", stockLogQuery);
    }
}