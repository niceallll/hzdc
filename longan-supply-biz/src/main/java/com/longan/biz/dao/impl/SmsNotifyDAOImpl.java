package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.SmsNotifyDAO;
import com.longan.biz.dataobject.SmsNotify;
import com.longan.biz.dataobject.SmsNotifyQuery;

public class SmsNotifyDAOImpl implements SmsNotifyDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsNotify> queryByPage(SmsNotifyQuery smsNotifyQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("sms_notify.queryByPageCount", smsNotifyQuery);
	smsNotifyQuery.setTotalItem(count);
	return (List<SmsNotify>) sqlMapClient.queryForList("sms_notify.queryByPage", smsNotifyQuery);
    }

    @Override
    public Long insert(SmsNotify record) throws SQLException {
	return (Long) sqlMapClient.insert("sms_notify.insert", record);
    }

    @Override
    public SmsNotify selectByPrimaryKey(Long id) throws SQLException {
	SmsNotify key = new SmsNotify();
	key.setId(id);
	SmsNotify record = (SmsNotify) sqlMapClient.queryForObject("sms_notify.selectByPrimaryKey", key);
	return record;
    }
}
