package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;

public interface QueryBizOrderDAO {
    List<BizOrder> queryByPage(BizOrderQuery bizOrderQuery) throws SQLException;

    List<BizOrder> queryByExport(BizOrderQuery bizOrderQuery) throws SQLException;

    Integer queryCount(BizOrderQuery bizOrderQuery) throws SQLException;
}