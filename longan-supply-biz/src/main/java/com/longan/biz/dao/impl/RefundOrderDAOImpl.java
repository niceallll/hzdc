package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.RefundOrderDAO;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderExample;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.sumobject.RefundOrderAmount;

public class RefundOrderDAOImpl implements RefundOrderDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(RefundOrder record) throws SQLException {
	return (Long) sqlMapClient.insert("refund_order.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(RefundOrder record) throws SQLException {
	int rows = sqlMapClient.update("refund_order.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(RefundOrderExample example) throws SQLException {
	List list = sqlMapClient.queryForList("refund_order.abatorgenerated_selectByExample", example);
	return list;
    }

    public RefundOrder selectByPrimaryKey(Long id) throws SQLException {
	RefundOrder key = new RefundOrder();
	key.setId(id);
	RefundOrder record = (RefundOrder) sqlMapClient.queryForObject("refund_order.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(RefundOrderExample example) throws SQLException {
	int rows = sqlMapClient.delete("refund_order.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	RefundOrder key = new RefundOrder();
	key.setId(id);
	int rows = sqlMapClient.delete("refund_order.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(RefundOrderExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("refund_order.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(RefundOrder record, RefundOrderExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("refund_order.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends RefundOrderExample {
	private Object record;

	public UpdateByExampleParms(Object record, RefundOrderExample example) {
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
    public List<RefundOrder> queryByPage(RefundOrderQuery refundOrderQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("refund_order.queryByPageCount", refundOrderQuery);
	refundOrderQuery.setTotalItem(count);
	return (List<RefundOrder>) sqlMapClient.queryForList("refund_order.queryByPage", refundOrderQuery);
    }

    @Override
    public int countByExport(RefundOrderQuery refundOrderQuery) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("refund_order.countByExport", refundOrderQuery);
	return count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RefundOrder> queryByExport(RefundOrderQuery refundOrderQuery) throws SQLException {
	return (List<RefundOrder>) sqlMapClient.queryForList("refund_order.queryByExport", refundOrderQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RefundOrder> selectByExport(RefundOrderQuery refundOrderQuery) throws SQLException {
	return (List<RefundOrder>) sqlMapClient.queryForList("refund_order.selectByExport", refundOrderQuery);
    }

    @Override
    public RefundOrderAmount querySumAmount(RefundOrderQuery refundOrderQuery) throws SQLException {
	return (RefundOrderAmount) sqlMapClient.queryForObject("refund_order.querySumAmount", refundOrderQuery);
    }
}