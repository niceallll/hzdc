package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoQuery;
import com.longan.biz.domain.Result;

public interface UserRoleRelationService {
    public Result<List<UserInfo>> queryUserRoleRelationList(UserInfoQuery userInfoQuery);
}
