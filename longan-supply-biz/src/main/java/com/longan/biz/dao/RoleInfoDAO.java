package com.longan.biz.dao;

import java.sql.SQLException;
import java.util.List;

import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.RoleInfoExample;
import com.longan.biz.dataobject.RoleInfoQuery;

public interface RoleInfoDAO {
	Integer insert(RoleInfo record) throws SQLException;

	int updateByPrimaryKeySelective(RoleInfo record) throws SQLException;

	@SuppressWarnings("rawtypes")
	List selectByExample(RoleInfoExample example) throws SQLException;

	RoleInfo selectByPrimaryKey(Integer id) throws SQLException;

	int deleteByExample(RoleInfoExample example) throws SQLException;

	int deleteByPrimaryKey(Integer id) throws SQLException;

	int countByExample(RoleInfoExample example) throws SQLException;

	int updateByExampleSelective(RoleInfo record, RoleInfoExample example) throws SQLException;

	List<RoleInfo> queryRoleInfoByPage(RoleInfoQuery roleInfoQuery) throws SQLException;

	List<RoleInfo> queryAllRoleInfoList() throws SQLException;
}