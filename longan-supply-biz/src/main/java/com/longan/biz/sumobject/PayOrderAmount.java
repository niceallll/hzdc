package com.longan.biz.sumobject;

import com.longan.biz.utils.BigDecimalUtils;

public class PayOrderAmount {
    private String payAmount;
    private Long pay;

    public String getPayAmount() {
	if (pay == null)
	    return "0";
	else {
	    payAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(pay + ""));
	    return payAmount;
	}
    }

    public void setPayAmount(String payAmount) {
	this.payAmount = payAmount;
    }

    public Long getPay() {
	return pay;
    }

    public void setPay(Long pay) {
	this.pay = pay;
    }
}
