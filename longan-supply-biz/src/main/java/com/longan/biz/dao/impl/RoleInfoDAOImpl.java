package com.longan.biz.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.longan.biz.dao.RoleInfoDAO;
import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.RoleInfoExample;
import com.longan.biz.dataobject.RoleInfoQuery;

public class RoleInfoDAOImpl implements RoleInfoDAO {
    @Resource
    private SqlMapClient sqlMapClient;

    public Integer insert(RoleInfo record) throws SQLException {
	return (Integer) sqlMapClient.insert("role_info.abatorgenerated_insert", record);
    }

    public int updateByPrimaryKeySelective(RoleInfo record) throws SQLException {
	int rows = sqlMapClient.update("role_info.abatorgenerated_updateByPrimaryKeySelective", record);
	return rows;
    }

    @SuppressWarnings("rawtypes")
    public List selectByExample(RoleInfoExample example) throws SQLException {
	List list = sqlMapClient.queryForList("role_info.abatorgenerated_selectByExample", example);
	return list;
    }

    public RoleInfo selectByPrimaryKey(Integer id) throws SQLException {
	RoleInfo key = new RoleInfo();
	key.setId(id);
	RoleInfo record = (RoleInfo) sqlMapClient.queryForObject("role_info.abatorgenerated_selectByPrimaryKey", key);
	return record;
    }

    public int deleteByExample(RoleInfoExample example) throws SQLException {
	int rows = sqlMapClient.delete("role_info.abatorgenerated_deleteByExample", example);
	return rows;
    }

    public int deleteByPrimaryKey(Integer id) throws SQLException {
	RoleInfo key = new RoleInfo();
	key.setId(id);
	int rows = sqlMapClient.delete("role_info.abatorgenerated_deleteByPrimaryKey", key);
	return rows;
    }

    public int countByExample(RoleInfoExample example) throws SQLException {
	Integer count = (Integer) sqlMapClient.queryForObject("role_info.abatorgenerated_countByExample", example);
	return count.intValue();
    }

    public int updateByExampleSelective(RoleInfo record, RoleInfoExample example) throws SQLException {
	UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
	int rows = sqlMapClient.update("role_info.abatorgenerated_updateByExampleSelective", parms);
	return rows;
    }

    private static class UpdateByExampleParms extends RoleInfoExample {
	private Object record;

	public UpdateByExampleParms(Object record, RoleInfoExample example) {
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
    public List<RoleInfo> queryRoleInfoByPage(RoleInfoQuery roleInfoQuery) throws SQLException {
	int count = (Integer) sqlMapClient.queryForObject("role_info.queryByPageCount", roleInfoQuery);
	roleInfoQuery.setTotalItem(count);
	return (List<RoleInfo>) sqlMapClient.queryForList("role_info.queryByPage", roleInfoQuery);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RoleInfo> queryAllRoleInfoList() throws SQLException {
	List<RoleInfo> list = (List<RoleInfo>) sqlMapClient.queryForList("role_info.queryAll");
	return list;
    }

}