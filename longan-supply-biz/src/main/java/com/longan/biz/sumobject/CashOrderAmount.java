package com.longan.biz.sumobject;

import com.longan.biz.utils.BigDecimalUtils;

public class CashOrderAmount {
    private String cashAmount;
    private Long cash;

    public String getCashAmount() {
	if (cash == null)
	    return "0";
	else {
	    cashAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(cash + ""));
	    return cashAmount;
	}
    }

    public void setCashAmount(String cashAmount) {
	this.cashAmount = cashAmount;
    }

    public Long getCash() {
	return cash;
    }

    public void setCash(Long cash) {
	this.cash = cash;
    }
}
