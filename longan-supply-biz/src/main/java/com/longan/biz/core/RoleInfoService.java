package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.RoleInfo;
import com.longan.biz.dataobject.RoleInfoQuery;
import com.longan.biz.domain.Result;

public interface RoleInfoService {
    public Result<List<RoleInfo>> queryRoleInfoList(RoleInfoQuery roleInfoQuery);

    public Result<RoleInfo> getRoleInfoById(Integer id);

    public Result<Boolean> updateRoleInfo(RoleInfo roleInfo);

    public Result<Boolean> createRoleInfo(RoleInfo roleInfo);

    public Result<List<RoleInfo>> queryAllRoleInfo();
}
