package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class ManualLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer status;
    private Long userId;
    private Long totalPrice;
    private Long succPrice;
    private Integer totalCount;
    private Integer succCount;
    private Long dealOperId;
    private Integer chargeType;
    private String memo;

    public String dealOperName;

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

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Long getTotalPrice() {
	return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
	this.totalPrice = totalPrice;
    }

    public Long getSuccPrice() {
	return succPrice;
    }

    public void setSuccPrice(Long succPrice) {
	this.succPrice = succPrice;
    }

    public Integer getTotalCount() {
	return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
	this.totalCount = totalCount;
    }

    public Integer getSuccCount() {
	return succCount;
    }

    public void setSuccCount(Integer succCount) {
	this.succCount = succCount;
    }

    public Long getDealOperId() {
	return dealOperId;
    }

    public void setDealOperId(Long dealOperId) {
	this.dealOperId = dealOperId;
    }

    public Integer getChargeType() {
	return chargeType;
    }

    public void setChargeType(Integer chargeType) {
	this.chargeType = chargeType;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }

    public String getDealOperName() {
	return dealOperName;
    }

    public void setDealOperName(String dealOperName) {
	this.dealOperName = dealOperName;
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}

	if (Constants.ManualLog.STATUS_CHARGING == status) {
	    result = Constants.ManualLog.STATUS_CHARGING_DESC;
	} else if (Constants.ManualLog.STATUS_FIN == status) {
	    result = Constants.ManualLog.STATUS_FIN_DESC;
	} else if (Constants.ManualLog.STATUS_FAILED == status) {
	    result = Constants.ManualLog.STATUS_FAILED_DESC;
	}
	return result;
    }

    public String getTotalPriceDesc() {
	if (totalPrice == null) {
	    return "";
	}
	Double result = BigDecimalUtils.doubleDiveid(totalPrice + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public String getSuccPriceDesc() {
	if (succPrice == null) {
	    return "";
	}
	Double result = BigDecimalUtils.doubleDiveid(succPrice + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public String getChargeTypeDesc() {
	String result = null;
	if (chargeType == null) {
	    return null;
	}

	if (Constants.ManualLog.TYPE_CHARGE_FILE == chargeType) {
	    result = Constants.ManualLog.TYPE_CHARGE_FILE_DESC;
	} else if (Constants.ManualLog.TYPE_CHARGE_TEXT == chargeType) {
	    result = Constants.ManualLog.TYPE_CHARGE_TEXT_DESC;
	}
	return result;
    }
}
