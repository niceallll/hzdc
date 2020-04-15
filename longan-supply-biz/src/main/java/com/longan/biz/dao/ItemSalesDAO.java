package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.ItemSales;
import com.longan.biz.dataobject.ItemSalesQuery;

public interface ItemSalesDAO {
    Long insert(ItemSales record) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int updateByPrimaryKeySelective(ItemSales record) throws SQLException;

    ItemSales selectByPrimaryKey(Long id) throws SQLException;

    ItemSales selectByIndex(Long userId, Integer itemId) throws SQLException;

    List<ItemSales> queryItemList(ItemSalesQuery itemSalesQuery) throws SQLException;
}
