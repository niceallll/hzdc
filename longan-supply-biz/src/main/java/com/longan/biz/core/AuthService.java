package com.longan.biz.core;

import java.util.Set;

import com.longan.biz.domain.Result;

public interface AuthService {
    public Result<Set<String>> getAuthCatalogByUserId(Long userId);

    public Result<Set<String>> getAuthUrlByUserId(Long userId);

    public Result<Set<Integer>> getAuthBizIdByUserId(Long userId);

    public Result<Set<Integer>> getAuthIdByRoleId(Integer roleId);

    public Result<Set<Integer>> getAuthIdByUserId(Long userId);
}
