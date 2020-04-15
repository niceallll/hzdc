package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.QueryPayOrderDAO;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderQuery;

public class QueryPayOrderDAOImpl implements QueryPayOrderDAO {

	@Resource
	private SqlMapClient querySqlMapClient;

	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrder> queryByPage(PayOrderQuery payOrderQuery) throws SQLException {
		int count = (Integer) querySqlMapClient.queryForObject("pay_order.queryByPageCount",
				payOrderQuery);
		payOrderQuery.setTotalItem(count);
		return (List<PayOrder>) querySqlMapClient.queryForList("pay_order.queryByPage",
				payOrderQuery);
	}
}