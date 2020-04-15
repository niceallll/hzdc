package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.TraderInfoService;
import com.longan.biz.dao.TraderInfoDAO;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.TraderInfoExample;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.Constants;

public class TraderInfoServiceImpl extends BaseService implements TraderInfoService {
    @Resource
    private TraderInfoDAO traderInfoDAO;

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<TraderInfo>> queryTraderInfoList() {
	Result<List<TraderInfo>> result = new Result<List<TraderInfo>>();
	TraderInfoExample example = new TraderInfoExample();
	example.createCriteria().andTraderTypeEqualTo(Constants.TraderInfo.TRADER_TYPE_UPSTREAM);
	List<TraderInfo> list = new ArrayList<TraderInfo>();
	try {
	    list = (List<TraderInfo>) traderInfoDAO.selectByExample(example);
	} catch (SQLException e) {
	    logger.error("queryTraderInfoList error", e);
	    result.setResultMsg("queryTraderInfoList error");
	    return result;
	}

	result.setSuccess(true);
	result.setModule(list);
	return result;
    }

    @Override
    public Result<TraderInfo> getTraderInfo(Long traderId) {
	Result<TraderInfo> result = new Result<TraderInfo>();
	try {
	    TraderInfo traderInfo = traderInfoDAO.selectByPrimaryKey(traderId);
	    if (traderInfo != null) {
		result.setSuccess(true);
		result.setModule(traderInfo);
	    } else {
		result.setResultMsg("不存在该商户信息 ");
	    }
	} catch (Exception ex) {
	    result.setResultMsg("商户信息查询异常  msg : " + ex.getMessage());
	    logger.error("getTraderInfo error traderId = " + traderId, ex);
	}
	return result;
    }

    @Override
    public Result<TraderInfo> getTraderInfoByUserId(Long userId) {
	Result<TraderInfo> result = new Result<TraderInfo>();
	try {
	    TraderInfo traderInfo = traderInfoDAO.selectByUserId(userId);
	    if (traderInfo != null) {
		result.setSuccess(true);
		result.setModule(traderInfo);
	    } else {
		result.setResultMsg("不存在该商户信息 ");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("商户信息查询异常  msg : " + e.getMessage());
	    logger.error("getTraderInfo error  userId = " + userId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateTraderInfo(TraderInfo traderInfo) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    int row = traderInfoDAO.updateByPrimaryKeySelective(traderInfo);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("更新商户信息失败    msg: " + e.getMessage());
	    logger.error("updateTraderInfo error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> createTraderInfo(TraderInfo traderInfo) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	if (traderInfo == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    traderInfoDAO.insert(traderInfo);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (SQLException e) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_BIZORDER);
	    result.setResultMsg("创建商户信息失败  msg: " + e.getMessage());
	    logger.error("createTraderInfo error", e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean repeatSmsExtend(String smsExtend) {
	if (StringUtils.isBlank(smsExtend)) {
	    return false;
	}

	try {
	    TraderInfoExample example = new TraderInfoExample();
	    example.createCriteria().andSmsExtendEqualTo(smsExtend);
	    List<TraderInfo> list = (List<TraderInfo>) traderInfoDAO.selectByExample(example);
	    if (list == null || list.size() == 0) {
		return false;
	    }
	} catch (SQLException e) {
	}
	return true;
    }
}
