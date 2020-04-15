package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.OperationInfoService;
import com.longan.biz.dao.OperationInfoDAO;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationInfoExample;
import com.longan.biz.dataobject.OperationInfoQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class OperationInfoServiceImpl extends BaseService implements OperationInfoService {
    @Resource
    private OperationInfoDAO operationInfoDAO;

    @Override
    public Result<List<OperationInfo>> queryOperationInfoList(OperationInfoQuery operationInfoQuery) {
	Result<List<OperationInfo>> result = new Result<List<OperationInfo>>();
	try {
	    List<OperationInfo> queryResult = operationInfoDAO.queryOperationInfoByPage(operationInfoQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("操作信息列表查询失败    msg: " + e.getMessage());
	    logger.error("queryOperationInfoQueryList error ", e);
	}

	return result;
    }

    @Override
    public Result<OperationInfo> getOperationInfoById(Integer id) {
	Result<OperationInfo> result = new Result<OperationInfo>();
	try {
	    OperationInfo operationInfo = operationInfoDAO.selectByPrimaryKey(id);
	    if (operationInfo != null) {
		result.setSuccess(true);
		result.setModule(operationInfo);
	    } else {
		result.setResultMsg("不存在该操作信息 ");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("操作信息查询异常  msg : " + e.getMessage());
	    logger.error("getOperationInfo error  operationInfoId = " + id, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Boolean> updateOperationInfo(OperationInfo operationInfo) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    if (!StringUtils.isEmpty(operationInfo.getOperationName())) {
		OperationInfoExample example = new OperationInfoExample();
		example.createCriteria().andOperationNameEqualTo(operationInfo.getOperationName());
		List<OperationInfo> list = (List<OperationInfo>) operationInfoDAO.selectByExample(example);
		for (OperationInfo operationInfo2 : list) {
		    if (!operationInfo2.getId().equals(operationInfo.getId())) {
			result.setResultMsg("该操作名称已存在");
			return result;
		    }
		}
	    }
	    if (!StringUtils.isEmpty(operationInfo.getOperationUrl())) {
		OperationInfoExample example = new OperationInfoExample();
		example.createCriteria().andOperationUrlEqualTo(operationInfo.getOperationUrl());
		List<OperationInfo> list = (List<OperationInfo>) operationInfoDAO.selectByExample(example);
		for (OperationInfo operationInfo2 : list) {
		    if (!operationInfo2.getId().equals(operationInfo.getId())) {
			result.setResultMsg("该操url称已存在");
			return result;
		    }
		}
	    }
	    int row = operationInfoDAO.updateByPrimaryKeySelective(operationInfo);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("更新操作信息失败    msg: " + e.getMessage());
	    logger.error("updateOperationInfo error ", e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Boolean> createOperationInfo(OperationInfo operationInfo) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    if (!StringUtils.isEmpty(operationInfo.getOperationName())) {
		OperationInfoExample example = new OperationInfoExample();
		example.createCriteria().andOperationNameEqualTo(operationInfo.getOperationName());
		List<OperationInfo> list = (List<OperationInfo>) operationInfoDAO.selectByExample(example);
		if (list.size() > 0) {
		    result.setResultMsg("该操作名称已存在");
		    return result;
		}
	    }
	    if (!StringUtils.isEmpty(operationInfo.getOperationUrl())) {
		OperationInfoExample example = new OperationInfoExample();
		example.createCriteria().andOperationUrlEqualTo(operationInfo.getOperationUrl());
		List<OperationInfo> list = (List<OperationInfo>) operationInfoDAO.selectByExample(example);
		if (list.size() > 0) {
		    result.setResultMsg("该操url称已存在");
		    return result;
		}
	    }
	    operationInfo.setStatus(Constants.OperationInfo.STATUS_NORMAL);
	    Integer id = operationInfoDAO.insert(operationInfo);
	    if (id == null) {
		result.setResultMsg("新增操作信息失败");
		return result;
	    }
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (Exception e) {
	    result.setResultMsg("新增操作信息失败 ");
	    logger.error("createOperationInfo error OperationInfoName : " + operationInfo.getOperationName(), e);
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<OperationInfo>> queryOperationInfoByType(Integer type) {
	Result<List<OperationInfo>> result = new Result<List<OperationInfo>>();
	try {
	    List<OperationInfo> selectResult = operationInfoDAO.queryOperationListByType(type);
	    result.setSuccess(true);
	    result.setModule(selectResult);
	} catch (SQLException e) {
	    result.setResultMsg("操作信息列表查询失败    msg: " + e.getMessage());
	    logger.error("queryOperationInfoByType error ", e);
	}

	return result;
    }

    @Override
    public Result<List<OperationInfo>> queryAllOperationInfo() {
	Result<List<OperationInfo>> result = new Result<List<OperationInfo>>();
	try {
	    List<OperationInfo> queryResult = operationInfoDAO.queryAllOperationList();
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("操作信息列表查询失败    msg: " + e.getMessage());
	    logger.error("queryAllOperationInfo error ", e);
	}

	return result;
    }
}
