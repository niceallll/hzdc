package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.ItemPriceDAO;
import com.longan.biz.dataobject.ItemPrice;
import com.longan.biz.dataobject.ItemPriceQuery;

public class ItemPriceDAOImpl implements ItemPriceDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @SuppressWarnings("unchecked")
    @Override
    public List<ItemPrice> queryByPage(ItemPriceQuery itemPriceQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("item_price.queryByPageCount", itemPriceQuery);
	itemPriceQuery.setTotalItem(count);
	return (List<ItemPrice>) sqlMapClient.queryForList("item_price.queryByPage", itemPriceQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ItemPrice> queryByExport(ItemPriceQuery itemPriceQuery) throws SQLException {
	return (List<ItemPrice>) sqlMapClient.queryForList("item_price.queryByExport", itemPriceQuery);
    }
}
