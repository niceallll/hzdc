package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.ExportDAO;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.ExportQuery;

public class ExportDAOImpl implements ExportDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(Export export) throws SQLException {
	return (Long) sqlMapClient.insert("export.insert", export);
    }

    @Override
    public int updateByPrimaryKeySelective(Export export) throws SQLException {
	int rows = sqlMapClient.update("export.updateByPrimaryKeySelective", export);
	return rows;
    }

    @Override
    public Export selectByPrimaryKey(Long id) throws SQLException {
	Export key = new Export();
	key.setId(id);
	Export export = (Export) sqlMapClient.queryForObject("export.selectByPrimaryKey", key);
	return export;
    }

    @Override
    public int deleteByPrimaryKey(Long id) throws SQLException {
	Export key = new Export();
	key.setId(id);
	int rows = sqlMapClient.delete("export.deleteByPrimaryKey", key);
	return rows;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Export> queryByPage(ExportQuery exportQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("export.queryByPageCount", exportQuery);
	exportQuery.setTotalItem(count);
	return (List<Export>) sqlMapClient.queryForList("export.queryByPage", exportQuery);
    }
}
