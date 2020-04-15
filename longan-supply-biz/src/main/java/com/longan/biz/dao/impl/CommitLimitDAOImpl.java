package com.longan.biz.dao.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.longan.biz.dao.CommitLimitDAO;
import com.longan.biz.dao.TraderInfoDAO;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.domain.Result;

public class CommitLimitDAOImpl implements CommitLimitDAO {
    Logger logger = LoggerFactory.getLogger(CommitLimitDAOImpl.class);

    @Resource
    private TraderInfoDAO traderInfoDAO;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> addLimit(Long supplyTraderId, Integer price) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	// 获取账户,锁定该行
	TraderInfo traderInfo = traderInfoDAO.selectForUpdate(supplyTraderId);
	if (traderInfo == null) {
	    logger.error("获取账户失败  supplyTraderId :" + supplyTraderId);
	    result.setResultMsg("获取账户失败");
	    return result;
	}

	Long limit = traderInfo.getChargingLimit() + price;
	// 更新账户
	traderInfo.setChargingLimit(limit);
	traderInfoDAO.updateByPrimaryKeySelective(traderInfo);

	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Boolean> deductLimit(Long supplyTraderId, Integer price) throws Exception {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);

	// 获取账户,锁定该行
	TraderInfo traderInfo = traderInfoDAO.selectForUpdate(supplyTraderId);
	if (traderInfo == null) {
	    logger.error("获取账户失败  supplyTraderId :" + supplyTraderId);
	    result.setResultMsg("获取账户失败");
	    return result;
	}
	if (traderInfo.getChargingLimit() <= 0) {
	    logger.warn("限额不能为0或者负数  supplyTraderId : " + supplyTraderId);
	    throw new RuntimeException("限额不能为0或者负数");
	}

	Long limit = traderInfo.getChargingLimit() - price;
	if (limit < 0) {
	    logger.warn("限额不足  supplyTraderId : " + supplyTraderId);
	    // 抛出异常才会执行 rollback 释放掉上面的锁
	    throw new RuntimeException("限额不足");
	}

	// 更新账户
	traderInfo.setChargingLimit(limit);
	traderInfoDAO.updateByPrimaryKeySelective(traderInfo);

	result.setSuccess(true);
	result.setModule(true);
	return result;
    }
}
