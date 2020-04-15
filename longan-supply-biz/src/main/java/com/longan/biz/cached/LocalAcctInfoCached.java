package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.dao.AcctInfoDAO;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AcctInfoExample;
import com.longan.biz.utils.Constants;

public class LocalAcctInfoCached extends LocalCached<Long, AcctInfo> {
    @Resource
    private AcctInfoDAO acctInfoDAO;

    @Override
    public void init() {
	super.init(60l, 60l);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<Long, AcctInfo> cached) {
	AcctInfoExample acctInfoExample = new AcctInfoExample();
	acctInfoExample.createCriteria().andStatusEqualTo(Constants.AcctInfo.STATUS_NORMAL);
	try {
	    List<AcctInfo> acctInfoList = (List<AcctInfo>) acctInfoDAO.selectByExample(acctInfoExample);
	    for (AcctInfo acctInfo : acctInfoList) {
		cached.put(acctInfo.getId(), acctInfo);
	    }
	} catch (SQLException e) {
	    logger.error("reload AcctInfo error", e);
	}
    }
}
