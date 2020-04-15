package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.OperationInfoDAO;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationInfoExample;
import com.longan.biz.dataobject.OperationInfoQuery;

public class OperationInfoDAOImpl implements OperationInfoDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Integer insert(OperationInfo record) throws SQLException {
	return (Integer) sqlMapClient.insert("operation_info.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(OperationInfo record) throws SQLException {
	int rows = sqlMapClient.update("operation_info.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(OperationInfoExample example) throws SQLException {
	List list = sqlMapClient.queryForList("operation_info.abatorgenerated_selectByExample", example);
	return list;
    }

    public OperationInfo selectByPrimaryKey(Integer id) throws SQLException {
	OperationInfo key = new OperationInfo();
	key.setId(id);
	OperationInfo record = (OperationInfo) sqlMapClient.queryForObject("operation_info.abatorgenerated_selectByPrimaryKey",
		key);
	return record;
    }

    public int deleteByExample(OperationInfoExample example) throws SQLException {
	int rows = sqlMapClient.delete("operation_info.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Integer id) throws SQLException {
	OperationInfo key = new OperationInfo();
	key.setId(id);
	int rows = sqlMapClient.delete("operation_info.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(OperationInfoExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("operation_info.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(OperationInfo record, OperationInfoExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("operation_info.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends OperationInfoExample {
	private Object record;

	public UpdateByExampleParms(Object record, OperationInfoExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @SuppressWarnings("unchecked")
    public List<OperationInfo> selectOperationListByRole(Integer roleId, Integer type) throws SQLException {
	Map<String, Object> map = new HashMap<String, Object>(2);
	map.put("roleId", roleId);
	map.put("type", type);
	List<OperationInfo> result = (List<OperationInfo>) sqlMapClient.queryForList("operation_info.selectOperationListByRole",
		map);
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OperationInfo> queryOperationInfoByPage(OperationInfoQuery operationInfoQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("operation_info.queryByPageCount", operationInfoQuery);
	operationInfoQuery.setTotalItem(count);
	return (List<OperationInfo>) sqlMapClient.queryForList("operation_info.queryByPage", operationInfoQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OperationInfo> queryOperationListByType(Integer type) throws SQLException {
	List<OperationInfo> list = (List<OperationInfo>) sqlMapClient.queryForList("operation_info.queryByType", type);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OperationInfo> queryAllOperationList() throws SQLException {
	List<OperationInfo> list = (List<OperationInfo>) sqlMapClient.queryForList("operation_info.queryAll");
	return list;
    }
}