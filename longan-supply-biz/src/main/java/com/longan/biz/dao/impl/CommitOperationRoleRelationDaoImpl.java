package com.longan.biz.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.longan.biz.dao.CommitOperationRoleRelationDao;
import com.longan.biz.dao.OperationRoleRelationDAO;
import com.longan.biz.dataobject.OperationRoleRelation;
import com.longan.biz.domain.Result;

public class CommitOperationRoleRelationDaoImpl implements CommitOperationRoleRelationDao {
    Logger logger = LoggerFactory.getLogger(CommitOperationRoleRelationDaoImpl.class);
    @Resource
    private OperationRoleRelationDAO operationRoleRelationDAO;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> EditOperationRoleRelation(List<OperationRoleRelation> operationRoleRelationList, Integer roleId)
	    throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	operationRoleRelationDAO.deleteByRoleId(roleId);
	Boolean flag = operationRoleRelationDAO.batchInsert(operationRoleRelationList);
	if (flag) {
	    result.setSuccess(true);
	    result.setModule(true);
	} else {
	    logger.error("EditOperationRoleRelation batchInsert  faild roleId:" + roleId);
	    result.setResultMsg("操作失败");
	}
	return result;
    }
}
