package com.longan.client.remote.service;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.domain.Result;

public interface CallMessageService {
    public void sendSmsNote(BizOrder bizOrder);

    public void sendSmsOnly(String mobile, String text);

    public Result<Boolean> sendSmsNotify(String mobile, String text);
}
