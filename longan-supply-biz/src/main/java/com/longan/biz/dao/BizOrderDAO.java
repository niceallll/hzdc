package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderExample;
import com.longan.biz.dataobject.BizOrderQuery;


public interface BizOrderDAO {
    Long insert(BizOrder record) throws SQLException;

    int updateByPrimaryKeySelective(BizOrder record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(BizOrderExample example) throws SQLException;

    BizOrder selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(BizOrderExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(BizOrderExample example) throws SQLException;

    int updateByExampleSelective(BizOrder record, BizOrderExample example) throws SQLException;

    List<BizOrder> queryByPage(BizOrderQuery bizOrderQuery) throws SQLException;

    int countByExport(BizOrderQuery bizOrderQuery) throws SQLException;

    List<BizOrder> queryByExport(BizOrderQuery bizOrderQuery) throws SQLException;

    List<BizOrder> selectByExport(BizOrderQuery bizOrderQuery) throws SQLException;

    int lockBizOrder(BizOrder bizOrder) throws SQLException;

    int unLockBizOrder(BizOrder bizOrder) throws SQLException;

    int cancelBizOrder(BizOrder bizOrder) throws SQLException;

    int confirmBizOrder(BizOrder bizOrder) throws SQLException;

    int unConfirmBizOrder(BizOrder bizOrder) throws SQLException;

    int remarks(BizOrder bizOrder) throws SQLException;

    BizOrder selectRemaks(Long id) throws SQLException;
}