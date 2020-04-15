package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderExample;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.domain.Result;

public interface BizOrderService {
    public Result<BizOrder> bizOrderPreCheck(BizOrder bizOrder);

    public Result<BizOrder> createBizOrder(BizOrder bizOrder);

    public Result<Boolean> updateBizOrder(BizOrder bizOrder);

    public Result<Boolean> updateBizOrder(BizOrder bizOrder, BizOrderExample bizOrderExample);

    public Result<BizOrder> getBizOrderById(Long bizOrderId);

    public Result<BizOrder> getBizOrderByPayOrder(Long payOrderId);

    public Result<BizOrder> getBizOrderDownstreamSerialno(String downstreamSerialno, Long userId);

    public Result<BizOrder> getBizOrderUpstreamSerialno(String upstreamSerialno, Long userId);

    public Result<List<BizOrder>> queryBizOrderPage(BizOrderQuery bizOrderQuery);

    public Result<Integer> getCountInExport(BizOrderQuery bizOrderQuery);

    public Result<List<BizOrder>> queryBizOrderExport(BizOrderQuery bizOrderQuery);

    // 翻页查询
    public Result<List<BizOrder>> getBizOrderExport(BizOrderQuery bizOrderQuery);

    public Result<Boolean> checkDownstreamSerialno(BizOrder bizOrder);

    // 确认订单 如果有处理中额度限制 ，计数减少
    public Result<Boolean> confirmBizOrder(BizOrder bizOrder);

    // 取消订单， 有库存则返还库存， 并且退款给下游 ，如果有处理中额度限制 ，计数减少
    public Result<Boolean> cancelBizOrder(BizOrder bizOrder);

    // 设置订单未确认
    public Result<Boolean> unConfirmBizOrder(BizOrder bizOrder);

    // 设置订单处理中，计数增加
    public Result<Boolean> chargingBizOrderAndCounting(BizOrder bizOrder);

    public Result<Boolean> manualLockBizOrder(BizOrder bizOrder);

    public Result<Boolean> manualUnLockBizOrder(BizOrder bizOrder);

    public Result<Boolean> manualConfirmBizOrder(BizOrder bizOrder);

    public Result<Boolean> manualCancelBizOrder(BizOrder bizOrder);

    public Result<Boolean> manualUnConfirmBizOrder(BizOrder bizOrder);

    public Result<Integer> getCountInCharging(Integer bizId);

    public Result<Integer> getCountInChargingByTraderId(Long traderId);

    public Result<Boolean> checkUidCount(BizOrder bizOrder);

    public Result<BizOrder> robotLockBizOrder(Long upstreamId, String posId, String pcId, Long lockOperId);

    public  void  updataRemaks(BizOrder bizOrder);
    public Result<BizOrder> selectRemaks(Long bizOderId);
}
