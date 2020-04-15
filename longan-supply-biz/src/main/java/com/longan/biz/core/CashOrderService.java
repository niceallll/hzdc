package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.CashOrderQuery;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.CashOrderAmount;

public interface CashOrderService {
    public Result<List<CashOrder>> queryCashOrder(CashOrderQuery cashOrderQuery);

    public Result<CashOrderAmount> queryCashOrderSum(CashOrderQuery cashOrderQuery);

    public Result<CashOrder> createCashOrder(OperationVO operationVO, CashOrder cashOrder);

    public Result<CashOrder> getCashOrder(Long id);

    public Result<Boolean> verifyCashOrder(OperationVO operationVO, Long cashOrderId);

    public Result<Boolean> cancelCashOrder(OperationVO operationVO, Long cashOrderId);
}
