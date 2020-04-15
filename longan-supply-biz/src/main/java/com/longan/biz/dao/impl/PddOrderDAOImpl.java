package com.longan.biz.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.PddOrderDao;
import com.longan.biz.dataobject.PddOrder;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

public class PddOrderDAOImpl implements PddOrderDao {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public PddOrder selectByPrimaryKey(Long id) throws SQLException {
        PddOrder key = new PddOrder();
        PddOrder pddOrder = (PddOrder) sqlMapClient.queryForObject("pddOrder.abatorgenerated_selectByPrimaryKey", key);
        return pddOrder;
    }

    @Override
    public Long insert(PddOrder pddOrder) throws SQLException {
        return (Long) sqlMapClient.insert("pddOrder.pddInsert", pddOrder);
    }

    @Override
    public List<PddOrder> select(PddOrder pddOrder) throws SQLException {
        return (List<PddOrder>) sqlMapClient.queryForList("pddOrder.pddoderselect", pddOrder);
    }
}
