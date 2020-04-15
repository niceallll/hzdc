package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.OperationRoleRelationDAO;
import com.longan.biz.dataobject.OperationRoleRelation;
import com.longan.biz.dataobject.OperationRoleRelationExample;

public class OperationRoleRelationDAOImpl implements OperationRoleRelationDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public void insert(OperationRoleRelation record) throws SQLException {
	sqlMapClient.insert("operation_role_relation.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(OperationRoleRelation record) throws SQLException {
	int rows = sqlMapClient.update("operation_role_relation.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(OperationRoleRelationExample example) throws SQLException {
	List list = sqlMapClient.queryForList("operation_role_relation.abatorgenerated_selectByExample", example);
	return list;
    }

    public OperationRoleRelation selectByPrimaryKey(Integer id) throws SQLException {
	OperationRoleRelation key = new OperationRoleRelation();
	key.setId(id);
	OperationRoleRelation record = (OperationRoleRelation) sqlMapClient.queryForObject(
		"operation_role_relation.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(OperationRoleRelationExample example) throws SQLException {
	int rows = sqlMapClient.delete("operation_role_relation.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Integer id) throws SQLException {
	OperationRoleRelation key = new OperationRoleRelation();
	key.setId(id);
	int rows = sqlMapClient.delete("operation_role_relation.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(OperationRoleRelationExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("operation_role_relation.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(OperationRoleRelation record, OperationRoleRelationExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("operation_role_relation.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends OperationRoleRelationExample {
	private Object record;

	public UpdateByExampleParms(Object record, OperationRoleRelationExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @Override
    public int deleteByRoleId(Integer roleId) throws SQLException {
	OperationRoleRelation key = new OperationRoleRelation();
	key.setRoleId(roleId);
	int rows = sqlMapClient.delete("operation_role_relation.abatorgenerated_deleteByRoleId", key);
	return rows;
    }

    @Override
    public Boolean batchInsert(List<OperationRoleRelation> operationRoleRelationList) throws Exception {
	sqlMapClient.startBatch();
	for (OperationRoleRelation operationRoleRelation : operationRoleRelationList) {
	    sqlMapClient.insert("operation_role_relation.abatorgenerated_insert", operationRoleRelation);
	}
	sqlMapClient.executeBatch();
	return true;
    }

}