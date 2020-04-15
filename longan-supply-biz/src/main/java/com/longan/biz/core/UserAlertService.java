package com.longan.biz.core;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.domain.Result;

public interface UserAlertService {
    public String getRequestData(Long userId, String key, String type);

    public Result<Boolean> alertRequest(Long userId, String key, String type);

    public Result<Boolean> bizOrderRequest(BizOrder bizOrder);

    public Result<Boolean> supplyOrderRequest(SupplyOrder supplyOrder);
}
