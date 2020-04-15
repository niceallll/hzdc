package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.SmsNotify;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.SmsOrderCount;

public interface SmsOrderService {
    public Result<SmsOrder> smsOrderPreCheck(SmsOrder smsOrder);

    public Result<SmsOrderCount> querySmsOrderSum(SmsOrderQuery smsOrderQuery);

    public Result<SmsOrderCount> querySmsSupplySum(SmsSupplyQuery smsSupplyQuery);

    public Result<SmsOrder> createSmsOrder(SmsOrder smsOrder);

    public Result<Boolean> updateSmsOrder(SmsOrder smsOrder);

    public Result<SmsOrder> getSmsOrderById(Long smsOrderId);

    public Result<SmsOrder> getSmsOrderBySerialno(Long userId, String downstreamSerialno);

    public Result<SmsOrder> getSmsOrderByUpstreamSerialno(Long upstreamId, String upstreamSerialno);

    public Result<Integer> getCountInExport(SmsOrderQuery smsOrderQuery);

    public Result<List<SmsOrder>> querySmsOrderExport(SmsOrderQuery smsOrderQuery);

    // 翻页查询
    public Result<List<SmsOrder>> getSmsOrderExport(SmsOrderQuery smsOrderQuery);

    public Result<SmsSupply> createSmsSupply(SmsSupply smsSupply);

    public Result<Boolean> createSmsSupply(List<SmsSupply> supplyList);

    public Result<Boolean> updateSmsSupply(SmsSupply smsSupply);

    public Result<SmsSupply> getSmsSupplyById(Long smsSupplyId);

    public Result<SmsSupply> getSmsSupplyBySerialno(Long userId, String upstreamSerialno);

    public Result<List<SmsSupply>> getSmsSupplyList(Long smsOrderId);

    public Result<Integer> getCountInExport(SmsSupplyQuery smsSupplyQuery);

    public Result<List<SmsSupply>> querySmsSupplyExport(SmsSupplyQuery smsSupplyQuery);

    // 翻页查询
    public Result<List<SmsSupply>> getSmsSupplyExport(SmsSupplyQuery smsSupplyQuery);

    public Result<Boolean> updateOrder(SmsOrder smsOrder, List<SmsSupply> supplyList);

    // 取消订单、供货单
    public Result<Boolean> confirmSmsOrder(SmsOrder smsOrder);

    public Result<Boolean> confirmSmsSupply(SmsSupply smsSupply);

    public Result<Boolean> cancelSmsOrder(SmsOrder smsOrder);

    public Result<Boolean> cancelSmsSupply(List<SmsSupply> supplyList);

    public Result<Boolean> cancelSmsSupply(SmsSupply smsSupply);

    public Result<SmsNotify> createSmsNotify(SmsNotify smsNotify);

    public Result<SmsNotify> getSmsNotifyById(Long smsNotifyId);
}
