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

public class LocalBizAuthCached extends LocalCached<Long, Set<Integer>> {
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
    void reloadFromDb(ConcurrentMap<Long, Set<Integer>> cached) {
	UserInfoExample userInfoExample = new UserInfoExample();
	userInfoExample.createCriteria().andStatusEqualTo(Constants.UserInfo.STATUS_NORMAL);
	try {
	    List<UserInfo> userInfoList = (List<UserInfo>) userInfoDAO.selectByExample(userInfoExample);
	    if (userInfoList != null) {
		for (UserInfo userInfo : userInfoList) {
		    Result<Set<Integer>> result = authService.getAuthBizIdByUserId(userInfo.getId());
		    if (result.isSuccess()) {
			cached.put(userInfo.getId(), result.getModule());
		    } else {
			logger.error("getAuthBizIdByUserId error userId : " + userInfo.getId() + " msg : "
				+ result.getResultMsg());
		    }
		}
	    }
	} catch (SQLException e) {
	    logger.error("LocalBizAuthCached reloadFromDb error ", e);
	}
    }
}
