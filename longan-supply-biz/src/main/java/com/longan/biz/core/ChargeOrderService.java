package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.ChargeOrderQuery;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.ChargeOrderAmount;

public interface ChargeOrderService {
    public Result<List<ChargeOrder>> queryChargeOrder(ChargeOrderQuery chargeOrderQuery);

    public Result<ChargeOrderAmount> queryChargeOrderSum(ChargeOrderQuery chargeOrderQuery);

    public Result<ChargeOrder> getChargeOrder(Long id);

    public Result<ChargeOrder> createChargeOrder(OperationVO operationVO, ChargeOrder chargeOrder);

    public Result<Boolean> verifyChargeOrder(OperationVO operationVO, Long chargeOrderId);

    public Result<Boolean> cancelChargeOrder(OperationVO operationVO, Long chargeOrderId);

    public Result<Boolean> updateChargeOrderWhenException(Long chargeOrderId, String errorMsg);
}
