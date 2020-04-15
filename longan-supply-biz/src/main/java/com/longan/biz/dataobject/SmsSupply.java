package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class SmsSupply implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Long bizOrderId;
    private Long userId;
    private Integer bizId;
    private Integer supplyStatus;
    private Long amount;
    private String itemUid;
    private Integer itemPrice;
    private Integer itemCostPrice;
    private Integer count;
    private Long costPrice;
    private Long supplyTraderId;
    private String upstreamSerialno;
    private String upstreamMemo;
    private Integer costTime;
    private String provinceCode;
    private String text;

    private String supplyTraderName;
    private String provinceName;
    private Boolean callback = false;

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

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Long getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public Integer getSupplyStatus() {
	return supplyStatus;
    }

    public void setSupplyStatus(Integer supplyStatus) {
	this.supplyStatus = supplyStatus;
    }

    public Long getAmount() {
	return amount;
    }

    public void setAmount(Long amount) {
	this.amount = amount;
    }

    public String getItemUid() {
	return itemUid;
    }

    public void setItemUid(String itemUid) {
	this.itemUid = itemUid;
    }

    public Integer getItemPrice() {
	return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
	this.itemPrice = itemPrice;
    }

    public Integer getItemCostPrice() {
	return itemCostPrice;
    }

    public void setItemCostPrice(Integer itemCostPrice) {
	this.itemCostPrice = itemCostPrice;
    }

    public Integer getCount() {
	return count;
    }

    public void setCount(Integer count) {
	this.count = count;
    }

    public Long getCostPrice() {
	return costPrice;
    }

    public void setCostPrice(Long costPrice) {
	this.costPrice = costPrice;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public String getUpstreamSerialno() {
	return upstreamSerialno;
    }

    public void setUpstreamSerialno(String upstreamSerialno) {
	this.upstreamSerialno = upstreamSerialno;
    }

    public String getUpstreamMemo() {
	return upstreamMemo;
    }

    public void setUpstreamMemo(String upstreamMemo) {
	this.upstreamMemo = upstreamMemo;
    }

    public Integer getCostTime() {
	return costTime;
    }

    public void setCostTime(Integer costTime) {
	this.costTime = costTime;
    }

    public String getProvinceCode() {
	return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public String getSupplyTraderName() {
	return supplyTraderName;
    }

    public void setSupplyTraderName(String supplyTraderName) {
	this.supplyTraderName = supplyTraderName;
    }

    public String getProvinceName() {
	return provinceName;
    }

    public void setProvinceName(String provinceName) {
	this.provinceName = provinceName;
    }

    public Boolean isCallback() {
	return callback;
    }

    public void setCallback(Boolean callback) {
	this.callback = callback;
    }

    public String getSupplyStatusDesc() {
	if (supplyStatus == null) {
	    return null;
	}

	String result = null;
	if (Constants.SupplyOrder.STATUS_INIT == supplyStatus) {
	    result = Constants.SupplyOrder.STATUS_INIT_DESC;
	} else if (Constants.SupplyOrder.STATUS_CHARGING == supplyStatus) {
	    result = Constants.SupplyOrder.STATUS_CHARGING_DESC;
	} else if (Constants.SupplyOrder.STATUS_SUCCESS == supplyStatus) {
	    result = Constants.SupplyOrder.STATUS_SUCCESS_DESC;
	} else if (Constants.SupplyOrder.STATUS_FAILED == supplyStatus) {
	    result = Constants.SupplyOrder.STATUS_FAILED_DESC;
	}
	return result;
    }

    public String getAmountDesc() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return result.toString();
    }

    public String getCostPriceDesc() {
	Double result = BigDecimalUtils.doubleDiveid(costPrice + "");
	return result.toString();
    }

    public double getItemPriceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(itemPrice + "");
	return result;
    }

    public double getAmountDouble() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return result;
    }

    public double getItemCostPriceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(itemCostPrice + "");
	return result;
    }

    public double getCostPriceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(costPrice + "");
	return result;
    }

    public String getCostTimeDesc() {
	if (costTime == null) {
	    return null;
	}
	return DateTool.secondToDate(costTime);
    }

    public boolean showRed() {
	if (supplyStatus == null) {
	    return false;
	}
	return Constants.SupplyOrder.STATUS_FAILED == supplyStatus;
    }

    public boolean showGreen() {
	if (supplyStatus == null) {
	    return false;
	}
	return Constants.SupplyOrder.STATUS_SUCCESS == supplyStatus;
    }

    public boolean canDeal() {
	if (supplyStatus == null || gmtCreate == null) {
	    return false;
	}
	Long now = System.currentTimeMillis();
	return (Constants.SupplyOrder.STATUS_CHARGING == supplyStatus) && now - gmtCreate.getTime() > 60 * 1000 * 2;
    }

    public boolean isOver() {
	if (supplyStatus == null) {
	    return false;
	}
	return supplyStatus == Constants.SupplyOrder.STATUS_SUCCESS || supplyStatus == Constants.SupplyOrder.STATUS_FAILED;
    }
}
