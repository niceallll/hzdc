package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.QueryBizOrderDAO;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;

public class QueryBizOrderDAOImpl implements QueryBizOrderDAO {
    @Resource
    private SqlMapClient querySqlMapClient;

    @SuppressWarnings("unchecked")
    @Override
    public List<BizOrder> queryByPage(BizOrderQuery bizOrderQuery) throws SQLException {
        if (bizOrderQuery.getMemo()==null){
            int count = (Integer) querySqlMapClient.queryForObject("biz_order.queryByPageCount", bizOrderQuery);
            bizOrderQuery.setTotalItem(count);
            return (List<BizOrder>) querySqlMapClient.queryForList("biz_order.queryByPage", bizOrderQuery);
        }else {
            int count = (Integer) querySqlMapClient.queryForObject("biz_order.queryByPageCountly", bizOrderQuery);
            bizOrderQuery.setTotalItem(count);
            return (List<BizOrder>) querySqlMapClient.queryForList("biz_order.queryByPagely", bizOrderQuery);
    }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BizOrder> queryByExport(BizOrderQuery bizOrderQuery) throws SQLException {
	return (List<BizOrder>) querySqlMapClient.queryForList("biz_order.queryByExport", bizOrderQuery);
    }

    @Override
    public Integer queryCount(BizOrderQuery bizOrderQuery) throws SQLException {
	Integer count = (Integer) querySqlMapClient.queryForObject("biz_order.queryByPageCount", bizOrderQuery);
	return count;
    }
}