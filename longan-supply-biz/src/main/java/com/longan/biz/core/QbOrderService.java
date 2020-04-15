package com.longan.biz.core;

import com.longan.biz.dataobject.QbOrder;
import com.longan.biz.domain.Result;

public interface QbOrderService {
    public Result<QbOrder> createQbOrder(QbOrder qbOrder);

    public Result<QbOrder> getQbOrderBySerialNum(String serialNum);
}
