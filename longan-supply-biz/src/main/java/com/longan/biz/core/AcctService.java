package com.longan.biz.core;

import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.Item;
import com.longan.biz.domain.Result;

public interface AcctService {
    public Result<AcctInfo> getAcctInfo(Long acctId);

    public Result<Boolean> checkAcct(AcctInfo acctInfo);

    public Result<Boolean> checkBalanceAcct(AcctInfo acctInfo, Item item);

    public Result<Boolean> deductMoney(Long payOrderId);

    public Result<Boolean> addMoney(ChargeOrder chargeOrder);

    public Result<Boolean> refundMoney(Long refundOrderId);

    public Result<Long> addAcct(AcctInfo acctInfo);

    public Result<Boolean> updateAcct(AcctInfo acctInfo);

    public String getVerificationCode(AcctInfo acctInfo);

    public Result<Boolean> subMoney(CashOrder cashOrder);
}
