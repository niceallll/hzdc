package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.UserInfoDAO;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoExample;
import com.longan.biz.dataobject.UserInfoQuery;

public class UserInfoDAOImpl implements UserInfoDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Long insert(UserInfo record) throws SQLException {
	return (Long) sqlMapClient.insert("user_info.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(UserInfo record) throws SQLException {
	int rows = sqlMapClient.update("user_info.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(UserInfoExample example) throws SQLException {
	List list = sqlMapClient.queryForList("user_info.abatorgenerated_selectByExample", example);
	return list;
    }

    public UserInfo selectByPrimaryKey(Long id) throws SQLException {
	UserInfo key = new UserInfo();
	key.setId(id);
	UserInfo record = (UserInfo) sqlMapClient.queryForObject("user_info.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    @Override
    public UserInfo selectDownStreamByPrimaryKey(Long id) throws SQLException {
	UserInfo key = new UserInfo();
	key.setId(id);
	UserInfo record = (UserInfo) sqlMapClient.queryForObject("user_info.abatorgenerated_selectDownStreamByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(UserInfoExample example) throws SQLException {
	int rows = sqlMapClient.delete("user_info.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
	UserInfo key = new UserInfo();
	key.setId(id);
	int rows = sqlMapClient.delete("user_info.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(UserInfoExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("user_info.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(UserInfo record, UserInfoExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("user_info.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends UserInfoExample {
	private Object record;

	public UpdateByExampleParms(Object record, UserInfoExample example) {
	    super(example);
	    this.record = record;
	}

	@SuppressWarnings("unused")
	public Object getRecord() {
	    return record;
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserInfo> queryByPage(UserInfoQuery userInfoQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("user_info.queryByPageCount", userInfoQuery);
	userInfoQuery.setTotalItem(count);
	return (List<UserInfo>) sqlMapClient.queryForList("user_info.queryByPage", userInfoQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserInfo> queryDownStreamByPage(UserInfoQuery userInfoQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("user_info.queryForPageCount", userInfoQuery);
	userInfoQuery.setTotalItem(count);
	return (List<UserInfo>) sqlMapClient.queryForList("user_info.queryDownStreamInfoForPage", userInfoQuery);
    }

}