package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.UserRoleRelationService;
import com.longan.biz.dao.UserRoleRelationDAO;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoQuery;
import com.longan.biz.domain.Result;

public class UserRoleRelationServiceImpl extends BaseService implements UserRoleRelationService {
    @Resource
    private UserRoleRelationDAO userRoleRelationDAO;

    @Override
    public Result<List<UserInfo>> queryUserRoleRelationList(UserInfoQuery userInfoQuery) {
	Result<List<UserInfo>> result = new Result<List<UserInfo>>();
	try {
	    List<UserInfo> queryResult = userRoleRelationDAO.queryUserRoleRelationByPage(userInfoQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("用户角色列表查询失败    msg: " + e.getMessage());
	    logger.error("queryUserRoleRelationList error ", e);
	}

	return result;
    }

}
