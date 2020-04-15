package com.longan.biz.core;

import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.CashOrder;
import com.longan.biz.dataobject.ChargeOrder;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.domain.OperationVO;
import com.longan.biz.domain.Result;

public interface PayService {
    /**
     * 事务支付
     * 
     * @param bizOrder
     * @return
     */
    public Result<BizOrder> commitPay(BizOrder bizOrder);

    /**
     * 事务退款操作 operationVO设置为null，则表示系统自动退款
     * 
     * @param payOrderId
     * @param operationVO
     * @return
     */
    public Result<RefundOrder> commitRefund(Long payOrderId, OperationVO operationVO);

    /**
     * 事务充值
     * 
     * @param chargeOrderId
     * @return
     */
    public Result<Boolean> commitCharge(ChargeOrder chargeOrder);

    /**
     * 事务调账
     * 
     * @param payOrderId
     * @param operationVO
     * @return
     */
    public Result<Boolean> adjustPayOrder(Long payOrderId, OperationVO operationVO);

    public Result<Boolean> commitCash(CashOrder CashOrder);
}
