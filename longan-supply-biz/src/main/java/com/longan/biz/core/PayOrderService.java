package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.PayOrderAmount;

public interface PayOrderService {
    public Result<PayOrder> createPayOrder(BizOrder bizOrder);

    public Result<Boolean> updatePayOrder(PayOrder payOrder);

    public Result<PayOrder> getPayOrder(Long payOrderId);

    public Result<Boolean> updatePayOrderWhenException(Long payOrderId, String errorMsg);

    public Result<List<PayOrder>> queryPayOrder(PayOrderQuery payOrderQuery);

    public Result<PayOrderAmount> queryPayOrderSum(PayOrderQuery payOrderQuery);
}
