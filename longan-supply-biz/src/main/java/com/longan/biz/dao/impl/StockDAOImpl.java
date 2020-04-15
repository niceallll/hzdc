package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.StockDAO;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockExample;
import com.longan.biz.dataobject.StockQuery;

public class StockDAOImpl implements StockDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public void insert(Stock record) throws SQLException {
	sqlMapClient.insert("stock.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(Stock record) throws SQLException {
	int rows = sqlMapClient.update("stock.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(StockExample example) throws SQLException {
	List list = sqlMapClient.queryForList("stock.abatorgenerated_selectByExample", example);
	return list;
    }

    public Stock selectByPrimaryKey(Long id) throws SQLException {
	Stock key = new Stock();
	key.setId(id);
	Stock record = (Stock) sqlMapClient.queryForObject("stock.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(StockExample example) throws SQLException {
	int rows = sqlMapClient.delete("stock.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	Stock key = new Stock();
	key.setId(id);
	int rows = sqlMapClient.delete("stock.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(StockExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("stock.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(Stock record, StockExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("stock.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends StockExample {
	private Object record;

	public UpdateByExampleParms(Object record, StockExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @Override
    public Stock selectForUpdate(Long itemSupplyId) throws SQLException {
	Stock stock = (Stock) sqlMapClient.queryForObject("stock.forUpdateOneRow", itemSupplyId);
	return stock;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Stock> selectInSales(Long itemSupplyId, Integer count) throws SQLException {
	Map<String, Object> map = new HashMap<String, Object>(2);
	map.put("itemSupplyId", itemSupplyId);
	map.put("count", count);
	List<Stock> result = (List<Stock>) sqlMapClient.queryForList("stock.selectInSales", map);
	return result;
    }

    @Override
    public Boolean batchInsert(List<Stock> stockList) throws SQLException {
	sqlMapClient.startBatch();
	for (Stock stock : stockList) {
	    sqlMapClient.insert("stock.abatorgenerated_insert", stock);
	}
	sqlMapClient.executeBatch();
	return true;
    }

    @Override
    public Stock selectForUpdateById(Long id) throws SQLException {
	Stock stock = (Stock) sqlMapClient.queryForObject("stock.selectForUpdateById", id);
	return stock;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Stock> queryByPage(StockQuery stockQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("stock.queryByPageCount", stockQuery);
	stockQuery.setTotalItem(count);
	return (List<Stock>) sqlMapClient.queryForList("stock.queryByPage", stockQuery);
    }

    @Override
    public int outStockByCount(Integer count, Long itemSupplyId, Long stockLogId) throws SQLException {
	Map<String, Object> map = new HashMap<String, Object>(3);
	map.put("itemSupplyId", itemSupplyId);
	map.put("count", count);
	map.put("stockLogId", stockLogId);
	return sqlMapClient.update("stock.outStockByCount", map);
    }

}