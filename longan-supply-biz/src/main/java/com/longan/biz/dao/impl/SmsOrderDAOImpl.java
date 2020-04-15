package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.SmsOrderDAO;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderExample;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.sumobject.SmsOrderCount;

public class SmsOrderDAOImpl implements SmsOrderDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsOrder> queryByPage(SmsOrderQuery smsOrderQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("sms_order.queryByPageCount", smsOrderQuery);
	smsOrderQuery.setTotalItem(count);
	return (List<SmsOrder>) sqlMapClient.queryForList("sms_order.queryByPage", smsOrderQuery);
    }

    @Override
    public SmsOrderCount querySumCount(SmsOrderQuery smsOrderQuery) throws SQLException {
	return (SmsOrderCount) sqlMapClient.queryForObject("sms_order.querySumCount", smsOrderQuery);
    }

    @Override
    public int countByExport(SmsOrderQuery smsOrderQuery) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("sms_order.countByExport", smsOrderQuery);
	return count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsOrder> queryByExport(SmsOrderQuery smsOrderQuery) throws SQLException {
	return (List<SmsOrder>) sqlMapClient.queryForList("sms_order.queryByExport", smsOrderQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsOrder> selectByExport(SmsOrderQuery smsOrderQuery) throws SQLException {
	return (List<SmsOrder>) sqlMapClient.queryForList("sms_order.selectByExport", smsOrderQuery);
    }

    @Override
    public Long insert(SmsOrder record) throws SQLException {
	return (Long) sqlMapClient.insert("sms_order.insert", record);
    }

    @Override
    public int updateByPrimaryKeySelective(SmsOrder record) throws SQLException {
	int rows = sqlMapClient.update("sms_order.updateByPrimaryKeySelective", record);
	return rows;
    }

    @Override
    public boolean updateSmsOrderCheckStatus(SmsOrder smsOrder, List<Integer> statusList) throws SQLException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("smsOrder", smsOrder);
	params.put("statusList", statusList);
	return sqlMapClient.update("sms_order.updateSmsOrderCheckStatus", params) > 0;
    }

    @Override
    public SmsOrder selectByPrimaryKey(Long id) throws SQLException {
	SmsOrder key = new SmsOrder();
	key.setId(id);
	SmsOrder record = (SmsOrder) sqlMapClient.queryForObject("sms_order.selectByPrimaryKey", key);
	return record;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List selectByExample(SmsOrderExample example) throws SQLException {
	List list = sqlMapClient.queryForList("sms_order.selectByExample", example);
	return list;
    }
}
