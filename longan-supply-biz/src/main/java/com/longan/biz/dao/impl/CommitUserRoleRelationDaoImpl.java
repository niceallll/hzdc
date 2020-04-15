package com.longan.biz.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.longan.biz.dao.CommitUserRoleRelationDao;
import com.longan.biz.dao.UserRoleRelationDAO;
import com.longan.biz.dataobject.UserRoleRelation;
import com.longan.biz.domain.Result;

public class CommitUserRoleRelationDaoImpl implements CommitUserRoleRelationDao {
    Logger logger = LoggerFactory.getLogger(CommitUserRoleRelationDaoImpl.class);
    @Resource
    private UserRoleRelationDAO userRoleRelationDAO;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> EditUserRoleRelation(List<UserRoleRelation> userRoleRelationList, Long userId) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	userRoleRelationDAO.deleteByUserId(userId);
	Boolean flag = userRoleRelationDAO.batchInsert(userRoleRelationList);
	if (flag) {
	    result.setSuccess(true);
	    result.setModule(true);
	} else {
	    logger.error("EditUserRoleRelation batchInsert  faild userId:" + userId);
	    result.setResultMsg("操作失败");
	}
	return result;
    }

}
