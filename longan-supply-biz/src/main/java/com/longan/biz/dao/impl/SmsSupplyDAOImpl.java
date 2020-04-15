package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.SmsSupplyDAO;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.sumobject.SmsOrderCount;

public class SmsSupplyDAOImpl implements SmsSupplyDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsSupply> queryByPage(SmsSupplyQuery smsSupplyQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("sms_supply.queryByPageCount", smsSupplyQuery);
	smsSupplyQuery.setTotalItem(count);
	return (List<SmsSupply>) sqlMapClient.queryForList("sms_supply.queryByPage", smsSupplyQuery);
    }

    @Override
    public SmsOrderCount querySumCount(SmsSupplyQuery smsSupplyQuery) throws SQLException {
	return (SmsOrderCount) sqlMapClient.queryForObject("sms_supply.querySumCount", smsSupplyQuery);
    }

    @Override
    public int countByExport(SmsSupplyQuery smsSupplyQuery) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("sms_supply.countByExport", smsSupplyQuery);
	return count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsSupply> queryByExport(SmsSupplyQuery smsSupplyQuery) throws SQLException {
	return (List<SmsSupply>) sqlMapClient.queryForList("sms_supply.queryByExport", smsSupplyQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsSupply> selectByExport(SmsSupplyQuery smsSupplyQuery) throws SQLException {
	return (List<SmsSupply>) sqlMapClient.queryForList("sms_supply.selectByExport", smsSupplyQuery);
    }

    @Override
    public Long insert(SmsSupply record) throws SQLException {
	return (Long) sqlMapClient.insert("sms_supply.insert", record);
    }

    @Override
    public int updateByPrimaryKeySelective(SmsSupply record) throws SQLException {
	int rows = sqlMapClient.update("sms_supply.updateByPrimaryKeySelective", record);
	return rows;
    }

    @Override
    public boolean updateSmsSupplyCheckStatus(SmsSupply smsSupply, List<Integer> statusList) throws SQLException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("smsSupply", smsSupply);
	params.put("statusList", statusList);
	return sqlMapClient.update("sms_supply.updateSmsSupplyCheckStatus", params) > 0;
    }

    @Override
    public SmsSupply selectByPrimaryKey(Long id) throws SQLException {
	SmsSupply key = new SmsSupply();
	key.setId(id);
	SmsSupply record = (SmsSupply) sqlMapClient.queryForObject("sms_supply.selectByPrimaryKey", key);
	return record;
    }

    @Override
    public SmsSupply selectBySerialno(Long supplyTraderId, String upstreamSerialno) throws SQLException {
	SmsSupply key = new SmsSupply();
	key.setSupplyTraderId(supplyTraderId);
	key.setUpstreamSerialno(upstreamSerialno);
	SmsSupply record = (SmsSupply) sqlMapClient.queryForObject("sms_supply.selectBySerialno", key);
	return record;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsSupply> selectBySmsOrderId(Long smsOrderId) throws SQLException {
	return (List<SmsSupply>) sqlMapClient.queryForList("sms_supply.selectBySmsOrderId", smsOrderId);
    }
}
