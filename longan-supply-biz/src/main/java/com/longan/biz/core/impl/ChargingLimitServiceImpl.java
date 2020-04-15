package com.longan.biz.core.impl;

import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.cached.MemcachedService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.ChargingLimitService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.core.TraderInfoService;
import com.longan.biz.dao.CommitLimitDAO;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.CachedUtils;

public class ChargingLimitServiceImpl extends BaseService implements ChargingLimitService {
    @Resource
    private MemcachedService memcachedService;

    @Resource
    private LocalCachedService localCachedService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private TraderInfoService traderInfoService;

    @Resource
    private CommitLimitDAO commitLimitDAO;

    private static final Integer CHARGING_LIMIT_EXP = 0;

    @Override
    public Result<Boolean> hasReachLimit(Long supplyTraderId, Integer price) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	// 先内存、然后数据库
	TraderInfo traderInfo = localCachedService.getTraderInfo(supplyTraderId);
	if (traderInfo == null || traderInfo.getChargingLimit() == null) {
	    // 为了让业务走下去，traderInfo == null 也返回true
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}

	Result<TraderInfo> traderInfoResult = traderInfoService.getTraderInfoByUserId(supplyTraderId);
	traderInfo = traderInfoResult.getModule();
	if (traderInfo == null || traderInfo.getChargingLimit() == null) {
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}

	Long maxLimit = traderInfo.getChargingLimit();
	if (price >= maxLimit) {
	    logger.warn("supply Trader id: " + supplyTraderId + " has reached limit . maxLimit : " + maxLimit);
	    result.setResultMsg("hasReachLimit");
	    return result;
	} else {
	    logger.warn("supply Trader id: " + supplyTraderId + " check charging amount : " + maxLimit);
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> hasReachLimit(Long supplyTraderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	TraderInfo traderInfo = localCachedService.getTraderInfo(supplyTraderId);
	if (traderInfo == null || traderInfo.getChargingLimit() == null) {
	    // 为了让业务走下去，traderInfo == null 也返回true
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}

	Long maxLimit = traderInfo.getChargingLimit();
	Long count = memcachedService.getLongValue(CachedUtils.getCharingLimitKey(supplyTraderId));
	if (count == null) {
	    // 查询失败 或者 没有值也返回true
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}
	if (count >= maxLimit) {
	    logger.warn("supply Trader id: " + supplyTraderId + " has reached limit . count : " + count);
	    result.setResultMsg("hasReachLimit");
	    return result;
	} else {
	    logger.warn("supply Trader id: " + supplyTraderId + " check charging count : " + count);
	}

	// 后面可以加上 当日数量 等逻辑
	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> incLimit(Long supplyTraderId, Integer price) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	// 先内存判段、然后数据库
	TraderInfo traderInfo = localCachedService.getTraderInfo(supplyTraderId);
	if (traderInfo == null || traderInfo.getChargingLimit() == null) {
	    // 为了让业务走下去，traderInfo == null 也返回true
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}

	try {
	    result = commitLimitDAO.addLimit(supplyTraderId, price);
	} catch (Exception ex) {
	    result.setResultMsg("限额增加失败  " + " msg :" + ex.getMessage());
	    logger.error("incLimit error supplyTraderId = " + supplyTraderId, ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> incCount(Long supplyTraderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	TraderInfo traderInfo = localCachedService.getTraderInfo(supplyTraderId);
	if (traderInfo == null || traderInfo.getChargingLimit() == null) {
	    // 为了让业务走下去，traderInfo == null 也返回true
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}

	Long count = memcachedService.inc(CachedUtils.getCharingLimitKey(supplyTraderId), 1);
	if (count == null) {
	    logger.error("supply Trader id: " + supplyTraderId + " inc count error");
	    result.setResultMsg("supply Trader id: " + supplyTraderId + " inc count error");
	    return result;
	}

	logger.warn("supply Trader id: " + supplyTraderId + " charging  inc count : " + count);

	// 后面可以加上 增加当日数量 等逻辑
	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> decLimit(Long supplyTraderId, Integer price) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	// 先内存判段、然后数据库
	TraderInfo traderInfo = localCachedService.getTraderInfo(supplyTraderId);
	if (traderInfo == null || traderInfo.getChargingLimit() == null) {
	    // 为了让业务走下去，traderInfo == null 也返回true
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}

	try {
	    result = commitLimitDAO.deductLimit(supplyTraderId, price);
	} catch (Exception ex) {
	    result.setResultMsg("限额减少失败  " + " msg :" + ex.getMessage());
	    logger.error("decLimit error supplyTraderId = " + supplyTraderId, ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> decCount(Long supplyTraderId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	TraderInfo traderInfo = localCachedService.getTraderInfo(supplyTraderId);
	if (traderInfo == null || traderInfo.getChargingLimit() == null) {
	    // 为了让业务走下去，traderInfo == null 也返回true
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}
	Long count = memcachedService.dec(CachedUtils.getCharingLimitKey(supplyTraderId), 1);

	if (count == null) {
	    logger.error("supply Trader id: " + supplyTraderId + " dec count error");
	    result.setResultMsg("supply Trader id: " + supplyTraderId + " dec count error");
	    return result;
	}

	logger.warn("supply Trader id: " + supplyTraderId + " charging  dec count : " + count);

	// 后面可以加上 减少当日数量 等逻辑
	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Long> getCount(Long supplyTraderId) {
	Result<Long> result = new Result<Long>();
	Long count = memcachedService.getLongValue(CachedUtils.getCharingLimitKey(supplyTraderId));
	if (count == null) {
	    logger.error("supply Trader id: " + supplyTraderId + "  count error");
	    result.setResultMsg("supply Trader id: " + supplyTraderId + "  count error");
	    return result;
	}
	result.setSuccess(true);
	result.setModule(count);
	return result;
    }

    @Override
    public Result<Boolean> initChargingCount() {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	List<TraderInfo> list = localCachedService.getTraderInfoList();
	if (list != null) {
	    for (TraderInfo traderInfo : list) {
		if (traderInfo.getChargingLimit() != null && traderInfo.getChargingLimit() != 0) {
		    if (traderInfo.getUserId() != null) {
			Result<Integer> queryResult = supplyOrderService.getCountInChargingByTraderId(traderInfo.getUserId());
			if (queryResult.isSuccess() && queryResult.getModule() != null) {
			    logger.warn("memcached set  getCharingLimitKey : "
				    + CachedUtils.getCharingLimitKey(traderInfo.getUserId()) + " charging count : "
				    + queryResult.getModule());
			    memcachedService.initCount(CachedUtils.getCharingLimitKey(traderInfo.getUserId()),
				    CHARGING_LIMIT_EXP, queryResult.getModule().longValue());
			}
		    }
		}
	    }
	    result.setSuccess(true);
	    result.setModule(true);
	}
	return result;
    }
}
