package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.ItemDAO;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemExample;
import com.longan.biz.dataobject.ItemQuery;

public class ItemDAOImpl implements ItemDAO {

    @Resource
    private SqlMapClient sqlMapClient;

    public Integer insert(Item record) throws SQLException {
	return (Integer) sqlMapClient.insert("item.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(Item record) throws SQLException {
	int rows = sqlMapClient.update("item.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(ItemExample example) throws SQLException {
	List list = sqlMapClient.queryForList("item.abatorgenerated_selectByExample", example);
	return list;
    }

    public Item selectByPrimaryKey(Integer id) throws SQLException {
	Item key = new Item();
	key.setId(id);
	Item record = (Item) sqlMapClient.queryForObject("item.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(ItemExample example) throws SQLException {
	int rows = sqlMapClient.delete("item.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Integer id) throws SQLException {
	Item key = new Item();
	key.setId(id);
	int rows = sqlMapClient.delete("item.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(ItemExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("item.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(Item record, ItemExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("item.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends ItemExample {
	private Object record;

	public UpdateByExampleParms(Object record, ItemExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Item> queryByPage(ItemQuery itemQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("item.queryByPageCount", itemQuery);
	itemQuery.setTotalItem(count);
	return (List<Item>) sqlMapClient.queryForList("item.queryByPage", itemQuery);
    }

    @Override
    public int batchUpdateStatusItem(List<Integer> ids, Integer status) throws SQLException {
	Map<String, Object> parms = new HashMap<String, Object>(2);
	parms.put("ids", ids);
	parms.put("status", status);
	int rows = sqlMapClient.update("item.batchUpdateStatusItem", parms);
	return rows;
    }
}