package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.core.AuthService;
import com.longan.biz.dao.UserInfoDAO;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoExample;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class LocalCatalogAuthCached extends LocalCached<Long, Set<String>> {
    @Resource
    private UserInfoDAO userInfoDAO;

    @Resource
    private AuthService authService;

    @Override
    public void init() {
	super.init(600L, 600L);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<Long, Set<String>> cached) {
	UserInfoExample userInfoExample = new UserInfoExample();
	userInfoExample.createCriteria().andStatusEqualTo(Constants.UserInfo.STATUS_NORMAL);
	try {
	    List<UserInfo> userInfoList = (List<UserInfo>) userInfoDAO.selectByExample(userInfoExample);
	    if (userInfoList != null) {
		for (UserInfo userInfo : userInfoList) {
		    if (!userInfo.isUpStreamUser()) {
			Result<Set<String>> result = authService.getAuthCatalogByUserId(userInfo.getId());
			if (result.isSuccess()) {
			    cached.put(userInfo.getId(), result.getModule());
			} else {
			    logger.error("getAuthCatalogByUserId error userId : " + userInfo.getId() + " msg : "
				    + result.getResultMsg());
			}
		    }
		}
	    }
	} catch (SQLException e) {
	    logger.error("LocalCatalogAuthCached reloadFromDb error ", e);
	}
    }
}
