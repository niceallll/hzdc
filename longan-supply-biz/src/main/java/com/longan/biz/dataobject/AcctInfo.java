package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class AcctInfo {
    private Long id;
    private Long userId;
    private String loginId;
    private Long balance;
    private Long lastTradeBalance;
    private Date gmtCreate;
    private Date gmtModify;
    private Date lastTradeDate;
    private Integer status;
    private String verificationCode;
    private Integer salesPrice;

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

    public String getLoginId() {
	return loginId;
    }

    public void setLoginId(String loginId) {
	this.loginId = loginId;
    }

    public Long getBalance() {
	return balance;
    }

    public void setBalance(Long balance) {
	this.balance = balance;
    }

    public Long getLastTradeBalance() {
	return lastTradeBalance;
    }

    public void setLastTradeBalance(Long lastTradeBalance) {
	this.lastTradeBalance = lastTradeBalance;
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

    public Date getLastTradeDate() {
	return lastTradeDate;
    }

    public void setLastTradeDate(Date lastTradeDate) {
	this.lastTradeDate = lastTradeDate;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public String getVerificationCode() {
	return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
	this.verificationCode = verificationCode;
    }

    public Integer getSalesPrice() {
	return salesPrice;
    }

    public void setSalesPrice(Integer salesPrice) {
	this.salesPrice = salesPrice;
    }

    public String getBalanceDesc() {
	Double result = BigDecimalUtils.doubleDiveid(balance + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public String getStatusDesc() {
	if (status == null) {
	    return null;
	}
	if (Constants.AcctInfo.STATUS_NORMAL == status) {
	    return Constants.AcctInfo.STATUS_NORMAL_DESC;
	} else if (Constants.AcctInfo.STATUS_FREEZE == status) {
	    return Constants.AcctInfo.STATUS_FREEZE_DESC;
	} else if (Constants.AcctInfo.STATUS_DEL == status) {
	    return Constants.AcctInfo.STATUS_DEL_DESC;
	}
	return null;
    }

    public String getSalePriceDesc() {
	if (salesPrice == null) {
	    return null;
	}
	if (Constants.AcctInfo.SALES_PRICE_1 == salesPrice) {
	    return Constants.AcctInfo.SALES_PRICE_1_DESC;
	} else if (Constants.AcctInfo.SALES_PRICE_2 == salesPrice) {
	    return Constants.AcctInfo.SALES_PRICE_2_DESC;
	} else if (Constants.AcctInfo.SALES_PRICE_3 == salesPrice) {
	    return Constants.AcctInfo.SALES_PRICE_3_DESC;
	} else if (Constants.AcctInfo.SALES_PRICE_4 == salesPrice) {
	    return Constants.AcctInfo.SALES_PRICE_4_DESC;
	}
	return null;
    }
}