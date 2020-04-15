package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.RefundOrderAmount;

public interface RefundOrderService {
    public Result<RefundOrder> createRefundOrder(PayOrder payOrder, OperationVO operationVO);

    public Result<RefundOrder> getRefundOrder(Long refundOrderId);

    public Result<List<RefundOrder>> queryRefundOrder(RefundOrderQuery refundOrderQuery);

    public Result<Integer> getCountInExport(RefundOrderQuery refundOrderQuery);

    public Result<List<RefundOrder>> queryRefundOrderExport(RefundOrderQuery refundOrderQuery);

    // 翻页查询
    public Result<List<RefundOrder>> getRefundOrderExport(RefundOrderQuery refundOrderQuery);

    public Result<Boolean> updateRefundOrderWhenException(Long refundOrderId, String errorMsg);

    public Result<RefundOrderAmount> queryRefundOrderSum(RefundOrderQuery refundOrderQuery);
}
