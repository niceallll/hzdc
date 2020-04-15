package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogExample;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.sumobject.AcctLogAmount;

public interface AcctLogDAO {
    Long insert(AcctLog record) throws SQLException;

    int updateByPrimaryKeySelective(AcctLog record) throws SQLException;

    @SuppressWarnings("rawtypes")
    List selectByExample(AcctLogExample example) throws SQLException;

    AcctLog selectByPrimaryKey(Long id) throws SQLException;

    int deleteByExample(AcctLogExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    int countByExample(AcctLogExample example) throws SQLException;

    int updateByExampleSelective(AcctLog record, AcctLogExample example) throws SQLException;

    List<AcctLog> queryByPage(AcctLogQuery acctLogQuery) throws SQLException;

    int countByExport(AcctLogQuery acctLogQuery) throws SQLException;

    List<AcctLog> queryByExport(AcctLogQuery acctLogQuery) throws SQLException;

    List<AcctLog> selectByExport(AcctLogQuery acctLogQuery) throws SQLException;

    AcctLogAmount querySumAmount(AcctLogQuery acctLogQuery) throws SQLException;
}