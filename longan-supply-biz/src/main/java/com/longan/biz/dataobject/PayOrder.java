package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class PayOrder {
    private Long id;
    private Integer bizId;
    private Long userId;
    private Long acctId;
    private Long amount;
    private Long amountDummy;
    private String acctDate;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer payMode;
    private Integer status;
    private String errorMsg;
    private Integer payType;
    private Integer channle;
    private Long bizOrderId;
    private Integer itemId;
    private Integer supplyItemId;
    private Integer supplyTraderId;
    private Long acctLogId;
    private String bankNo;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
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

    public Long getAmountDummy() {
	return amountDummy;
    }

    public void setAmountDummy(Long amountDummy) {
	this.amountDummy = amountDummy;
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

    public Integer getPayMode() {
	return payMode;
    }

    public void setPayMode(Integer payMode) {
	this.payMode = payMode;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public String getErrorMsg() {
	return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    public Integer getPayType() {
	return payType;
    }

    public void setPayType(Integer payType) {
	this.payType = payType;
    }

    public Integer getChannle() {
	return channle;
    }

    public void setChannle(Integer channle) {
	this.channle = channle;
    }

    public Long getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public Integer getSupplyItemId() {
	return supplyItemId;
    }

    public void setSupplyItemId(Integer supplyItemId) {
	this.supplyItemId = supplyItemId;
    }

    public Integer getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Integer supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
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

    public String getAmountDesc() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return result.toString();
    }

    public String getAmountDummyDesc() {
	if (amountDummy == null) {
	    return getAmountDesc();
	}
	Double result = BigDecimalUtils.doubleDiveid(amountDummy + "");
	return result.toString();
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return Constants.PayOrder.STATUS_UNCONFIRMED == status || Constants.PayOrder.STATUS_INIT == status
		|| Constants.PayOrder.STATUS_FAILED == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.PayOrder.STATUS_SUCCESS == status;
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}
	if (Constants.PayOrder.STATUS_INIT == status) {
	    result = Constants.PayOrder.STATUS_INIT_DESC;
	} else if (Constants.PayOrder.STATUS_SUCCESS == status) {
	    result = Constants.PayOrder.STATUS_SUCCESS_DESC;
	} else if (Constants.PayOrder.STATUS_FAILED == status) {
	    result = Constants.PayOrder.STATUS_FAILED_DESC;
	} else if (Constants.PayOrder.STATUS_UNCONFIRMED == status) {
	    result = Constants.PayOrder.STATUS_UNCONFIRMED_DESC;
	} else if (Constants.PayOrder.STATUS_REFUND == status) {
	    result = Constants.PayOrder.STATUS_REFUND_DESC;
	}
	return result;
    }

    public boolean canAdjust() {
	if (status == null) {
	    return false;
	}
	return Constants.PayOrder.STATUS_SUCCESS == status;
    }
}