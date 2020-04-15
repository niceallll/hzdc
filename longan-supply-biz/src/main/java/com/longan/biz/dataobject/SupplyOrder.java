package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class SupplyOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long bulkId;
    private Date gmtCreate;
    private Date gmtModify;
    private Long bizOrderId;
    private Integer bizId;
    private Long itemSupplyId;
    private Long stockId;
    private Long userId;
    private String userName;
    private Integer itemId;
    private String itemName;
    private Integer itemFacePrice;
    private String itemUid;
    private Long amount;
    private Long amountDummy;
    private Long lockOperId;
    private Long dealOperId;
    private Long supplyTraderId;
    private String supplyTraderName;
    private Integer supplyTermPeriod;
    private Integer supplyCostTime;
    private Integer supplyType;
    private Integer supplyFacePrice;
    private Integer supplyCostPrice;
    private Integer supplyActualCost;
    private Integer supplyStatus;
    private Integer finalType;
    private Integer repeatType;
    private Integer combineType;
    private Integer manualType;
    private String upstreamSerialno;
    private Integer upstreamStatus;
    private Date upstreamDate;
    private String upstreamMemo;
    private boolean flagCardValid;
    public String lockOperName;
    public String dealOperName;
    private String downstreamName;
    private String uidAreaInfo;
    private String provinceCode;
    private Integer saleStatus;

    // 扩展信息
    private String extend;

    // qb区号
    private String areaCode;

    // 上游兑换卡、目前只电影票
    private String cardPwd;
    private String cardNote;

    // 拼单
    private boolean succCombine = false;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getBulkId() {
	return bulkId;
    }

    public void setBulkId(Long bulkId) {
	this.bulkId = bulkId;
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

    public Long getItemSupplyId() {
	return itemSupplyId;
    }

    public void setItemSupplyId(Long itemSupplyId) {
	this.itemSupplyId = itemSupplyId;
    }

    public Long getStockId() {
	return stockId;
    }

    public void setStockId(Long stockId) {
	this.stockId = stockId;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
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

    public Integer getItemFacePrice() {
	return itemFacePrice;
    }

    public void setItemFacePrice(Integer itemFacePrice) {
	this.itemFacePrice = itemFacePrice;
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

    public Long getLockOperId() {
	return lockOperId;
    }

    public void setLockOperId(Long lockOperId) {
	this.lockOperId = lockOperId;
    }

    public Long getDealOperId() {
	return dealOperId;
    }

    public void setDealOperId(Long dealOperId) {
	this.dealOperId = dealOperId;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public String getSupplyTraderName() {
	return supplyTraderName;
    }

    public void setSupplyTraderName(String supplyTraderName) {
	this.supplyTraderName = supplyTraderName;
    }

    public Integer getSupplyTermPeriod() {
	return supplyTermPeriod;
    }

    public void setSupplyTermPeriod(Integer supplyTermPeriod) {
	this.supplyTermPeriod = supplyTermPeriod;
    }

    public Integer getSupplyCostTime() {
	return supplyCostTime;
    }

    public void setSupplyCostTime(Integer supplyCostTime) {
	this.supplyCostTime = supplyCostTime;
    }

    public Integer getSupplyType() {
	return supplyType;
    }

    public void setSupplyType(Integer supplyType) {
	this.supplyType = supplyType;
    }

    public Integer getSupplyFacePrice() {
	return supplyFacePrice;
    }

    public void setSupplyFacePrice(Integer supplyFacePrice) {
	this.supplyFacePrice = supplyFacePrice;
    }

    public Integer getSupplyCostPrice() {
	return supplyCostPrice;
    }

    public void setSupplyCostPrice(Integer supplyCostPrice) {
	this.supplyCostPrice = supplyCostPrice;
    }

    public Integer getSupplyActualCost() {
	return supplyActualCost;
    }

    public void setSupplyActualCost(Integer supplyActualCost) {
	this.supplyActualCost = supplyActualCost;
    }

    public Integer getSupplyStatus() {
	return supplyStatus;
    }

    public void setSupplyStatus(Integer supplyStatus) {
	this.supplyStatus = supplyStatus;
    }

    public Integer getFinalType() {
	return finalType;
    }

    public void setFinalType(Integer finalType) {
	this.finalType = finalType;
    }

    public Integer getRepeatType() {
	return repeatType;
    }

    public void setRepeatType(Integer repeatType) {
	this.repeatType = repeatType;
    }

    public Integer getCombineType() {
	return combineType;
    }

    public void setCombineType(Integer combineType) {
	this.combineType = combineType;
    }

    public Integer getManualType() {
	return manualType;
    }

    public void setManualType(Integer manualType) {
	this.manualType = manualType;
    }

    public String getUpstreamSerialno() {
	return upstreamSerialno;
    }

    public void setUpstreamSerialno(String upstreamSerialno) {
	this.upstreamSerialno = upstreamSerialno;
    }

    public Integer getUpstreamStatus() {
	return upstreamStatus;
    }

    public void setUpstreamStatus(Integer upstreamStatus) {
	this.upstreamStatus = upstreamStatus;
    }

    public Date getUpstreamDate() {
	return upstreamDate;
    }

    public void setUpstreamDate(Date upstreamDate) {
	this.upstreamDate = upstreamDate;
    }

    public String getUpstreamMemo() {
	return upstreamMemo;
    }

    public void setUpstreamMemo(String upstreamMemo) {
	this.upstreamMemo = upstreamMemo;
    }

    public String getItemUid() {
	return itemUid;
    }

    public void setItemUid(String itemUid) {
	this.itemUid = itemUid;
    }

    public boolean isOver() {
	if (supplyStatus == null) {
	    return false;
	}
	return supplyStatus == Constants.SupplyOrder.STATUS_SUCCESS || supplyStatus == Constants.SupplyOrder.STATUS_FAILED;
    }

    public boolean isTypeCardForwardCharge() {
	return supplyType != null && supplyType == Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE;
    }

    public boolean isTypeDirectCharge() {
	return supplyType != null && supplyType == Constants.ItemSupply.TYPE_DIRECT_CHARGE;
    }

    public boolean isTypeMan() {
	return supplyType != null && supplyType == Constants.ItemSupply.TYPE_MAN;
    }

    public boolean isTypeCard() {
	return supplyType != null && supplyType == Constants.ItemSupply.TYPE_CARD;
    }

    public boolean isTypeCardCharge() {
	return supplyType != null && supplyType == Constants.ItemSupply.TYPE_CARD_CHARGE;
    }

    public boolean isManCharge() {
	if (supplyType == null) {
	    return false;
	}
	return supplyType == Constants.ItemSupply.TYPE_MAN;
    }

    public boolean isSupplyFromStock() {
	if (supplyType == null) {
	    return false;
	}
	return supplyType == Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE || supplyType == Constants.ItemSupply.TYPE_CARD;
    }

    public boolean isFlagCardValid() {
	return flagCardValid;
    }

    public void setFlagCardValid(boolean flagCardValid) {
	this.flagCardValid = flagCardValid;
    }

    public Integer computCostTime(Date date) {
	if (date != null) {
	    Long costTime = (System.currentTimeMillis() - date.getTime()) / 1000;
	    if (costTime == 0) {
		costTime = 1L;
	    }
	    return costTime.intValue();
	}
	return null;
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
	} else if (Constants.SupplyOrder.STATUS_LOCK == supplyStatus) {
	    result = Constants.SupplyOrder.STATUS_LOCK_DESC;
	} else if (Constants.SupplyOrder.STATUS_UNCONFIRMED == supplyStatus) {
	    result = Constants.SupplyOrder.STATUS_UNCONFIRMED_DESC;
	} else if (Constants.SupplyOrder.STATUS_EXCEPTION == supplyStatus) {
	    result = Constants.SupplyOrder.STATUS_EXCEPTION_DESC;
	} else if (Constants.SupplyOrder.STATUS_PARTS == supplyStatus) {
	    result = Constants.SupplyOrder.STATUS_PARTS_DESC;
	}
	return result;
    }

    public String getSaleStatusDesc() {
	if (Constants.ItemSupply.TYPE_MAN == supplyType) {
	    if (Constants.SupplyOrder.SALE_INIT == saleStatus) {
		return Constants.SupplyOrder.SALE_INIT_DESC;
	    } else if (Constants.SupplyOrder.SALE_EXPORT == saleStatus) {
		return Constants.SupplyOrder.SALE_EXPORT_DESC;
	    } else if (Constants.SupplyOrder.SALE_SUCCESS == saleStatus) {
		return Constants.SupplyOrder.SALE_SUCCESS_DESC;
	    } else if (Constants.SupplyOrder.SALE_FAILED == saleStatus) {
		return Constants.SupplyOrder.SALE_FAILED_DESC;
	    }
	}
	return null;
    }

    public String getSupplyCostTimeDesc() {
	if (supplyCostTime == null) {
	    return null;
	}
	return DateTool.secondToDate(supplyCostTime);
    }

    public String getLockOperName() {
	return lockOperName;
    }

    public void setLockOperName(String lockOperName) {
	this.lockOperName = lockOperName;
    }

    public String getDealOperName() {
	return dealOperName;
    }

    public void setDealOperName(String dealOperName) {
	this.dealOperName = dealOperName;
    }

    public String getDownstreamName() {
	return downstreamName;
    }

    public void setDownstreamName(String downstreamName) {
	this.downstreamName = downstreamName;
    }

    public boolean showRed() {
	if (supplyStatus == null) {
	    return false;
	}
	return Constants.SupplyOrder.STATUS_UNCONFIRMED == supplyStatus || Constants.SupplyOrder.STATUS_LOCK == supplyStatus
		|| Constants.SupplyOrder.STATUS_FAILED == supplyStatus || Constants.SupplyOrder.STATUS_PARTS == supplyStatus;
    }

    public boolean showGreen() {
	if (supplyStatus == null) {
	    return false;
	}
	return Constants.SupplyOrder.STATUS_SUCCESS == supplyStatus;
    }

    public Double getSupplyActualCostDesc() {
	if (supplyActualCost == null) {
	    return null;
	}
	return BigDecimalUtils.doubleDiveid(supplyActualCost + "");
    }

    public Double getSupplyCostPriceDesc() {
	if (supplyCostPrice == null) {
	    return null;
	}
	return BigDecimalUtils.doubleDiveid(supplyCostPrice + "");
    }

    public boolean canDeal() {
	if (supplyStatus == null || gmtCreate == null) {
	    return false;
	}

	Long now = System.currentTimeMillis();
	return (Constants.SupplyOrder.STATUS_CHARGING == supplyStatus || Constants.SupplyOrder.STATUS_UNCONFIRMED == supplyStatus)
		&& (now - gmtCreate.getTime() > 60 * 1000 * 1);
    }

    public boolean canPart() {
	if (supplyStatus == null || gmtCreate == null || !isCombine()) {
	    return false;
	}

	Long now = System.currentTimeMillis();
	return Constants.SupplyOrder.STATUS_PARTS == supplyStatus
		&& (succCombine || (now - gmtCreate.getTime() > 60 * 1000 * 30));
    }

    public boolean canAdjust() {
	if (supplyStatus == null) {
	    return false;
	}
	return Constants.SupplyOrder.STATUS_SUCCESS == supplyStatus || Constants.SupplyOrder.STATUS_INIT == supplyStatus;
    }

    public boolean isManualRepeatType() {
	if (manualType == null) {
	    return false;
	}
	return manualType == Constants.SupplyOrder.MANUAL_TYPE_YES;
    }

    public boolean isCombine() {
	if (combineType == null) {
	    return false;
	}
	return Constants.BizOrder.COMBINE_TYPE_YES == combineType;
    }

    public String getFinalTypeDesc() {
	String result = null;
	if (finalType == null) {
	    return result;
	}

	if (finalType == Constants.SupplyOrder.FINAL_TYPE_YES) {
	    result = Constants.SupplyOrder.FINAL_TYPE_YES_DESC;
	} else if (finalType == Constants.SupplyOrder.FINAL_TYPE_NO) {
	    result = Constants.SupplyOrder.FINAL_TYPE_NO_DESC;
	}
	return result;
    }

    public String getRepeatTypeDesc() {
	String result = null;
	if (repeatType == null) {
	    return result;
	}

	if (repeatType == Constants.SupplyOrder.REPEAT_TYPE_YES) {
	    result = Constants.SupplyOrder.REPEAT_TYPE_YES_DESC;
	} else if (repeatType == Constants.SupplyOrder.REPEAT_TYPE_NO) {
	    result = Constants.SupplyOrder.REPEAT_TYPE_NO_DESC;
	}
	return result;
    }

    public String getManualRepeatTypeDesc() {
	String result = null;
	if (manualType == null) {
	    return result;
	}

	if (manualType == Constants.SupplyOrder.MANUAL_TYPE_YES) {
	    result = Constants.SupplyOrder.MANUAL_TYPE_YES_DESC;
	} else if (manualType == Constants.SupplyOrder.MANUAL_TYPE_NO) {
	    result = Constants.SupplyOrder.MANUAL_TYPE_NO_DESC;
	}
	return result;
    }

    public String getIdDesc() {
	if (supplyTraderId == null) {
	    return id + "";
	}

	if (supplyTraderId == Constants.Ltkd.LTKD_USER_ID) {
	    // 临时用
	    try {
		Date temp1 = DateTool.strintToDatetime("2019-08-05 15:00:00");
		Date temp2 = DateTool.strintToDatetime("2019-08-05 20:00:00");
		if (gmtCreate.getTime() < temp1.getTime()) {
		    return id + "";
		}
		if (gmtCreate.getTime() > temp1.getTime() && gmtCreate.getTime() < temp2.getTime()) {
		    return "JG2X" + id;
		}
	    } catch (Exception ex) {
	    }
	    return Constants.Ltkd.LTKD_ID_KEY + id;
	} else if (supplyTraderId == Constants.Ltkd.SLS_USER_ID || supplyTraderId == Constants.Ltkd.SLSX_USER_ID
		|| supplyTraderId == Constants.Ltkd.HZXS_USER_ID || supplyTraderId == Constants.Ltkd.LTDK_USER_ID) {
	    return Constants.Ltkd.LTKD_ID_KEY + id;
	} else {
	    return id + "";
	}
    }

    public double getItemFacePriceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(itemFacePrice + "");
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

    public String getUidAreaInfo() {
	return uidAreaInfo;
    }

    public void setUidAreaInfo(String uidAreaInfo) {
	this.uidAreaInfo = uidAreaInfo;
    }

    public String getProvinceCode() {
	return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
    }

    public Integer getSaleStatus() {
	return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
	this.saleStatus = saleStatus;
    }

    public String getExtend() {
	return extend;
    }

    public void setExtend(String extend) {
	this.extend = extend;
    }

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    public String getCardPwd() {
	return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
	this.cardPwd = cardPwd;
    }

    public String getCardNote() {
	return cardNote;
    }

    public void setCardNote(String cardNote) {
	this.cardNote = cardNote;
    }

    public boolean isSuccCombine() {
	return succCombine;
    }

    public void setSuccCombine(boolean succCombine) {
	this.succCombine = succCombine;
    }
}