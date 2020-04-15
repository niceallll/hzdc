package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class SmsOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer status;
    private Long userId;
    private Long amount;
    private Integer bizId;
    private Integer itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer itemFacePrice;
    private Integer itemCostPrice;
    private Long itemSupplyId;
    private Integer uidCount;
    private Integer totalCount;
    private Integer succCount;
    private Integer failCount;
    private Long costPrice;
    private Long upstreamId;
    private String upstreamSerialno;
    private String upstreamMemo;
    private String downstreamSerialno;
    private Integer costTime;
    private Integer carrierType;
    private String extend;

    private String uids;
    private String texts;
    private String provinces;

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

    public Long getAmount() {
	return amount;
    }

    public void setAmount(Long amount) {
	this.amount = amount;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public Integer getItemPrice() {
	return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
	this.itemPrice = itemPrice;
    }

    public Integer getItemFacePrice() {
	return itemFacePrice;
    }

    public void setItemFacePrice(Integer itemFacePrice) {
	this.itemFacePrice = itemFacePrice;
    }

    public Integer getItemCostPrice() {
	return itemCostPrice;
    }

    public void setItemCostPrice(Integer itemCostPrice) {
	this.itemCostPrice = itemCostPrice;
    }

    public Long getItemSupplyId() {
	return itemSupplyId;
    }

    public void setItemSupplyId(Long itemSupplyId) {
	this.itemSupplyId = itemSupplyId;
    }

    public Integer getUidCount() {
	return uidCount;
    }

    public void setUidCount(Integer uidCount) {
	this.uidCount = uidCount;
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

    public Integer getFailCount() {
	return failCount;
    }

    public void setFailCount(Integer failCount) {
	this.failCount = failCount;
    }

    public Long getCostPrice() {
	return costPrice;
    }

    public void setCostPrice(Long costPrice) {
	this.costPrice = costPrice;
    }

    public Long getUpstreamId() {
	return upstreamId;
    }

    public void setUpstreamId(Long upstreamId) {
	this.upstreamId = upstreamId;
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

    public String getDownstreamSerialno() {
	return downstreamSerialno;
    }

    public void setDownstreamSerialno(String downstreamSerialno) {
	this.downstreamSerialno = downstreamSerialno;
    }

    public Integer getCostTime() {
	return costTime;
    }

    public void setCostTime(Integer costTime) {
	this.costTime = costTime;
    }

    public Integer getCarrierType() {
	return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
	this.carrierType = carrierType;
    }

    public String getExtend() {
	return extend;
    }

    public void setExtend(String extend) {
	this.extend = extend;
    }

    public String getUids() {
	return uids;
    }

    public void setUids(String uids) {
	this.uids = uids;
    }

    public String getTexts() {
	return texts;
    }

    public void setTexts(String texts) {
	this.texts = texts;
    }

    public String getProvinces() {
	return provinces;
    }

    public void setProvinces(String provinces) {
	this.provinces = provinces;
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}

	if (Constants.BizOrder.STATUS_INIT == status) {
	    result = Constants.BizOrder.STATUS_INIT_DESC;
	} else if (Constants.BizOrder.STATUS_CHARGING == status) {
	    result = Constants.BizOrder.STATUS_CHARGING_DESC;
	} else if (Constants.BizOrder.STATUS_SUCCESS == status) {
	    result = Constants.BizOrder.STATUS_SUCCESS_DESC;
	} else if (Constants.BizOrder.STATUS_FAILED == status) {
	    result = Constants.BizOrder.STATUS_FAILED_DESC;
	} else if (Constants.BizOrder.STATUS_PARTS == status) {
	    result = Constants.BizOrder.STATUS_PARTS_DESC;
	}
	return result;
    }

    public String getAmountDesc() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return result.toString();
    }

    public String getItemFacePriceDesc() {
	Double result = BigDecimalUtils.doubleDiveid(itemFacePrice + "");
	return result.toString();
    }

    public double getAmountDouble() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return result;
    }

    public double getItemPriceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(itemPrice + "");
	return result;
    }

    public double getCostPriceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(costPrice + "");
	return result;
    }

    public double getItemCostPriceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(itemCostPrice + "");
	return result;
    }

    public String getCostTimeDesc() {
	if (costTime == null) {
	    return null;
	}
	return DateTool.secondToDate(costTime);
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return Constants.BizOrder.STATUS_PARTS == status || Constants.BizOrder.STATUS_FAILED == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.BizOrder.STATUS_SUCCESS == status;
    }

    public boolean canCallback() {
	if (status == null) {
	    return false;
	}
	return Constants.BizOrder.STATUS_SUCCESS == status || Constants.BizOrder.STATUS_PARTS == status
		|| Constants.BizOrder.STATUS_FAILED == status;
    }

    public boolean canDeal() {
	if (status == null) {
	    return true;
	}
	return Constants.BizOrder.STATUS_CHARGING == status || Constants.BizOrder.STATUS_EXCEPTION == status;
    }

    public boolean isOver() {
	if (status == null) {
	    return false;
	}
	return status == Constants.BizOrder.STATUS_SUCCESS || status == Constants.BizOrder.STATUS_PARTS
		|| status == Constants.BizOrder.STATUS_FAILED;
    }
}
