package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.SupplyOrderDAO;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.sumobject.SupplyOrderAmount;
import com.longan.biz.utils.Constants;

public class SupplyOrderDAOImpl implements SupplyOrderDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(SupplyOrder supplyOrder) throws SQLException {
	return (Long) sqlMapClient.insert("supply_order.insert", supplyOrder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SupplyOrder> queryByPage(SupplyOrderQuery supplyOrderQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("supply_order.queryByPageCount", supplyOrderQuery);
	supplyOrderQuery.setTotalItem(count);
	return (List<SupplyOrder>) sqlMapClient.queryForList("supply_order.queryByPage", supplyOrderQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SupplyOrder> queryByList(SupplyOrderQuery supplyOrderQuery) throws SQLException {
	return (List<SupplyOrder>) sqlMapClient.queryForList("supply_order.queryByList", supplyOrderQuery);
    }

    @Override
    public int updateSupplyOrder(SupplyOrder supplyOrder) throws SQLException {
	return sqlMapClient.update("supply_order.updateSupplyOrder", supplyOrder);
    }

    @Override
    public SupplyOrder getSupplyOrderById(Long supplyOrderId) throws SQLException {
	return (SupplyOrder) sqlMapClient.queryForObject("supply_order.getSupplyOrderById", supplyOrderId);
    }

    @Override
    public SupplyOrder selectSupplyOrderById(Long id) throws SQLException {
	SupplyOrder key = new SupplyOrder();
	key.setId(id);
	SupplyOrder record = (SupplyOrder) sqlMapClient.queryForObject("supply_order.selectByPrimaryKey", key);
	return record;
    }

    @Override
    public Integer queryCount(SupplyOrderQuery supplyOrderQuery) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("supply_order.queryByPageCount", supplyOrderQuery);
	return count;
    }

    @Override
    public boolean updateSupplyOrderCheckStatus(SupplyOrder supplyOrder, List<Integer> statusList) throws SQLException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("supplyOrder", supplyOrder);
	params.put("statusList", statusList);
	return sqlMapClient.update("supply_order.updateSupplyOrderCheckStatus", params) > 0;
    }

    @Override
    public int countByExport(SupplyOrderQuery supplyOrderQuery) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("supply_order.countByExport", supplyOrderQuery);
	return count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SupplyOrder> queryByExport(SupplyOrderQuery supplyOrderQuery) throws SQLException {
	return (List<SupplyOrder>) sqlMapClient.queryForList("supply_order.queryByExport", supplyOrderQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SupplyOrder> selectByExport(SupplyOrderQuery supplyOrderQuery) throws SQLException {
	return (List<SupplyOrder>) sqlMapClient.queryForList("supply_order.selectByExport", supplyOrderQuery);
    }

    @Override
    public SupplyOrderAmount querySumAmount(SupplyOrderQuery supplyOrderQuery) throws SQLException {
	return (SupplyOrderAmount) sqlMapClient.queryForObject("supply_order.querySumAmount", supplyOrderQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SupplyOrder> getSaleExport(SupplyOrderQuery supplyOrderQuery) throws SQLException {
	List<SupplyOrder> list1 = (List<SupplyOrder>) sqlMapClient.queryForList("supply_order.queryByExport", supplyOrderQuery);
	List<SupplyOrder> list2 = new ArrayList<SupplyOrder>();
	for (SupplyOrder order1 : list1) {
	    SupplyOrder order2 = new SupplyOrder();
	    order2.setId(order1.getId());
	    order2.setSaleStatus(Constants.SupplyOrder.SALE_EXPORT);
	    int count = updateSupplyOrder(order2);
	    if (count > 0) {
		order2.setSupplyType(order1.getSupplyType());
		order2.setItemUid(order1.getItemUid());
		order2.setItemName(order1.getItemName());
		order2.setItemFacePrice(order1.getItemFacePrice());
		order2.setGmtCreate(order1.getGmtCreate());
		list2.add(order2);
	    }
	}
	list1.clear();
	return list2;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SupplyOrder> selectByCrawl(Long supplyTraderId) throws SQLException {
	return (List<SupplyOrder>) sqlMapClient.queryForList("supply_order.selectByCrawl", supplyTraderId);
    }

    @Override
    public int updateByCrawl(Long id) throws SQLException {
	return sqlMapClient.update("supply_order.updateByCrawl", id);
    }

    @Override
    public int updateRollbackCrawl(Long id) throws SQLException {
	return sqlMapClient.update("supply_order.updateRollbackCrawl", id);
    }
}
