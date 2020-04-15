package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemExample;
import com.longan.biz.dataobject.ItemQuery;

public interface ItemDAO {
    Integer insert(Item record) throws SQLException;

    int updateByPrimaryKeySelective(Item record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(ItemExample example) throws SQLException;

    Item selectByPrimaryKey(Integer id) throws SQLException;

    int deleteByExample(ItemExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    int countByExample(ItemExample example) throws SQLException;

    int updateByExampleSelective(Item record, ItemExample example) throws SQLException;

    List<Item> queryByPage(ItemQuery itemQuery) throws SQLException;

    int batchUpdateStatusItem(List<Integer> ids, Integer status) throws SQLException;
}