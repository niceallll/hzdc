package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.RoleInfoService;
import com.longan.biz.dao.RoleInfoDAO;
import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.RoleInfoExample;
import com.longan.biz.dataobject.RoleInfoQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class RoleInfoServiceImpl extends BaseService implements RoleInfoService {
    @Resource
    private RoleInfoDAO roleInfoDAO;

    @Override
    public Result<List<RoleInfo>> queryRoleInfoList(RoleInfoQuery roleInfoQuery) {
	Result<List<RoleInfo>> result = new Result<List<RoleInfo>>();
	try {
	    List<RoleInfo> queryResult = roleInfoDAO.queryRoleInfoByPage(roleInfoQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("角色列表查询失败    msg: " + e.getMessage());
	    logger.error("queryRoleInfoQueryList error ", e);
	}

	return result;
    }

    @Override
    public Result<RoleInfo> getRoleInfoById(Integer id) {
	Result<RoleInfo> result = new Result<RoleInfo>();
	try {
	    RoleInfo roleInfo = roleInfoDAO.selectByPrimaryKey(id);
	    if (roleInfo != null) {
		result.setSuccess(true);
		result.setModule(roleInfo);
	    } else {
		result.setResultMsg("不存在该角色信息 ");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("角色信息查询异常  msg : " + e.getMessage());
	    logger.error("getRoleInfo error  roleInfoId = " + id, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Boolean> updateRoleInfo(RoleInfo roleInfo) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    RoleInfoExample example = new RoleInfoExample();
	    example.createCriteria().andRoleNameEqualTo(roleInfo.getRoleName());
	    List<RoleInfo> list = (List<RoleInfo>) roleInfoDAO.selectByExample(example);
	    if (list.size() > 0) {
		for (RoleInfo roleInfoTemp : list) {
		    if (!roleInfo.getId().equals(roleInfoTemp.getId())) {
			result.setResultMsg("该角色名称已存在");
			return result;
		    }
		}
	    }
	    example = new RoleInfoExample();
	    example.createCriteria().andRoleNameEqualTo(roleInfo.getRoleDesc());
	    list = (List<RoleInfo>) roleInfoDAO.selectByExample(example);
	    if (list.size() > 0) {
		for (RoleInfo roleInfoTemp : list) {
		    if (!roleInfo.getId().equals(roleInfoTemp.getId())) {
			result.setResultMsg("该角色注释已存在");
			return result;
		    }
		}
	    }
	    int row = roleInfoDAO.updateByPrimaryKeySelective(roleInfo);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("更新角色信息失败    msg: " + e.getMessage());
	    logger.error("updateRoleInfo error ", e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Boolean> createRoleInfo(RoleInfo roleInfo) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    RoleInfoExample example = new RoleInfoExample();
	    example.createCriteria().andRoleNameEqualTo(roleInfo.getRoleName());
	    List<RoleInfo> list = (List<RoleInfo>) roleInfoDAO.selectByExample(example);
	    if (list.size() > 0) {
		result.setResultMsg("该角色名称已存在");
		return result;
	    }
	    example = new RoleInfoExample();
	    example.createCriteria().andRoleNameEqualTo(roleInfo.getRoleDesc());
	    list = (List<RoleInfo>) roleInfoDAO.selectByExample(example);
	    if (list.size() > 0) {
		result.setResultMsg("该解释名称已存在");
		return result;
	    }
	    roleInfo.setStatus(Constants.RoleInfo.STATUS_NORMAL);
	    Integer id = roleInfoDAO.insert(roleInfo);
	    if (id == null) {
		result.setResultMsg("新增角色信息失败");
		return result;
	    }
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (Exception e) {
	    result.setResultMsg("新增角色信息失败 ");
	    logger.error("createRoleInfo error RoleInfoName : " + roleInfo.getRoleDesc(), e);
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<RoleInfo>> queryAllRoleInfo() {
	Result<List<RoleInfo>> result = new Result<List<RoleInfo>>();
	try {
	    List<RoleInfo> queryResult = roleInfoDAO.queryAllRoleInfoList();
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("角色信息列表查询失败    msg: " + e.getMessage());
	    logger.error("queryAllRoleInfo error ", e);
	}

	return result;
    }

}
