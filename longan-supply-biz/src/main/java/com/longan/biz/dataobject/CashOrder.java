package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class CashOrder {
    private Long id;
    private Long userId;
    private Long acctId;
    private Long amount;
    private String acctDate;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer status;
    private Integer payType;
    private Long operId;
    private String operName;
    private String verifyOperName;
    private String errorMsg;
    private Long acctLogId;
    private String bankNo;
    private String bankSerialno;
    private String alipaySerialno;
    private String tenpaySerialno;
    private String memo;
    private String userName;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

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

    public Long getAmount() {
	return amount;
    }

    public void setAmount(Long amount) {
	this.amount = amount;
    }

    public String getAcctDate() {
	return acctDate;
    }

    public void setAcctDate(String acctDate) {
	this.acctDate = acctDate;
    }

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
	return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
	this.gmtModify = gmtModify;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getPayType() {
	return payType;
    }

    public void setPayType(Integer payType) {
	this.payType = payType;
    }

    public Long getOperId() {
	return operId;
    }

    public void setOperId(Long operId) {
	this.operId = operId;
    }

    public String getOperName() {
	return operName;
    }

    public void setOperName(String operName) {
	this.operName = operName;
    }

    public String getErrorMsg() {
	return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    public Long getAcctLogId() {
	return acctLogId;
    }

    public void setAcctLogId(Long acctLogId) {
	this.acctLogId = acctLogId;
    }

    public String getBankNo() {
	return bankNo;
    }

    public void setBankNo(String bankNo) {
	this.bankNo = bankNo;
    }

    public String getBankSerialno() {
	return bankSerialno;
    }

    public void setBankSerialno(String bankSerialno) {
	this.bankSerialno = bankSerialno;
    }

    public String getAlipaySerialno() {
	return alipaySerialno;
    }

    public void setAlipaySerialno(String alipaySerialno) {
	this.alipaySerialno = alipaySerialno;
    }

    public String getTenpaySerialno() {
	return tenpaySerialno;
    }

    public void setTenpaySerialno(String tenpaySerialno) {
	this.tenpaySerialno = tenpaySerialno;
    }

    public String getVerifyOperName() {
	return verifyOperName;
    }

    public void setVerifyOperName(String verifyOperName) {
	this.verifyOperName = verifyOperName;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getAmountDesc() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public boolean canDeal() {
	if (status == null) {
	    return false;
	}
	return Constants.CashOrder.STATUS_INIT == status;
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return Constants.CashOrder.STATUS_UNCONFIRMED == status || Constants.CashOrder.STATUS_INIT == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.CashOrder.STATUS_SUCCESS == status;
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}
	if (Constants.CashOrder.STATUS_INIT == status) {
	    result = Constants.CashOrder.STATUS_INIT_DESC;
	} else if (Constants.CashOrder.STATUS_SUCCESS == status) {
	    result = Constants.CashOrder.STATUS_SUCCESS_DESC;
	} else if (Constants.CashOrder.STATUS_FAILED == status) {
	    result = Constants.CashOrder.STATUS_FAILED_DESC;
	} else if (Constants.CashOrder.STATUS_UNCONFIRMED == status) {
	    result = Constants.CashOrder.STATUS_UNCONFIRMED_DESC;
	}
	return result;
    }

}