package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.dao.UserInfoDAO;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.dataobject.UserInfoExample;
import com.longan.biz.utils.Constants;

public class LocalUserInfoCached extends LocalCached<Long, UserInfo> {
    @Resource
    private UserInfoDAO userInfoDAO;

    @Override
    public void init() {
	super.init(600L, 600L);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<Long, UserInfo> cached) {
	UserInfoExample userInfoExample = new UserInfoExample();
	userInfoExample.createCriteria().andStatusEqualTo(Constants.UserInfo.STATUS_NORMAL);
	try {
	    List<UserInfo> userInfoList = (List<UserInfo>) userInfoDAO.selectByExample(userInfoExample);
	    for (UserInfo userInfo : userInfoList) {
		cached.put(userInfo.getId(), userInfo);
	    }
	} catch (SQLException e) {
	    logger.error("reload UserInfo error", e);
	}
    }
}
