package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.ExportQuery;

public interface ExportDAO {
    Long insert(Export export) throws SQLException;

    int updateByPrimaryKeySelective(Export export) throws SQLException;

    Export selectByPrimaryKey(Long id) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    List<Export> queryByPage(ExportQuery exportQuery) throws SQLException;
}
