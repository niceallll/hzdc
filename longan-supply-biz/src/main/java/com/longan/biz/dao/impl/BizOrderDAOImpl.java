package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.BizOrderDAO;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderExample;
import com.longan.biz.dataobject.BizOrderQuery;

public class BizOrderDAOImpl implements BizOrderDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(BizOrder record) throws SQLException {
        return (Long) sqlMapClient.insert("biz_order.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(BizOrder record) throws SQLException {
        int rows = sqlMapClient.update("biz_order.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(BizOrderExample example) throws SQLException {
        List list = sqlMapClient.queryForList("biz_order.abatorgenerated_selectByExample", example);
        return list;
    }

    public BizOrder selectByPrimaryKey(Long id) throws SQLException {
        BizOrder key = new BizOrder();
        key.setId(id);
        BizOrder record = (BizOrder) sqlMapClient.queryForObject("biz_order.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int deleteByExample(BizOrderExample example) throws SQLException {
        int rows = sqlMapClient.delete("biz_order.abatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
        BizOrder key = new BizOrder();
        key.setId(id);
        int rows = sqlMapClient.delete("biz_order.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public int countByExample(BizOrderExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("biz_order.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int updateByExampleSelective(BizOrder record, BizOrderExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("biz_order.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    private static class UpdateByExampleParms extends BizOrderExample {
        private Object record;

        public UpdateByExampleParms(Object record, BizOrderExample example) {
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
    public List<BizOrder> queryByPage(BizOrderQuery bizOrderQuery) throws SQLException {
        int count = (Integer) sqlMapClient.queryForObject("biz_order.queryByPageCount", bizOrderQuery);
        bizOrderQuery.setTotalItem(count);
        return (List<BizOrder>) sqlMapClient.queryForList("biz_order.queryByPage", bizOrderQuery);
    }

    @Override
    public int countByExport(BizOrderQuery bizOrderQuery) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("biz_order.countByExport", bizOrderQuery);
        return count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BizOrder> queryByExport(BizOrderQuery bizOrderQuery) throws SQLException {
        return (List<BizOrder>) sqlMapClient.queryForList("biz_order.queryByExport", bizOrderQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BizOrder> selectByExport(BizOrderQuery bizOrderQuery) throws SQLException {
        return (List<BizOrder>) sqlMapClient.queryForList("biz_order.selectByExport", bizOrderQuery);
    }

    @Override
    public int lockBizOrder(BizOrder bizOrder) throws SQLException {
        int rows = sqlMapClient.update("biz_order.lockBizOrder", bizOrder);
        return rows;
    }

    @Override
    public int unLockBizOrder(BizOrder bizOrder) throws SQLException {
        int rows = sqlMapClient.update("biz_order.unLockBizOrder", bizOrder);
        return rows;
    }

    @Override
    public int cancelBizOrder(BizOrder bizOrder) throws SQLException {
        int rows = sqlMapClient.update("biz_order.cancelBizOrder", bizOrder);
        return rows;
    }

    @Override
    public int confirmBizOrder(BizOrder bizOrder) throws SQLException {
        int rows = sqlMapClient.update("biz_order.confirmBizOrder", bizOrder);
        return rows;
    }

    @Override
    public int unConfirmBizOrder(BizOrder bizOrder) throws SQLException {
        int rows = sqlMapClient.update("biz_order.unConfirmBizOrder", bizOrder);
        return rows;
    }

    @Override
    public int remarks(BizOrder bizOrder) throws SQLException {
        int rows = sqlMapClient.update("biz_order.remarks", bizOrder);
        return rows;
    }

    @Override
    public BizOrder selectRemaks(Long id) throws SQLException {
        BizOrder key = new BizOrder();
        key.setId(id);
        BizOrder bizOrder = (BizOrder) sqlMapClient.queryForObject("biz_order.abatorgenerated_selectByPrimaryKey", key);
        return bizOrder;
    }
}