package com.longan.biz.sumobject;

import com.longan.biz.utils.BigDecimalUtils;

public class RefundOrderAmount {
    private String refundAmount;
    private Long refund;

    public String getRefundAmount() {
	if (refund == null)
	    return "0";
	else {
	    refundAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(refund + ""));
	    return refundAmount;
	}
    }

    public void setRefundAmount(String refundAmount) {
	this.refundAmount = refundAmount;
    }

    public Long getRefund() {
	return refund;
    }

    public void setRefund(Long refund) {
	this.refund = refund;
    }
}
