package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.AuthService;
import com.longan.biz.core.BaseService;
import com.longan.biz.dao.OperationInfoDAO;
import com.longan.biz.dao.OperationRoleRelationDAO;
import com.longan.biz.dao.UserRoleRelationDAO;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationRoleRelation;
import com.longan.biz.dataobject.OperationRoleRelationExample;
import com.longan.biz.dataobject.UserRoleRelation;
import com.longan.biz.dataobject.UserRoleRelationExample;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class AuthServiceImpl extends BaseService implements AuthService {
    @Resource
    private UserRoleRelationDAO userRoleRelationDAO;

    @Resource
    private OperationRoleRelationDAO operationRoleRelationDAO;

    @Resource
    private OperationInfoDAO operationInfoDAO;

    @SuppressWarnings("unchecked")
    @Override
    public Result<Set<String>> getAuthCatalogByUserId(Long userId) {
	Result<Set<String>> result = new Result<Set<String>>();
	UserRoleRelationExample example = new UserRoleRelationExample();
	example.createCriteria().andUserIdEqualTo(userId);

	Set<String> catalogSet = new HashSet<String>();
	try {
	    List<UserRoleRelation> list = userRoleRelationDAO.selectByExample(example);
	    if (list != null) {
		for (UserRoleRelation userRoleRelation : list) {
		    List<OperationInfo> operationInfoList = operationInfoDAO.selectOperationListByRole(
			    userRoleRelation.getRoleId(), Constants.OperationInfo.TYPE_CATALOG);
		    for (OperationInfo operationInfo : operationInfoList) {
			if (StringUtils.isNotEmpty(operationInfo.getOperationCode())) {
			    catalogSet.add(operationInfo.getOperationCode());
			}
		    }
		}
	    }
	    result.setSuccess(true);
	    result.setModule(catalogSet);
	} catch (SQLException e) {
	    result.setResultMsg("getAuthCatalogByUserId error msg : " + e.getMessage());
	    logger.error("getAuthCatalogByUserId error userId : " + userId, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Set<String>> getAuthUrlByUserId(Long userId) {
	Result<Set<String>> result = new Result<Set<String>>();
	UserRoleRelationExample example = new UserRoleRelationExample();
	example.createCriteria().andUserIdEqualTo(userId);

	Set<String> urlSet = new HashSet<String>();
	try {
	    List<UserRoleRelation> list = userRoleRelationDAO.selectByExample(example);
	    if (list != null) {
		for (UserRoleRelation userRoleRelation : list) {
		    List<OperationInfo> operationInfoList = operationInfoDAO.selectOperationListByRole(
			    userRoleRelation.getRoleId(), Constants.OperationInfo.TYPE_URL);
		    for (OperationInfo operationInfo : operationInfoList) {
			if (StringUtils.isNotEmpty(operationInfo.getOperationUrl())) {
			    urlSet.add(operationInfo.getOperationUrl());
			}
		    }
		}
	    }
	    result.setSuccess(true);
	    result.setModule(urlSet);
	} catch (SQLException e) {
	    result.setResultMsg("getAuthUrlByUserId error msg : " + e.getMessage());
	    logger.error("getAuthUrlByUserId error userId : " + userId, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Set<Integer>> getAuthBizIdByUserId(Long userId) {
	Result<Set<Integer>> result = new Result<Set<Integer>>();
	UserRoleRelationExample example = new UserRoleRelationExample();
	example.createCriteria().andUserIdEqualTo(userId);

	Set<Integer> bizSet = new HashSet<Integer>();
	try {
	    List<UserRoleRelation> list = userRoleRelationDAO.selectByExample(example);
	    if (list != null) {
		for (UserRoleRelation userRoleRelation : list) {
		    List<OperationInfo> operationInfoList = operationInfoDAO.selectOperationListByRole(
			    userRoleRelation.getRoleId(), Constants.OperationInfo.TYPE_BIZ);
		    for (OperationInfo operationInfo : operationInfoList) {
			if (operationInfo.getBizId() != null) {
			    bizSet.add(operationInfo.getBizId());
			}
		    }
		}
	    }
	    result.setSuccess(true);
	    result.setModule(bizSet);
	} catch (SQLException e) {
	    result.setResultMsg("getAuthBizIdByUserId error msg : " + e.getMessage());
	    logger.error("getAuthBizIdByUserId error userId : " + userId, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Set<Integer>> getAuthIdByRoleId(Integer roleId) {
	Result<Set<Integer>> result = new Result<Set<Integer>>();
	OperationRoleRelationExample example = new OperationRoleRelationExample();
	example.createCriteria().andRoleIdEqualTo(roleId);

	Set<Integer> operationSet = new HashSet<Integer>();
	try {
	    List<OperationRoleRelation> list = operationRoleRelationDAO.selectByExample(example);
	    if (list != null) {
		for (OperationRoleRelation operationRoleRelation : list) {
		    operationSet.add(operationRoleRelation.getOperationId());
		}
	    }
	    result.setSuccess(true);
	    result.setModule(operationSet);
	} catch (SQLException e) {
	    result.setResultMsg("getAuthIdByRoleId error msg : " + e.getMessage());
	    logger.error("getAuthIdByRoleId error roleId : " + roleId, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Set<Integer>> getAuthIdByUserId(Long userId) {
	Result<Set<Integer>> result = new Result<Set<Integer>>();
	UserRoleRelationExample example = new UserRoleRelationExample();
	example.createCriteria().andUserIdEqualTo(userId);

	Set<Integer> roleSet = new HashSet<Integer>();
	try {
	    List<UserRoleRelation> list = userRoleRelationDAO.selectByExample(example);
	    if (list != null) {
		for (UserRoleRelation userRoleRelation : list) {
		    roleSet.add(userRoleRelation.getRoleId());
		}
	    }
	    result.setSuccess(true);
	    result.setModule(roleSet);
	} catch (SQLException e) {
	    result.setResultMsg("getAuthIdByUserId error msg : " + e.getMessage());
	    logger.error("getAuthIdByUserId error userId : " + userId, e);
	}
	return result;
    }

}
