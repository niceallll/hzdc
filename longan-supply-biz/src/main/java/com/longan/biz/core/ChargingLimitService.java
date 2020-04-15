package com.longan.biz.core;

import com.longan.biz.domain.Result;

public interface ChargingLimitService {
    // 金额限制
    public Result<Boolean> hasReachLimit(Long supplyTraderId, Integer price);

    public Result<Boolean> decLimit(Long supplyTraderId, Integer price);

    public Result<Boolean> incLimit(Long supplyTraderId, Integer price);

    // 数量限制
    public Result<Boolean> hasReachLimit(Long supplyTraderId);

    public Result<Boolean> incCount(Long supplyTraderId);

    public Result<Boolean> decCount(Long supplyTraderId);

    public Result<Long> getCount(Long supplyTraderId);

    public Result<Boolean> initChargingCount();
}
