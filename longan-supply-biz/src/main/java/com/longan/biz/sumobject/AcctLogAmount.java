package com.longan.biz.sumobject;

import com.longan.biz.utils.BigDecimalUtils;

public class AcctLogAmount {
    private String iAmount;
    private Long iamt;
    private String oAmount;
    private Long oamt;
    private String balance;

    public String getiAmount() {
	if (iamt == null)
	    return "0";
	else {
	    iAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(iamt + ""));
	    return iAmount;
	}
    }

    public void setiAmount(String iAmount) {
	this.iAmount = iAmount;
    }

    public Long getIamt() {
	return iamt;
    }

    public void setIamt(Long iamt) {
	this.iamt = iamt;
    }

    public String getoAmount() {
	if (oamt == null)
	    return "0";
	else {
	    oAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(oamt + ""));
	    return oAmount;
	}
    }

    public void setoAmount(String oAmount) {
	this.oAmount = oAmount;
    }

    public Long getOamt() {
	return oamt;
    }

    public void setOamt(Long oamt) {
	this.oamt = oamt;
    }

    public String getBalance() {
	if (iamt == null || oamt == null)
	    return "0";
	else {
	    balance = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid((iamt - oamt) + ""));
	    return balance;
	}
    }

    public void setBalance(String balance) {
	this.balance = balance;
    }
}
