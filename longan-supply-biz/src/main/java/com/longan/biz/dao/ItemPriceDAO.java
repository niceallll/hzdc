package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.ItemPrice;
import com.longan.biz.dataobject.ItemPriceQuery;

public interface ItemPriceDAO {
    List<ItemPrice> queryByPage(ItemPriceQuery itemPriceQuery) throws SQLException;

    List<ItemPrice> queryByExport(ItemPriceQuery itemPriceQuery) throws SQLException;
}
