package com.longan.biz.dao;

import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.StockLogExample;
import com.longan.biz.dataobject.StockLogQuery;

import java.sql.SQLException;
import java.util.List;

public interface StockLogDAO {
	Long insert(StockLog record) throws SQLException;

	int updateByPrimaryKeySelective(StockLog record) throws SQLException;

	@SuppressWarnings("rawtypes")
	List selectByExample(StockLogExample example) throws SQLException;

	StockLog selectByPrimaryKey(Long id) throws SQLException;

	int deleteByExample(StockLogExample example) throws SQLException;

	int deleteByPrimaryKey(Long id) throws SQLException;

	int countByExample(StockLogExample example) throws SQLException;

	int updateByExampleSelective(StockLog record, StockLogExample example) throws SQLException;

	List<StockLog> queryByPage(StockLogQuery stockLogQuery) throws SQLException;

}