package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.ItemSupplyExample;
import com.longan.biz.dataobject.ItemSupplyQuery;

public interface ItemSupplyDAO {
    Long insert(ItemSupply record) throws SQLException;

    int updateByPrimaryKeySelective(ItemSupply record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(ItemSupplyExample example) throws SQLException;

    ItemSupply selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(ItemSupplyExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(ItemSupplyExample example) throws SQLException;

    int updateByExampleSelective(ItemSupply record, ItemSupplyExample example) throws SQLException;

    ItemSupply selectForUpdate(Long id) throws SQLException;

    List<ItemSupply> queryByPage(ItemSupplyQuery itemSupplyQuery) throws SQLException;

    int batchUpdateStatusItemSupply(List<Integer> ids, Integer status) throws SQLException;
}