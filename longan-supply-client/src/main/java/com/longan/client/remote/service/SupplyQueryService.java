package com.longan.client.remote.service;

import java.util.List;

import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.domain.Result;
import com.longan.client.remote.domain.BalanceQueryInfo;
import com.longan.client.remote.domain.CardChargeInfo;
import com.longan.client.remote.domain.CardCheck;
import com.longan.client.remote.domain.ChargeInfo;
import com.longan.client.remote.domain.MobileCheck;

public interface SupplyQueryService {
    public Result<CardCheck> cardCheck(Long stockId);

    public Result<List<CardChargeInfo>> cardChargeInfoQuery(Long stockId);

    public Result<ChargeInfo> chargeInfoQuery(SupplyOrder supplyOrder);

    public Result<BalanceQueryInfo> balanceQuery(String userId);

    public Result<MobileCheck> mobileCheck(Long supplyTraderId, Long supplyOrderId, String mobile, Integer itemFacePrice);
}
