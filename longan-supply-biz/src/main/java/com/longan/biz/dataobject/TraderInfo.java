package com.longan.biz.dataobject;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class TraderInfo {
    private Long id;
    private Long userId;
    private Integer supplyType;
    private Integer supplyBulkType;
    private Boolean isAsyncSupply;
    private Boolean needSmsNote;
    private Integer notifyWay1;
    private Integer notifyWay2;
    private Integer notifyWay3;
    private Integer serviceFee;
    private Integer cashFee;
    private String whitelistIp;
    private String callbackUrl;
    private String downstreamKey;
    private Integer traderType;
    private Integer status;
    private Integer alertStatus;
    private Long chargingLimit;
    private Integer maxDay;
    private Integer maxMounth;
    private String smsExtend;
    private String notifyUrl;

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

    public Integer getSupplyType() {
	return supplyType;
    }

    public void setSupplyType(Integer supplyType) {
	this.supplyType = supplyType;
    }

    public Integer getSupplyBulkType() {
	return supplyBulkType;
    }

    public void setSupplyBulkType(Integer supplyBulkType) {
	this.supplyBulkType = supplyBulkType;
    }

    public Boolean getIsAsyncSupply() {
	return isAsyncSupply;
    }

    public void setIsAsyncSupply(Boolean isAsyncSupply) {
	this.isAsyncSupply = isAsyncSupply;
    }

    public Boolean getNeedSmsNote() {
	return needSmsNote;
    }

    public void setNeedSmsNote(Boolean needSmsNote) {
	this.needSmsNote = needSmsNote;
    }

    public Boolean isNeedSmsNote() {
	if (needSmsNote == null) {
	    return false;
	}
	return needSmsNote;
    }

    public Integer getNotifyWay1() {
	return notifyWay1;
    }

    public void setNotifyWay1(Integer notifyWay1) {
	this.notifyWay1 = notifyWay1;
    }

    public Integer getNotifyWay2() {
	return notifyWay2;
    }

    public void setNotifyWay2(Integer notifyWay2) {
	this.notifyWay2 = notifyWay2;
    }

    public Integer getNotifyWay3() {
	return notifyWay3;
    }

    public void setNotifyWay3(Integer notifyWay3) {
	this.notifyWay3 = notifyWay3;
    }

    public Integer getServiceFee() {
	return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
	this.serviceFee = serviceFee;
    }

    public Integer getCashFee() {
	return cashFee;
    }

    public void setCashFee(Integer cashFee) {
	this.cashFee = cashFee;
    }

    public Boolean isManNotify(Integer carrierType) {
	if (carrierType == null) {
	    return false;
	}
	if (carrierType == Constants.Item.CARRIER_TYPE_UNICOM) {
	    return notifyWay1 != null && notifyWay1 == Constants.TraderInfo.NOTIFY_WAY_MANUAL;
	}
	if (carrierType == Constants.Item.CARRIER_TYPE_TELECOM) {
	    return notifyWay2 != null && notifyWay2 == Constants.TraderInfo.NOTIFY_WAY_MANUAL;
	}
	if (carrierType == Constants.Item.CARRIER_TYPE_MOBILE) {
	    return notifyWay3 != null && notifyWay3 == Constants.TraderInfo.NOTIFY_WAY_MANUAL;
	}
	return false;
    }

    public Boolean isAutoNotify(Integer carrierType) {
	if (carrierType == null) {
	    return false;
	}
	if (carrierType == Constants.Item.CARRIER_TYPE_UNICOM) {
	    return notifyWay1 != null && notifyWay1 == Constants.TraderInfo.NOTIFY_WAY_AUTO;
	}
	if (carrierType == Constants.Item.CARRIER_TYPE_TELECOM) {
	    return notifyWay2 != null && notifyWay2 == Constants.TraderInfo.NOTIFY_WAY_AUTO;
	}
	if (carrierType == Constants.Item.CARRIER_TYPE_MOBILE) {
	    return notifyWay3 != null && notifyWay3 == Constants.TraderInfo.NOTIFY_WAY_AUTO;
	}
	return false;
    }

    public Boolean isDownstream() {
	return Constants.TraderInfo.TRADER_TYPE_DOWNSTREAM == traderType;
    }

    public String getWhitelistIp() {
	return whitelistIp;
    }

    public void setWhitelistIp(String whitelistIp) {
	this.whitelistIp = whitelistIp;
    }

    public String getCallbackUrl() {
	return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
	this.callbackUrl = callbackUrl;
    }

    public String getDownstreamKey() {
	return downstreamKey;
    }

    public void setDownstreamKey(String downstreamKey) {
	this.downstreamKey = downstreamKey;
    }

    public Integer getTraderType() {
	return traderType;
    }

    public void setTraderType(Integer traderType) {
	this.traderType = traderType;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getAlertStatus() {
	return alertStatus;
    }

    public void setAlertStatus(Integer alertStatus) {
	this.alertStatus = alertStatus;
    }

    public Long getChargingLimit() {
	return chargingLimit;
    }

    public void setChargingLimit(Long chargingLimit) {
	this.chargingLimit = chargingLimit;
    }

    public Integer getMaxDay() {
	return maxDay;
    }

    public void setMaxDay(Integer maxDay) {
	this.maxDay = maxDay;
    }

    public Integer getMaxMounth() {
	return maxMounth;
    }

    public void setMaxMounth(Integer maxMounth) {
	this.maxMounth = maxMounth;
    }

    public String getSmsExtend() {
	return smsExtend;
    }

    public void setSmsExtend(String smsExtend) {
	this.smsExtend = smsExtend;
    }

    public String getNotifyUrl() {
	return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
	this.notifyUrl = notifyUrl;
    }

    public String getSupplyWayDesc() {
	String result = null;
	if (isAsyncSupply == null) {
	    return null;
	}
	if (isAsyncSupply) {
	    result = Constants.TraderInfo.SUPPLY_WAY_ASYNC_DESC;
	} else {
	    result = Constants.TraderInfo.SUPPLY_WAY_SYNC_DESC;
	}
	return result;
    }

    public boolean isManualSupply() {
	if (supplyType == null) {
	    return false;
	}
	return Constants.ItemSupply.TYPE_MAN == supplyType;
    }

    public String getChargingLimitDesc() {
	if (chargingLimit == null) {
	    return "";
	}
	Double result = BigDecimalUtils.doubleDiveid(chargingLimit + "");
	return BigDecimalUtils.getAmountDesc(result);
    }

    public String getSupplyTypeDesc() {
	String result = null;
	if (supplyType == null) {
	    return null;
	}
	if (Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE == supplyType) {
	    result = Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE_DESC;
	} else if (Constants.ItemSupply.TYPE_DIRECT_CHARGE == supplyType) {
	    result = Constants.ItemSupply.TYPE_DIRECT_CHARGE_DESC;
	} else if (Constants.ItemSupply.TYPE_MAN == supplyType) {
	    result = Constants.ItemSupply.TYPE_MAN_DESC;
	} else if (Constants.ItemSupply.TYPE_CARD == supplyType) {
	    result = Constants.ItemSupply.TYPE_CARD_DESC;
	} else if (Constants.ItemSupply.TYPE_CARD_CHARGE == supplyType) {
	    result = Constants.ItemSupply.TYPE_CARD_CHARGE_DESC;
	}
	return result;
    }

    public String getServiceFeeDesc() {
	if (serviceFee == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(serviceFee + "");
	return result.toString();
    }

    public String getCashFeeDesc() {
	if (cashFee == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(cashFee + "");
	return result.toString();
    }
}