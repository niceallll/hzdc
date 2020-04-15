package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.dao.TraderInfoDAO;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.TraderInfoExample;

public class LocalTraderInfoCached extends LocalCached<Long, TraderInfo> {
    @Resource
    private TraderInfoDAO traderInfoDAO;

    @Override
    public void init() {
	super.init(60l, 60l);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<Long, TraderInfo> cached) {
	TraderInfoExample traderInfoExample = new TraderInfoExample();
	try {
	    List<TraderInfo> traderInfoList = (List<TraderInfo>) traderInfoDAO.selectByExample(traderInfoExample);
	    for (TraderInfo traderInfo : traderInfoList) {
		cached.put(traderInfo.getUserId(), traderInfo);
	    }
	} catch (SQLException e) {
	    logger.error("reload traderInfo error", e);
	}
    }
}