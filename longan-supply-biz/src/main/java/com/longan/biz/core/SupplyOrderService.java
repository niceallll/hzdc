package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.SupplyOrderAmount;

public interface SupplyOrderService {
    public Result<List<SupplyOrder>> querySupplyOrderPage(SupplyOrderQuery supplyOrderQuery);

    public Result<List<SupplyOrder>> querySupplyOrderList(SupplyOrderQuery supplyOrderQuery);

    public Result<SupplyOrderAmount> querySupplyOrderSum(SupplyOrderQuery supplyOrderQuery);

    public Result<SupplyOrder> createSupplyOrder(SupplyOrder supplyOrder);

    public Result<Boolean> createSupplyOrder(List<SupplyOrder> supplyList);

    public Result<Boolean> updateSupplyOrder(SupplyOrder supplyOrder);

    public Result<Boolean> updateSupplyOrder(List<SupplyOrder> supplyList);

    public Result<SupplyOrder> getSupplyOrder(Long supplyOrderId);

    // 确认供货 如果有处理中额度限制 ，计数减少
    public Result<Boolean> confirmSupplyOrder(SupplyOrder supplyOrder);

    // 取消订单， 有库存则返还库存， 并且退款给下游 ，如果有处理中额度限制 ，计数减少
    public Result<Boolean> cancelSupplyOrder(SupplyOrder supplyOrder);

    // 设置供货未确认
    public Result<Boolean> unConfirmSupplyOrder(SupplyOrder supplyOrder);

    // 设置供货处理中， 如果有处理中额度限制 则 额度减少
    public Result<Boolean> chargingSupplyOrderAndLimit(SupplyOrder supplyOrder);

    public Result<Boolean> chargingSupplyOrderAndLimit(Long supplyTraderId, Integer itemFacePrice, List<SupplyOrder> supplyList);

    public Result<SupplyOrder> getSupplyOrderById(Long supplyOrderId);

    public Result<Integer> getCountInChargingByTraderId(Long traderId);

    public Result<SupplyOrder> getSupplyOrderByIdAndTraderId(Long id, Long traderId);

    public Result<SupplyOrder> getSupplyOrderByUpstreamSerialno(String upstreamSerialno, Long traderId);

    public Result<SupplyOrder> getSupplyOrderByItemUid(String upstreamSerialno, String itemUid, Long traderId);

    public Result<SupplyOrder> getSupplyOrderByBizOrderId(Long bizOrderId);

    public Result<List<SupplyOrder>> querySupplyOrderByBizOrder(Long bizOrderId);

    public Result<Boolean> updateSupplyOrderCheckStatus(SupplyOrder supplyOrder, List<Integer> statusList);

    public Result<Integer> getCountInExport(SupplyOrderQuery supplyOrderQuery);

    public Result<List<SupplyOrder>> querySupplyOrderExport(SupplyOrderQuery supplyOrderQuery);

    // 翻页查询
    public Result<List<SupplyOrder>> getSupplyOrderExport(SupplyOrderQuery supplyOrderQuery);

    public Result<List<SupplyOrder>> querySaleOrderExport(SupplyOrderQuery supplyOrderQuery);

    public Boolean checkRepeatUid(SupplyOrder supplyOrder);

    public String getCombineOrderExtends(Long bizOrderId);
}
