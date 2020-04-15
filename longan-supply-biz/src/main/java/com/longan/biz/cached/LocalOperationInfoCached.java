package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.dao.OperationInfoDAO;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.OperationInfoExample;

public class LocalOperationInfoCached extends LocalCached<String, OperationInfo> {
    @Resource
    private OperationInfoDAO operationInfoDAO;

    @Override
    public void init() {
	super.init(600L, 600L);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<String, OperationInfo> cached) {
	OperationInfoExample operationInfoExample = new OperationInfoExample();
	try {
	    List<OperationInfo> operationInfoList = (List<OperationInfo>) operationInfoDAO.selectByExample(operationInfoExample);
	    for (OperationInfo operationInfo : operationInfoList) {
		if (operationInfo.getOperationUrl() != null) {
		    cached.put(operationInfo.getOperationUrl(), operationInfo);
		}
	    }
	} catch (SQLException e) {
	    logger.error("reload OperationInfo error", e);
	}
    }
}
