package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoQuery;
import com.longan.biz.domain.Result;

public interface UserService {
    public Result<Boolean> checkUserInfo(UserInfo userInfo);

    public Result<UserInfo> getUserInfo(Long userId);

    public Result<UserInfo> getUserInfo(String loginId);

    public Result<List<UserInfo>> queryUserInfoList(UserInfoQuery userInfoQuery);

    public Result<Boolean> createUserInfo(UserInfo userInfo, TraderInfo traderInfo) throws Exception;

    public Result<Boolean> updateUserInfo(UserInfo userInfo);

    public Result<UserInfo> login(String userName, String pwd);

    public Result<List<UserInfo>> queryUserInfoAdmin();

    public Result<List<UserInfo>> queryUserInfoPartner();

    public Result<List<UserInfo>> queryUserInfoDownStream();

    public Result<List<UserInfo>> queryUserInfoUpStream();

    public Result<UserInfo> getUserInfoById(Long id);

    public Result<Boolean> modifyPwd(Long userId, String oldPwd, String newPwd);

    public Result<List<UserInfo>> queryDownStreamInfoList(UserInfoQuery userInfoQuery);

    public Result<UserInfo> getDownStreamById(Long userId);

    public Result<Boolean> updateUser(UserInfo userInfo, TraderInfo traderInfo);
}
