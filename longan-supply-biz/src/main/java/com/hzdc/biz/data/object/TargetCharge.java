package com.hzdc.biz.data.object;

import java.util.Date;

public class TargetCharge {
    private Long userId;
    private Long acctId;
    private Long chargeAmount;
    private Date sdate;
    private String acctDate;

    private Long chargeOrderId;
    private Long acctLogId;
    private String memo;

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Long getAcctId() {
	return acctId;
    }

    public void setAcctId(Long acctId) {
	this.acctId = acctId;
    }

    public Long getChargeAmount() {
	return chargeAmount;
    }

    public void setChargeAmount(Long chargeAmount) {
	this.chargeAmount = chargeAmount;
    }

    public Date getSdate() {
	return sdate;
    }

    public void setSdate(Date sdate) {
	this.sdate = sdate;
    }

    public String getAcctDate() {
	return acctDate;
    }

    public void setAcctDate(String acctDate) {
	this.acctDate = acctDate;
    }

    public Long getChargeOrderId() {
	return chargeOrderId;
    }

    public void setChargeOrderId(Long chargeOrderId) {
	this.chargeOrderId = chargeOrderId;
    }

    public Long getAcctLogId() {
	return acctLogId;
    }

    public void setAcctLogId(Long acctLogId) {
	this.acctLogId = acctLogId;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }
}
