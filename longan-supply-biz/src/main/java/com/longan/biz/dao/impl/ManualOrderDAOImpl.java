package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.ManualOrderDAO;
import com.longan.biz.dataobject.ManualOrder;

public class ManualOrderDAOImpl implements ManualOrderDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public ManualOrder selectByPrimaryKey(Long id) throws SQLException {
	return (ManualOrder) sqlMapClient.queryForObject("manual_order.selectByPrimaryKey", id);
    }

    @Override
    public Long insert(ManualOrder manualOrder) throws SQLException {
	return (Long) sqlMapClient.insert("manual_order.insert", manualOrder);
    }

    @Override
    public int updateByPrimaryKeySelective(ManualOrder manualOrder) throws SQLException {
	int rows = sqlMapClient.update("manual_order.updateByPrimaryKeySelective", manualOrder);
	return rows;
    }

    @Override
    public ManualOrder selectBySerialno(String serialno) throws SQLException {
	return (ManualOrder) sqlMapClient.queryForObject("manual_order.selectBySerialno", serialno);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ManualOrder> selectByLogId(Long logId) throws SQLException {
	return (List<ManualOrder>) sqlMapClient.queryForList("manual_order.selectByLogId", logId);
    }
}
