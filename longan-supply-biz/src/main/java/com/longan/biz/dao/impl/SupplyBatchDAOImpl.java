package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.SupplyBatchDAO;
import com.longan.biz.dataobject.SupplyBatch;
import com.longan.biz.dataobject.SupplyBatchExample;
import com.longan.biz.dataobject.SupplyBatchQuery;

public class SupplyBatchDAOImpl implements SupplyBatchDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(SupplyBatch record) throws SQLException {
	return (Long) sqlMapClient.insert("supply_batch.insert", record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) throws SQLException {
	SupplyBatch key = new SupplyBatch();
	key.setId(id);
	int rows = sqlMapClient.delete("supply_batch.deleteByPrimaryKey", key);
	return rows;
    }

    @Override
    public int deleteByExample(SupplyBatchExample example) throws SQLException {
	int rows = sqlMapClient.delete("supply_batch.deleteByExample", example);
	return rows;
    }

    @Override
    public int updateByPrimaryKeySelective(SupplyBatch record) throws SQLException {
	int rows = sqlMapClient.update("supply_batch.updateByPrimaryKeySelective", record);
	return rows;
    }

    @Override
    public int updateByExampleSelective(SupplyBatch record, SupplyBatchExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("supply_batch.updateByExampleSelective", parms);
	return rows;
    }

    @Override
    public SupplyBatch selectByPrimaryKey(Long id) throws SQLException {
	SupplyBatch key = new SupplyBatch();
	key.setId(id);
	SupplyBatch record = (SupplyBatch) sqlMapClient.queryForObject("supply_batch.selectByPrimaryKey", key);
	return record;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List selectByExample(SupplyBatchExample example) throws SQLException {
	List list = sqlMapClient.queryForList("supply_batch.selectByExample", example);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SupplyBatch> queryByPage(SupplyBatchQuery supplyBatchQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("supply_batch.queryByPageCount", supplyBatchQuery);
	supplyBatchQuery.setTotalItem(count);
	return (List<SupplyBatch>) sqlMapClient.queryForList("supply_batch.queryByPage", supplyBatchQuery);
    }

    private static class UpdateByExampleParms extends SupplyBatchExample {
	private Object record;

	public UpdateByExampleParms(Object record, SupplyBatchExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }
}
