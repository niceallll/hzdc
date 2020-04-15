package com.longan.biz.sumobject;

import com.longan.biz.utils.BigDecimalUtils;

public class ChargeOrderAmount {
    private String chargeAmount;
    private Long charge;

    public String getChargeAmount() {
	if (charge == null)
	    return "0";
	else {
	    chargeAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(charge + ""));
	    return chargeAmount;
	}
    }

    public void setChargeAmount(String chargeAmount) {
	this.chargeAmount = chargeAmount;
    }

    public Long getCharge() {
	return charge;
    }

    public void setCharge(Long charge) {
	this.charge = charge;
    }
}
