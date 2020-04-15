package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockQuery;

public interface QueryStockDAO {
	
	List<Stock> queryByPage(StockQuery stockQuery) throws SQLException;
	
}