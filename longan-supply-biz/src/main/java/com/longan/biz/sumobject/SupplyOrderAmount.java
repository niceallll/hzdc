package com.longan.biz.sumobject;

import com.longan.biz.utils.BigDecimalUtils;

public class SupplyOrderAmount {
    private String faceAmount;
    private Long face;
    private String saleAmount;
    private Long sale;
    private String dummyAmount;
    private Long dummy;
    private String costAmount;
    private Long cost;
    private String profitAmount;
    private Long fee;
    private String feeAmount;

    public String getFaceAmount() {
	if (face == null)
	    return "0";
	else {
	    faceAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(face + ""));
	    return faceAmount;
	}
    }

    public void setFaceAmount(String faceAmount) {
	this.faceAmount = faceAmount;
    }

    public Long getFace() {
	return face;
    }

    public void setFace(Long face) {
	this.face = face;
    }

    public String getSaleAmount() {
	if (sale == null)
	    return "0";
	else {
	    saleAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(sale + ""));
	    return saleAmount;
	}
    }

    public void setSaleAmount(String saleAmount) {
	this.saleAmount = saleAmount;
    }

    public String getDummyAmount() {
	if (dummy == null)
	    return "0";
	else {
	    dummyAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(dummy + ""));
	    return dummyAmount;
	}
    }

    public void setDummyAmount(String dummyAmount) {
	this.dummyAmount = dummyAmount;
    }

    public Long getDummy() {
	return dummy;
    }

    public void setDummy(Long dummy) {
	this.dummy = dummy;
    }

    public Long getSale() {
	return sale;
    }

    public void setSale(Long sale) {
	this.sale = sale;
    }

    public String getCostAmount() {
	if (cost == null)
	    return "0";
	else {
	    costAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(cost + ""));
	    return costAmount;
	}
    }

    public void setCostAmount(String costAmount) {
	this.costAmount = costAmount;
    }

    public Long getCost() {
	return cost;
    }

    public void setCost(Long cost) {
	this.cost = cost;
    }

    public String getProfitAmount() {
	if (sale == null || cost == null)
	    return "0";
	else {
	    if (fee == null) {
		profitAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid((sale - cost) + ""));
	    } else {
		profitAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid((sale - cost - fee) + ""));
	    }
	    return profitAmount;
	}
    }

    public void setProfitAmount(String profitAmount) {
	this.profitAmount = profitAmount;
    }

    public String getFeeAmount() {
	return feeAmount;
    }

    public void setFeeAmount(Integer serviceFee, Integer cashFee) {
	Double amount = 0d;
	if (serviceFee == null || serviceFee == 0) {
	    serviceFee = 0;
	} else {
	    if (sale != null) {
		amount = BigDecimalUtils.doubleDiveid(sale + "") * (BigDecimalUtils.doubleDiveid(serviceFee + "") / 100);
	    }
	}
	if (cashFee != null && cashFee != 0) {
	    amount += BigDecimalUtils.doubleDiveid(sale + "") * (1 - (BigDecimalUtils.doubleDiveid(serviceFee + "") / 100))
		    * (BigDecimalUtils.doubleDiveid(cashFee + "") / 100);
	}
	this.fee = BigDecimalUtils.multLong(amount + "");
	this.feeAmount = BigDecimalUtils.getAmountDesc(amount);
    }

    public Long getFee() {
	return fee;
    }

    public void setFee(Long fee) {
	this.fee = fee;
    }
}
