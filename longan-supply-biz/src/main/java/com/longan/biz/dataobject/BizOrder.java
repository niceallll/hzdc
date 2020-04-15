package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class BizOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Date gmtNotify;
    private Integer status;
    private Integer notifyStatus;
    private Long payOrderId;
    private Long userId;
    private String userName;
    private Long amount;
    private Long amountDummy;
    private Integer amt;
    private Integer bizId;
    private Integer itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer itemFacePrice;
    private Integer itemCategoryId;
    private String itemUid;
    private String itemCard;
    private String itemPosId;
    private String itemPcId;
    private String itemCardPwd;
    private Long itemSupplyId;
    private Integer itemSize;
    private Long stockId;
    private Integer channel;
    private String upstreamId;
    private String upstreamName;
    private String upstreamSerialno;
    private Date upstreamDate;
    private String upstreamMemo;
    private String downstreamId;
    private String downstreamName;
    private Date downstreamDate;
    private String downstreamSerialno;
    private String downstreamNotes;
    private String memo;
    private Long lockOperId;
    private Long dealOperId;
    private String uidAreaInfo;
    public String lockOperName;
    public String dealOperName;
    private Integer costTime;
    private Integer supplyType;
    private Integer downstreamSupplyWay;
    private Integer itemCostPrice;
    private Integer carrierType;
    private String provinceCode;
    private Integer actualCost;
    private Integer combineType;
    private Integer combineCount;

    // repeat
    private Integer repeatType;
    private Integer supplyCount;
    private Integer supplyFilterIndex;
    private Integer manualType;

    // 扩展信息
    private String extend;

    // qb area caller
    private String areaCode;

    // 积分卡信息
    private String cardId;
    private String cardPwd;
    private String cardFinalDate;
    private String cardNote;

    private String pddStatus;

    public String getPddStatus() {
        return pddStatus;
    }

    public void setPddStatus(String pddStatus) {
        this.pddStatus = pddStatus;
    }

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

    public Date getGmtNotify() {
	return gmtNotify;
    }

    public void setGmtNotify(Date gmtNotify) {
	this.gmtNotify = gmtNotify;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getNotifyStatus() {
	return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
	this.notifyStatus = notifyStatus;
    }

    public Boolean isManNotify() {
	if (notifyStatus == null) {
	    return false;
	}
	return notifyStatus != Constants.BizOrder.NOTIFY_NORMAL;
    }

    public Long getPayOrderId() {
	return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
	this.payOrderId = payOrderId;
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

    public Integer getAmt() {
	return amt;
    }

    public void setAmt(Integer amt) {
	this.amt = amt;
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

    public Integer getItemCategoryId() {
	return itemCategoryId;
    }

    public void setItemCategoryId(Integer itemCategoryId) {
	this.itemCategoryId = itemCategoryId;
    }

    public String getItemUid() {
	return itemUid;
    }

    public void setItemUid(String itemUid) {
	this.itemUid = itemUid;
    }

    public String getItemCard() {
	return itemCard;
    }

    public void setItemCard(String itemCard) {
	this.itemCard = itemCard;
    }

    public String getItemPosId() {
	return itemPosId;
    }

    public void setItemPosId(String itemPosId) {
	this.itemPosId = itemPosId;
    }

    public String getItemPcId() {
	return itemPcId;
    }

    public void setItemPcId(String itemPcId) {
	this.itemPcId = itemPcId;
    }

    public String getItemCardPwd() {
	return itemCardPwd;
    }

    public void setItemCardPwd(String itemCardPwd) {
	this.itemCardPwd = itemCardPwd;
    }

    public Long getItemSupplyId() {
	return itemSupplyId;
    }

    public void setItemSupplyId(Long itemSupplyId) {
	this.itemSupplyId = itemSupplyId;
    }

    public Integer getItemSize() {
	return itemSize;
    }

    public void setItemSize(Integer itemSize) {
	this.itemSize = itemSize;
    }

    public Long getStockId() {
	return stockId;
    }

    public void setStockId(Long stockId) {
	this.stockId = stockId;
    }

    public Integer getChannel() {
	return channel;
    }

    public void setChannel(Integer channel) {
	this.channel = channel;
    }

    public String getUpstreamId() {
	return upstreamId;
    }

    public void setUpstreamId(String upstreamId) {
	this.upstreamId = upstreamId;
    }

    public String getUpstreamName() {
	return upstreamName;
    }

    public void setUpstreamName(String upstreamName) {
	this.upstreamName = upstreamName;
    }

    public String getUpstreamSerialno() {
	return upstreamSerialno;
    }

    public void setUpstreamSerialno(String upstreamSerialno) {
	this.upstreamSerialno = upstreamSerialno;
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

    public String getDownstreamId() {
	return downstreamId;
    }

    public void setDownstreamId(String downstreamId) {
	this.downstreamId = downstreamId;
    }

    public String getDownstreamName() {
	return downstreamName;
    }

    public void setDownstreamName(String downstreamName) {
	this.downstreamName = downstreamName;
    }

    public Date getDownstreamDate() {
	return downstreamDate;
    }

    public void setDownstreamDate(Date downstreamDate) {
	this.downstreamDate = downstreamDate;
    }

    public String getDownstreamSerialno() {
	return downstreamSerialno;
    }

    public void setDownstreamSerialno(String downstreamSerialno) {
	this.downstreamSerialno = downstreamSerialno;
    }

    public String getDownstreamNotes() {
	return downstreamNotes;
    }

    public void setDownstreamNotes(String downstreamNotes) {
	this.downstreamNotes = downstreamNotes;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }

    public String getUidAreaInfo() {
	return uidAreaInfo;
    }

    public void setUidAreaInfo(String uidAreaInfo) {
	this.uidAreaInfo = uidAreaInfo;
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

    public Integer getCostTime() {
	return costTime;
    }

    public void setCostTime(Integer costTime) {
	this.costTime = costTime;
    }

    public Integer getSupplyType() {
	return supplyType;
    }

    public void setSupplyType(Integer supplyType) {
	this.supplyType = supplyType;
    }

    public Integer getDownstreamSupplyWay() {
	return downstreamSupplyWay;
    }

    public void setDownstreamSupplyWay(Integer downstreamSupplyWay) {
	this.downstreamSupplyWay = downstreamSupplyWay;
    }

    public Integer getItemCostPrice() {
	return itemCostPrice;
    }

    public void setItemCostPrice(Integer itemCostPrice) {
	this.itemCostPrice = itemCostPrice;
    }

    public Integer getCarrierType() {
	return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
	this.carrierType = carrierType;
    }

    public String getProvinceCode() {
	return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
    }

    public Integer getActualCost() {
	return actualCost;
    }

    public void setActualCost(Integer actualCost) {
	this.actualCost = actualCost;
    }

    public Integer getCombineType() {
	return combineType;
    }

    public void setCombineType(Integer combineType) {
	this.combineType = combineType;
    }

    public Integer getCombineCount() {
	return combineCount;
    }

    public void setCombineCount(Integer combineCount) {
	this.combineCount = combineCount;
    }

    public Integer getManualType() {
	return manualType;
    }

    public void setManualType(Integer manualType) {
	this.manualType = manualType;
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
	} else if (Constants.BizOrder.STATUS_LOCK == status) {
	    result = Constants.BizOrder.STATUS_LOCK_DESC;
	} else if (Constants.BizOrder.STATUS_PENDING == status) {
	    result = Constants.BizOrder.STATUS_PENDING_DESC;
	} else if (Constants.BizOrder.STATUS_UNCONFIRMED == status) {
	    result = Constants.BizOrder.STATUS_UNCONFIRMED_DESC;
	} else if (Constants.BizOrder.STATUS_EXCEPTION == status) {
	    result = Constants.BizOrder.STATUS_EXCEPTION_DESC;
	}
	return result;
    }

    public String getNotifyStatusDesc() {
	String result = null;
	if (notifyStatus == null) {
	    return null;
	}

	if (Constants.BizOrder.NOTIFY_INIT == notifyStatus) {
	    result = Constants.BizOrder.NOTIFY_INIT_DESC;
	} else if (Constants.BizOrder.NOTIFY_CHARGING == notifyStatus) {
	    result = Constants.BizOrder.NOTIFY_CHARGING_DESC;
	} else if (Constants.BizOrder.NOTIFY_SUCCESS == notifyStatus) {
	    result = Constants.BizOrder.NOTIFY_SUCCESS_DESC;
	} else if (Constants.BizOrder.NOTIFY_FAILED == notifyStatus) {
	    result = Constants.BizOrder.NOTIFY_FAILED_DESC;
	} else if (Constants.BizOrder.NOTIFY_NORMAL == notifyStatus) {
	    result = Constants.BizOrder.NOTIFY_NORMAL_DESC;
	} else if (Constants.BizOrder.NOTIFY_UNKNOWN == notifyStatus) {
	    return Constants.BizOrder.NOTIFY_UNKNOWN_DESC;
	}
	return result;
    }

    public String getAmountDesc() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return result.toString();
    }

    public double getAmountDouble() {
	Double result = BigDecimalUtils.doubleDiveid(amount + "");
	return result;
    }

    public String getAmountDummyDesc() {
	if (amountDummy == null) {
	    return getAmountDesc();
	}
	Double result = BigDecimalUtils.doubleDiveid(amountDummy + "");
	return result.toString();
    }

    public double getAmountDummyDouble() {
	if (amountDummy == null) {
	    return getAmountDouble();
	}
	Double result = BigDecimalUtils.doubleDiveid(amountDummy + "");
	return result;
    }

    public double getItemFacePriceDouble() {
	Double result = BigDecimalUtils.doubleDiveid(itemFacePrice + "");
	return result;
    }

    public boolean canDeal() {
	if (status == null || gmtCreate == null) {
	    return false;
	}

	Long now = System.currentTimeMillis();
	return isManNotify() && Constants.BizOrder.STATUS_PENDING == status && (now - gmtCreate.getTime() > 60 * 1000 * 1);
    }

    /**
     * 能否人工取消
     * 
     * @return
     */
    public boolean canManCancel() {
	if (status == null) {
	    return false;
	}
	return isManCharge()
		&& (status == Constants.BizOrder.STATUS_CHARGING || status == Constants.BizOrder.STATUS_LOCK || Constants.BizOrder.STATUS_UNCONFIRMED == status);
    }

    /**
     * 能否人工锁定
     * 
     * @return
     */
    public boolean canManLock() {
	if (status == null) {
	    return false;
	}
	return isManCharge() && status == Constants.BizOrder.STATUS_CHARGING;
    }

    /**
     * 是否能人工解锁
     * 
     * @return
     */
    public boolean canManUnLock() {
	if (status == null) {
	    return false;
	}
	return isManCharge() && status == Constants.BizOrder.STATUS_LOCK;
    }

    /**
     * 是否能人工充值
     * 
     * @return
     */
    public boolean canManConfirm() {
	if (status == null) {
	    return false;
	}
	return isManCharge() && (status == Constants.BizOrder.STATUS_LOCK || Constants.BizOrder.STATUS_UNCONFIRMED == status);
    }

    /**
     * 是否能人工充值
     * 
     * @return
     */
    public boolean canManUnConfirm() {
	if (status == null) {
	    return false;
	}
	return isManCharge() && (status == Constants.BizOrder.STATUS_LOCK);
    }

    public boolean canAdjust() {
	if (status == null) {
	    return false;
	}
	return Constants.BizOrder.STATUS_SUCCESS == status || Constants.BizOrder.STATUS_INIT == status;
    }

    public boolean canCallback() {
	if (status == null) {
	    return false;
	}

	if (notifyStatus == null || Constants.BizOrder.NOTIFY_NORMAL == notifyStatus) {
	    return Constants.BizOrder.STATUS_SUCCESS == status || Constants.BizOrder.STATUS_FAILED == status;
	} else {
	    return Constants.BizOrder.STATUS_SUCCESS == status
		    || (Constants.BizOrder.STATUS_FAILED == status && Constants.BizOrder.NOTIFY_FAILED == notifyStatus);
	}
    }

    public boolean canNotify() {
	if (status == null) {
	    return false;
	}

	Long now = System.currentTimeMillis();
	return isManNotify()
		&& (Constants.BizOrder.STATUS_CHARGING == status || Constants.BizOrder.STATUS_PENDING == status
			|| Constants.BizOrder.STATUS_UNCONFIRMED == status || (Constants.BizOrder.STATUS_FAILED == status && Constants.BizOrder.NOTIFY_SUCCESS == notifyStatus))
		&& (now - gmtCreate.getTime() > 60 * 1000 * 1);
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return Constants.BizOrder.STATUS_UNCONFIRMED == status || Constants.BizOrder.STATUS_LOCK == status
		|| Constants.BizOrder.STATUS_FAILED == status || Constants.BizOrder.STATUS_PENDING == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.BizOrder.STATUS_SUCCESS == status;
    }

    public boolean notifyRed() {
	if (notifyStatus == null) {
	    return false;
	}
	return Constants.BizOrder.NOTIFY_FAILED == notifyStatus;
    }

    public boolean notifyGreen() {
	if (notifyStatus == null) {
	    return false;
	}
	return Constants.BizOrder.NOTIFY_SUCCESS == notifyStatus;
    }

    /**
     * 是不是人工充值业务
     * 
     * @return
     */
    public boolean isManCharge() {
	// 目的暂时不做人工业务了。 这里做 false处理
	return false;
	// if (supplyType == null) {
	// return false;
	// }
	// return supplyType == Constants.ItemSupply.TYPE_MAN;
    }

    public Boolean isSupplyFromStock() {
	if (supplyType == null) {
	    return null;
	}
	return supplyType == Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE || supplyType == Constants.ItemSupply.TYPE_CARD;
    }

    public Boolean isDirectCharge() {
	if (supplyType == null) {
	    return null;
	}
	return supplyType == Constants.ItemSupply.TYPE_DIRECT_CHARGE;
    }

    public Boolean isCardCharge() {
	if (supplyType == null) {
	    return null;
	}
	return supplyType == Constants.ItemSupply.TYPE_CARD_CHARGE;
    }

    public Boolean isCardForwardCharge() {
	if (supplyType == null) {
	    return null;
	}
	return supplyType == Constants.ItemSupply.TYPE_CARD_FORWARD_CHARGE;
    }

    public boolean isSyncSupply() {
	if (downstreamSupplyWay == null) {
	    return false;
	}
	return downstreamSupplyWay == Constants.BizOrder.DOWNSTREAM_SUPPLY_WAY_SYNC;
    }

    public String getCarrierDesc() {
	String result = null;
	if (carrierType == null) {
	    return result;
	}

	if (Constants.Item.CARRIER_TYPE_MOBILE == carrierType) {
	    result = Constants.Item.CARRIER_TYPE_MOBILE_DESC;
	} else if (Constants.Item.CARRIER_TYPE_TELECOM == carrierType) {
	    result = Constants.Item.CARRIER_TYPE_TELECOM_DESC;
	} else if (Constants.Item.CARRIER_TYPE_UNICOM == carrierType) {
	    result = Constants.Item.CARRIER_TYPE_UNICOM_DESC;
	} else if (Constants.Item.CARRIER_TYPE_OTHER == carrierType) {
	    result = Constants.Item.CARRIER_TYPE_OTHER_DESC;
	}
	return result;
    }

    public String getRepeatTypeDesc() {
	String result = null;
	if (repeatType == null) {
	    return result;
	}

	if (repeatType == Constants.BizOrder.REPEAT_TYPE_YES) {
	    result = Constants.BizOrder.REPEAT_TYPE_YES_DESC;
	} else if (repeatType == Constants.BizOrder.REPEAT_TYPE_NO) {
	    result = Constants.BizOrder.REPEAT_TYPE_NO_DESC;
	}
	return result;
    }

    public String getManualRepeatTypeDesc() {
	String result = null;
	if (manualType == null) {
	    return result;
	}

	if (manualType == Constants.BizOrder.MANUAL_TYPE_YES) {
	    result = Constants.BizOrder.MANUAL_TYPE_YES_DESC;
	} else if (manualType == Constants.BizOrder.MANUAL_TYPE_NO) {
	    result = Constants.BizOrder.MANUAL_TYPE_NO_DESC;
	}
	return result;
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

    public String getCostTimeDesc() {
	if (costTime == null) {
	    return null;
	}
	return DateTool.secondToDate(costTime);
    }

    // 临时使用itemSize
    public String getNotifyTimeDesc() {
	if (itemSize == null || itemSize == 0) {
	    return getCostTimeDesc();
	} else {
	    return DateTool.secondToDate(itemSize);
	}
    }

    public Double getItemCostPriceDesc() {
	if (itemCostPrice == null) {
	    return null;
	}
	return BigDecimalUtils.doubleDiveid(itemCostPrice + "");
    }

    public Double getActualCostDesc() {
	if (actualCost == null) {
	    return null;
	}
	return BigDecimalUtils.doubleDiveid(actualCost + "");
    }

    public Integer getRepeatType() {
	return repeatType;
    }

    public void setRepeatType(Integer repeatType) {
	this.repeatType = repeatType;
    }

    public boolean isRepeat() {
	if (repeatType == null) {
	    return false;
	}
	return Constants.BizOrder.REPEAT_TYPE_YES == repeatType;
    }

    public Integer getSupplyCount() {
	return supplyCount;
    }

    public void setSupplyCount(Integer supplyCount) {
	this.supplyCount = supplyCount;
    }

    public Integer getSupplyFilterIndex() {
	return supplyFilterIndex;
    }

    public void setSupplyFilterIndex(Integer supplyFilterIndex) {
	this.supplyFilterIndex = supplyFilterIndex;
    }

    public boolean isManualRepeatType() {
	if (manualType == null) {
	    return false;
	}
	return manualType == Constants.BizOrder.MANUAL_TYPE_YES;
    }

    public boolean isOver() {
	if (status == null) {
	    return false;
	}
	return status == Constants.BizOrder.STATUS_SUCCESS || status == Constants.BizOrder.STATUS_FAILED;
    }

    public boolean isCombine() {
	if (combineType == null || itemCategoryId == null || itemCategoryId == 0) {
	    return false;
	}
	return Constants.BizOrder.COMBINE_TYPE_YES == combineType;
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

    public String getCardId() {
	return cardId;
    }

    public void setCardId(String cardId) {
	this.cardId = cardId;
    }

    public String getCardPwd() {
	return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
	this.cardPwd = cardPwd;
    }

    public String getCardFinalDate() {
	if (cardFinalDate != null) {
	    if (cardFinalDate.contains("D")) {
		int pos = cardFinalDate.indexOf("D");
		Integer day = Integer.parseInt(cardFinalDate.substring(0, pos).trim());
		Date date = DateTool.afterDay(new Date(), day);
		return DateTool.parseDate8(date) + "000000";
	    } else if (cardFinalDate.contains("M")) {
		int pos = cardFinalDate.indexOf("M");
		Integer month = Integer.parseInt(cardFinalDate.substring(0, pos).trim());
		Date date = DateTool.afterMonth(new Date(), month);
		return DateTool.parseDate8(date) + "000000";
	    }
	}
	return cardFinalDate;
    }

    public void setCardFinalDate(String cardFinalDate) {
	if (cardFinalDate != null)
	    this.cardFinalDate = cardFinalDate.trim().toUpperCase();
    }

    public String getCardNote() {
	return cardNote;
    }

    public void setCardNote(String cardNote) {
	this.cardNote = cardNote;
    }
}