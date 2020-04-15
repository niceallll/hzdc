package com.longan.biz.dao;

import java.util.List;

import com.longan.biz.dataobject.OperationRoleRelation;
import com.longan.biz.domain.Result;

public interface CommitOperationRoleRelationDao {
	Result<Boolean> EditOperationRoleRelation(List<OperationRoleRelation> operationRoleRelationList,
			Integer roleId) throws Exception;
}
