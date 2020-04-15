package com.longan.biz.sumobject;

import com.longan.biz.utils.BigDecimalUtils;

public class SmsOrderCount {
    private Long totalCount;
    private String totalAmount;
    private Long amount;
    private String costAmount;
    private Long cost;

    public Long getTotalCount() {
	if (totalCount == null) {
	    return 0l;
	}
	return totalCount;
    }

    public void setTotalCount(Long totalCount) {
	this.totalCount = totalCount;
    }

    public String getTotalAmount() {
	if (amount == null) {
	    return "0";
	} else {
	    totalAmount = BigDecimalUtils.getAmountDesc(BigDecimalUtils.doubleDiveid(amount + ""));
	    return totalAmount;
	}
    }

    public void setTotalAmount(String totalAmount) {
	this.totalAmount = totalAmount;
    }

    public Long getAmount() {
	return amount;
    }

    public void setAmount(Long amount) {
	this.amount = amount;
    }

    public String getCostAmount() {
	if (cost == null) {
	    return "0";
	} else {
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
}
