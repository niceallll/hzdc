package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.QueryStockDAO;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockQuery;

public class QueryStockDAOImpl implements QueryStockDAO {
    @Resource
    private SqlMapClient querySqlMapClient;

    @SuppressWarnings("unchecked")
    @Override
    public List<Stock> queryByPage(StockQuery stockQuery) throws SQLException {
	int count = (Integer) querySqlMapClient.queryForObject("stock.queryByPageCount", stockQuery);
	stockQuery.setTotalItem(count);
	return (List<Stock>) querySqlMapClient.queryForList("stock.queryByPage", stockQuery);
    }
}