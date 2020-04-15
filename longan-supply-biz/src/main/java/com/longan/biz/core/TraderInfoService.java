package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.domain.Result;

public interface TraderInfoService {
    public Result<List<TraderInfo>> queryTraderInfoList();

    public Result<TraderInfo> getTraderInfo(Long traderId);

    public Result<TraderInfo> getTraderInfoByUserId(Long userId);

    public Result<Boolean> updateTraderInfo(TraderInfo traderInfo);

    public Result<Boolean> createTraderInfo(TraderInfo traderInfo) throws Exception;

    public Boolean repeatSmsExtend(String smsExtend);
}
