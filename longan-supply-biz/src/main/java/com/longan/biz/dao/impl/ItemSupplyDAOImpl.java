package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.ItemSupplyDAO;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.ItemSupplyExample;
import com.longan.biz.dataobject.ItemSupplyQuery;

public class ItemSupplyDAOImpl implements ItemSupplyDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(ItemSupply record) throws SQLException {
	return (Long) sqlMapClient.insert("item_supply.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(ItemSupply record) throws SQLException {
	int rows = sqlMapClient.update("item_supply.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(ItemSupplyExample example) throws SQLException {
	List list = sqlMapClient.queryForList("item_supply.abatorgenerated_selectByExample", example);
	return list;
    }

    public ItemSupply selectByPrimaryKey(Long id) throws SQLException {
	ItemSupply key = new ItemSupply();
	key.setId(id);
	ItemSupply record = (ItemSupply) sqlMapClient.queryForObject("item_supply.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(ItemSupplyExample example) throws SQLException {
	int rows = sqlMapClient.delete("item_supply.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	ItemSupply key = new ItemSupply();
	key.setId(id);
	int rows = sqlMapClient.delete("item_supply.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(ItemSupplyExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("item_supply.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(ItemSupply record, ItemSupplyExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("item_supply.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends ItemSupplyExample {
	private Object record;

	public UpdateByExampleParms(Object record, ItemSupplyExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @Override
    public ItemSupply selectForUpdate(Long id) throws SQLException {
	ItemSupply itemSupply = (ItemSupply) sqlMapClient.queryForObject("item_supply.forUpdate", id);
	return itemSupply;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ItemSupply> queryByPage(ItemSupplyQuery itemSupplyQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("item_supply.queryByPageCount", itemSupplyQuery);
	itemSupplyQuery.setTotalItem(count);
	return (List<ItemSupply>) sqlMapClient.queryForList("item_supply.queryByPage", itemSupplyQuery);
    }

    @Override
    public int batchUpdateStatusItemSupply(List<Integer> ids, Integer status) throws SQLException {
	Map<String, Object> parms = new HashMap<String, Object>(2);
	parms.put("ids", ids);
	parms.put("status", status);
	int rows = sqlMapClient.update("item_supply.batchUpdateStatusItemSupply", parms);
	return rows;
    }
}