package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.ManualLogDAO;
import com.longan.biz.dataobject.ManualLog;
import com.longan.biz.dataobject.ManualLogQuery;

public class ManualLogDAOImpl implements ManualLogDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(ManualLog manualLog) throws SQLException {
	return (Long) sqlMapClient.insert("manual_log.insert", manualLog);
    }

    @Override
    public int updateByPrimaryKeySelective(ManualLog manualLog) throws SQLException {
	int rows = sqlMapClient.update("manual_log.updateByPrimaryKeySelective", manualLog);
	return rows;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ManualLog> queryByPage(ManualLogQuery manualLogQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("manual_log.queryByPageCount", manualLogQuery);
	manualLogQuery.setTotalItem(count);
	return (List<ManualLog>) sqlMapClient.queryForList("manual_log.queryByPage", manualLogQuery);
    }

    @Override
    public ManualLog selectForUpdate(Long id) throws SQLException {
	return (ManualLog) sqlMapClient.queryForObject("manual_log.forUpdateOneRow", id);
    }
}
