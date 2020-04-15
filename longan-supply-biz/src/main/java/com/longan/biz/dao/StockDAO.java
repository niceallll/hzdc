package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockExample;
import com.longan.biz.dataobject.StockQuery;

public interface StockDAO {
    void insert(Stock record) throws SQLException;

    int updateByPrimaryKeySelective(Stock record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(StockExample example) throws SQLException;

    Stock selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(StockExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(StockExample example) throws SQLException;

    int updateByExampleSelective(Stock record, StockExample example) throws SQLException;

    Stock selectForUpdate(Long itemSupplyId) throws SQLException;

    Boolean batchInsert(List<Stock> stockList) throws SQLException;

    List<Stock> selectInSales(Long itemSupplyId, Integer count) throws SQLException;

    Stock selectForUpdateById(Long id) throws SQLException;

    List<Stock> queryByPage(StockQuery stockQuery) throws SQLException;

    int outStockByCount(Integer count, Long itemSupplyId, Long stockLogId) throws SQLException;

}