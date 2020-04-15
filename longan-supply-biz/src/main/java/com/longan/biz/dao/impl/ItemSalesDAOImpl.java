package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.ItemSalesDAO;
import com.longan.biz.dataobject.ItemSales;
import com.longan.biz.dataobject.ItemSalesQuery;

public class ItemSalesDAOImpl implements ItemSalesDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(ItemSales record) throws SQLException {
	return (Long) sqlMapClient.insert("item_sales.abatorgenerated_insert", record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) throws SQLException {
	ItemSales key = new ItemSales();
	key.setId(id);
	int rows = sqlMapClient.delete("item_sales.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    @Override
    public int updateByPrimaryKeySelective(ItemSales record) throws SQLException {
	int rows = sqlMapClient.update("item_sales.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @Override
    public ItemSales selectByPrimaryKey(Long id) throws SQLException {
	ItemSales key = new ItemSales();
	key.setId(id);
	ItemSales record = (ItemSales) sqlMapClient.queryForObject("item_sales.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    @Override
    public ItemSales selectByIndex(Long userId, Integer itemId) throws SQLException {
	ItemSales key = new ItemSales();
	key.setUserId(userId);
	key.setItemId(itemId);
	return (ItemSales) sqlMapClient.queryForObject("item_sales.abatorgenerated_selectByIndex", key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ItemSales> queryItemList(ItemSalesQuery itemSalesQuery) throws SQLException {
	return (List<ItemSales>) sqlMapClient.queryForList("item_sales.queryItemList", itemSalesQuery);
    }
}
