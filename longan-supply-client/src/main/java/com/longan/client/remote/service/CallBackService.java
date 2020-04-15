package com.longan.client.remote.service;

import java.util.List;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.SmsNotify;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.domain.Result;

public interface CallBackService {
    public Result<Boolean> callBackAsync(BizOrder bizOrder);

    public Result<Boolean> callBackCombine(BizOrder bizOrder);

    public Result<Boolean> callBackAsync(SmsOrder smsOrder, List<SmsSupply> supplyList);

    public Result<Boolean> callNotifyAsync(Long userId, List<SmsNotify> notifyList);
}
