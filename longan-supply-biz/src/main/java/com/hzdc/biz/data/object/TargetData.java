package com.hzdc.biz.data.object;

import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.SupplyOrder;

public class TargetData {
    private BizOrder bizOrder;
    private SupplyOrder supplyOrder;
    private PayOrder payOrder;
    private AcctLog acctLog;

    public BizOrder getBizOrder() {
	return bizOrder;
    }

    public void setBizOrder(BizOrder bizOrder) {
	this.bizOrder = bizOrder;
    }

    public SupplyOrder getSupplyOrder() {
	return supplyOrder;
    }

    public void setSupplyOrder(SupplyOrder supplyOrder) {
	this.supplyOrder = supplyOrder;
    }

    public PayOrder getPayOrder() {
	return payOrder;
    }

    public void setPayOrder(PayOrder payOrder) {
	this.payOrder = payOrder;
    }

    public AcctLog getAcctLog() {
	return acctLog;
    }

    public void setAcctLog(AcctLog acctLog) {
	this.acctLog = acctLog;
    }
}
