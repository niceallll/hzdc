package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.UserRoleRelationDAO;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoQuery;
import com.longan.biz.dataobject.UserRoleRelation;
import com.longan.biz.dataobject.UserRoleRelationExample;

public class UserRoleRelationDAOImpl implements UserRoleRelationDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public void insert(UserRoleRelation record) throws SQLException {
	sqlMapClient.insert("user_role_relation.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(UserRoleRelation record) throws SQLException {
	int rows = sqlMapClient.update("user_role_relation.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(UserRoleRelationExample example) throws SQLException {
	List list = sqlMapClient.queryForList("user_role_relation.abatorgenerated_selectByExample", example);
	return list;
    }

    public UserRoleRelation selectByPrimaryKey(Integer id) throws SQLException {
	UserRoleRelation key = new UserRoleRelation();
	key.setId(id);
	UserRoleRelation record = (UserRoleRelation) sqlMapClient.queryForObject(
		"user_role_relation.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(UserRoleRelationExample example) throws SQLException {
	int rows = sqlMapClient.delete("user_role_relation.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Integer id) throws SQLException {
	UserRoleRelation key = new UserRoleRelation();
	key.setId(id);
	int rows = sqlMapClient.delete("user_role_relation.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(UserRoleRelationExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("user_role_relation.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(UserRoleRelation record, UserRoleRelationExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("user_role_relation.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends UserRoleRelationExample {
	private Object record;

	public UpdateByExampleParms(Object record, UserRoleRelationExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @Override
    public Boolean batchInsert(List<UserRoleRelation> userRoleRelationList) throws Exception {
	sqlMapClient.startBatch();
	for (UserRoleRelation userRoleRelation : userRoleRelationList) {
	    sqlMapClient.insert("user_role_relation.abatorgenerated_insert", userRoleRelation);
	}
	sqlMapClient.executeBatch();
	return true;
    }

    @Override
    public int deleteByUserId(Long userId) throws SQLException {
	UserRoleRelation key = new UserRoleRelation();
	key.setUserId(userId);
	int rows = sqlMapClient.delete("user_role_relation.abatorgenerated_deleteByUserId", key);
	return rows;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserInfo> queryUserRoleRelationByPage(UserInfoQuery userInfoQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("user_role_relation.queryForPageCount", userInfoQuery);
	userInfoQuery.setTotalItem(count);
	return (List<UserInfo>) sqlMapClient.queryForList("user_role_relation.queryUserRoleRelationByPage", userInfoQuery);
    }

}