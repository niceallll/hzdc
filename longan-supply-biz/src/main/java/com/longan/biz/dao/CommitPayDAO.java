package com.longan.biz.dao;

import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;

public interface CommitPayDAO {
    Result<Boolean> deductMoney(Long payOrderId) throws Exception;

    Result<Boolean> addMoney(ChargeOrder chargeOrder) throws Exception;

    Result<Boolean> refundMoney(Long refundOrderId) throws Exception;

    Result<Boolean> adjustPayOrder(Long refundOrderId, OperationVO operationVO) throws Exception;

    Result<Boolean> subMoney(CashOrder cashOrder) throws Exception;
}
