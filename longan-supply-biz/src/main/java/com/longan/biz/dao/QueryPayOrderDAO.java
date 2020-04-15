package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderQuery;

public interface QueryPayOrderDAO {
	List<PayOrder> queryByPage(PayOrderQuery payOrderQuery) throws SQLException;
}