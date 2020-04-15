package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.BulkOrderDAO;
import com.longan.biz.dataobject.BulkOrder;
import com.longan.biz.dataobject.BulkOrderQuery;

public class BulkOrderDAOImpl implements BulkOrderDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(BulkOrder bulkOrder) throws SQLException {
	return (Long) sqlMapClient.insert("bulk_order.insert", bulkOrder);
    }

    @Override
    public int updateBulkOrder(BulkOrder bulkOrder) throws SQLException {
	return sqlMapClient.update("bulk_order.updateBulkOrder", bulkOrder);
    }

    @Override
    public BulkOrder selectBulkOrderById(Long id) throws SQLException {
	BulkOrder key = new BulkOrder();
	key.setId(id);
	BulkOrder record = (BulkOrder) sqlMapClient.queryForObject("bulk_order.selectByPrimaryKey", key);
	return record;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BulkOrder> queryByPage(BulkOrderQuery bulkOrderQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("bulk_order.queryByPageCount", bulkOrderQuery);
	bulkOrderQuery.setTotalItem(count);
	return (List<BulkOrder>) sqlMapClient.queryForList("bulk_order.queryByPage", bulkOrderQuery);
    }
}
