package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class AcctLog {
    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Long userId;
    private Long acctId;
    private Long billId;
    private Integer billType;
    private Long bizOrderId;
    private Integer bizId;
    private Integer status;
    private Integer tradeType;
    private Long amtIn;
    private Long amtOut;
    private Long amtInEx;
    private Long amtOutEx;
    private Long amtBalance;
    private Integer channel;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public Long getBillId() {
	return billId;
    }

    public void setBillId(Long billId) {
	this.billId = billId;
    }

    public Integer getBillType() {
	return billType;
    }

    public void setBillType(Integer billType) {
	this.billType = billType;
    }

    public Long getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getTradeType() {
	return tradeType;
    }

    public void setTradeType(Integer tradeType) {
	this.tradeType = tradeType;
    }

    public Long getAmtIn() {
	return amtIn;
    }

    public void setAmtIn(Long amtIn) {
	this.amtIn = amtIn;
    }

    public Long getAmtOut() {
	return amtOut;
    }

    public void setAmtOut(Long amtOut) {
	this.amtOut = amtOut;
    }

    public Long getAmtInEx() {
	return amtInEx;
    }

    public void setAmtInEx(Long amtInEx) {
	this.amtInEx = amtInEx;
    }

    public Long getAmtOutEx() {
	return amtOutEx;
    }

    public void setAmtOutEx(Long amtOutEx) {
	this.amtOutEx = amtOutEx;
    }

    public Long getAmtBalance() {
	return amtBalance;
    }

    public void setAmtBalance(Long amtBalance) {
	this.amtBalance = amtBalance;
    }

    public Integer getChannel() {
	return channel;
    }

    public void setChannel(Integer channel) {
	this.channel = channel;
    }

    public String getAmtInDesc() {
	if (amtIn == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(amtIn + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public String getAmtOutDesc() {
	if (amtOut == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(amtOut + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public String getAmtInExDesc() {
	if (amtInEx == null) {
	    return getAmtInDesc();
	}
	Double result = BigDecimalUtils.doubleDiveid(amtInEx + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public String getAmtOutExDesc() {
	if (amtOutEx == null) {
	    return getAmtOutDesc();
	}
	Double result = BigDecimalUtils.doubleDiveid(amtOutEx + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public String getAmtBalanceDesc() {
	if (amtBalance == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(amtBalance + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return Constants.AcctLog.STATUS_EXCEPTION == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.AcctLog.STATUS_NORMAL == status;
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}

	if (Constants.AcctLog.STATUS_EXCEPTION == status) {
	    result = Constants.AcctLog.STATUS_EXCEPTION_DESC;
	} else if (Constants.AcctLog.STATUS_NORMAL == status) {
	    result = Constants.AcctLog.STATUS_NORMAL_DESC;
	}
	return result;
    }

    public String getTradeTypeDesc() {
	String result = null;
	if (tradeType == null) {
	    return null;
	}
	if (Constants.AcctLog.TRADE_TYPE_IN == tradeType) {
	    result = Constants.AcctLog.TRADE_TYPE_IN_DESC;
	} else if (Constants.AcctLog.TRADE_TYPE_OUT == tradeType) {
	    result = Constants.AcctLog.TRADE_TYPE_OUT_DESC;
	}
	return result;
    }

    public String getBillTypeDesc() {
	String result = null;
	if (billType == null) {
	    return null;
	}
	if (Constants.AcctLog.BILL_TYPE_CHARGE_ORDER == billType) {
	    result = Constants.AcctLog.BILL_TYPE_CHARGE_ORDER_DESC;
	} else if (Constants.AcctLog.BILL_TYPE_PAY_ORDER == billType) {
	    result = Constants.AcctLog.BILL_TYPE_PAY_ORDER_DESC;
	} else if (Constants.AcctLog.BILL_TYPE_REFUND_ORDER == billType) {
	    result = Constants.AcctLog.BILL_TYPE_REFUND_ORDER_DESC;
	} else if (Constants.AcctLog.BILL_TYPE_CASH_ORDER == billType) {
	    result = Constants.AcctLog.BILL_TYPE_CASH_ORDER_DESC;
	}
	return result;
    }

    private String itemId;
    private String itemName;
    private String upStreamId;

    public String getItemId() {
	return itemId;
    }

    public void setItemId(String itemId) {
	this.itemId = itemId;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public String getUpStreamId() {
	return upStreamId;
    }

    public void setUpStreamId(String upStreamId) {
	this.upStreamId = upStreamId;
    }

    public double getAmtInDouble() {
	Double result = BigDecimalUtils.doubleDiveid(amtIn + "");
	return result;
    }

    public double getAmtOutDouble() {
	Double result = BigDecimalUtils.doubleDiveid(amtOut + "");
	return result;
    }

    public double getAmtInExDouble() {
	if (amtInEx == null) {
	    return getAmtInDouble();
	}
	Double result = BigDecimalUtils.doubleDiveid(amtInEx + "");
	return result;
    }

    public double getAmtOutExDouble() {
	if (amtOutEx == null) {
	    return getAmtOutDouble();
	}
	Double result = BigDecimalUtils.doubleDiveid(amtOutEx + "");
	return result;
    }

    public double getAmtBalanceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(amtBalance + "");
	return result;
    }
}