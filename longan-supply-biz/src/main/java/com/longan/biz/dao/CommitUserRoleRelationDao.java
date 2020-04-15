package com.longan.biz.dao;

import java.util.List;

import com.longan.biz.dataobject.UserRoleRelation;
import com.longan.biz.domain.Result;

public interface CommitUserRoleRelationDao {
    Result<Boolean> EditUserRoleRelation(List<UserRoleRelation> userRoleRelationList, Long userId) throws Exception;
}
