package com.longan.client.remote.service;

import java.util.Map;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.domain.Result;

public interface SupplyForRemoteService {
    public Result<SupplyOrder> supply(BizOrder bizOrder);

    public Result<String> manualGet(String data);

    public Result<String> manualPost(String url, Map<String, String> map);
}
