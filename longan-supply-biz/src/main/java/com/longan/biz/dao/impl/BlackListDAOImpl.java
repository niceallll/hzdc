package com.longan.biz.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.BlackListDAO;

public class BlackListDAOImpl implements BlackListDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public int countByItemUid(String itemUid) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("black_list.abatorgenerated_countByItemUid", itemUid);
	return count.intValue();
    }
}
