package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class RefundOrder {
    private Long id;
    private Long payOrderId;
    private Integer bizId;
    private Long userId;
    private Long acctId;
    private Long amount;
    private Long amountDummy;
    private String acctDate;
    private Long acctLogId;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer status;
    private Integer payType;
    private String errorMsg;
    private String memo;
    private Long bizOrderId;
    private Integer itemId;
    private Integer supplyItemId;
    private Integer supplyTraderId;
    private Long operId;
    private String operName;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getPayOrderId() {
	return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
	this.payOrderId = payOrderId;
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

    public Long getAcctLogId() {
	return acctLogId;
    }

    public void setAcctLogId(Long acctLogId) {
	this.acctLogId = acctLogId;
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

    public String getErrorMsg() {
	return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
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
	return Constants.RefundOrder.STATUS_UNCONFIRMED == status || Constants.RefundOrder.STATUS_INIT == status
		|| Constants.RefundOrder.STATUS_FAILED == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.RefundOrder.STATUS_SUCCESS == status;
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}
	if (Constants.RefundOrder.STATUS_INIT == status) {
	    result = Constants.RefundOrder.STATUS_INIT_DESC;
	} else if (Constants.RefundOrder.STATUS_SUCCESS == status) {
	    result = Constants.RefundOrder.STATUS_SUCCESS_DESC;
	} else if (Constants.RefundOrder.STATUS_FAILED == status) {
	    result = Constants.RefundOrder.STATUS_FAILED_DESC;
	} else if (Constants.RefundOrder.STATUS_UNCONFIRMED == status) {
	    result = Constants.RefundOrder.STATUS_UNCONFIRMED_DESC;
	}
	return result;
    }

    public String getPayTypeDesc() {
	String result = null;
	if (payType == null) {
	    return null;
	}
	if (Constants.RefundOrder.PAY_TYPE_OPERATOR == payType) {
	    result = Constants.RefundOrder.PAY_TYPE_OPERATOR_DESC;
	} else if (Constants.RefundOrder.PAY_TYPE_SYSTEM == payType) {
	    result = Constants.RefundOrder.PAY_TYPE_SYSTEM_DESC;
	}
	return result;
    }

    public double getAmountDouble() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return result;
    }

    public double getAmountDummyDouble() {
	if (amountDummy == null) {
	    return getAmountDouble();
	}
	Double result = BigDecimalUtils.doubleDiveid(amountDummy + "");
	return result;
    }
}