package com.longan.biz.dataobject;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class Stock {
    private Long id;
    private String itemName;
    private Long itemSupplyId;
    private Integer itemId;
    private Integer itemFacePrice;
    private Integer itemCostPrice;
    private String cardSerialNo;
    private String cardId;
    private String cardPwd;
    private String cardFinalDate;
    private String cardNote;
    private Integer status;
    private Long inSerialno;
    private Date gmtCreate;
    private Date gmtModify;
    private Long bizOrderId;
    private Date outTime;
    private String errorInfo;
    private Integer bizId;
    private Long supplyTraderId;
    private Long outSerialno;
    private Long supplyOrderId;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public Long getItemSupplyId() {
	return itemSupplyId;
    }

    public void setItemSupplyId(Long itemSupplyId) {
	this.itemSupplyId = itemSupplyId;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
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

    public String getCardSerialNo() {
	return cardSerialNo;
    }

    public void setCardSerialNo(String cardSerialNo) {
	this.cardSerialNo = cardSerialNo;
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
	return cardFinalDate;
    }

    public void setCardFinalDate(String cardFinalDate) {
	this.cardFinalDate = cardFinalDate;
    }

    public String getCardNote() {
	return cardNote;
    }

    public void setCardNote(String cardNote) {
	this.cardNote = cardNote;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Long getInSerialno() {
	return inSerialno;
    }

    public void setInSerialno(Long inSerialno) {
	this.inSerialno = inSerialno;
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

    public Date getOutTime() {
	return outTime;
    }

    public void setOutTime(Date outTime) {
	this.outTime = outTime;
    }

    public String getErrorInfo() {
	return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
	this.errorInfo = errorInfo;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public Long getOutSerialno() {
	return outSerialno;
    }

    public void setOutSerialno(Long outSerialno) {
	this.outSerialno = outSerialno;
    }

    public Long getSupplyOrderId() {
	return supplyOrderId;
    }

    public void setSupplyOrderId(Long supplyOrderId) {
	this.supplyOrderId = supplyOrderId;
    }

    public String getItemCostPriceDesc() {
	if (itemCostPrice == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(itemCostPrice + "");
	return result.toString();
    }

    public String getItemFacePriceDesc() {
	if (itemFacePrice == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(itemFacePrice + "");
	return result.intValue() + "";
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}
	if (Constants.Stock.STATUS_INIT == status) {
	    result = Constants.Stock.STATUS_INIT_DESC;
	} else if (Constants.Stock.STATUS_NORMAL == status) {
	    result = Constants.Stock.STATUS_NORMAL_DESC;
	} else if (Constants.Stock.STATUS_DELIVERY == status) {
	    result = Constants.Stock.STATUS_DELIVERY_DESC;
	} else if (Constants.Stock.STATUS_INV_ALLOCATED == status) {
	    result = Constants.Stock.STATUS_INV_ALLOCATED_DESC;
	} else if (Constants.Stock.STATUS_EXCEPTION == status) {
	    result = Constants.Stock.STATUS_EXCEPTION_DESC;
	} else if (Constants.Stock.STATUS_INVALID == status) {
	    result = Constants.Stock.STATUS_INVALID_DESC;
	}
	return result;
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return Constants.Stock.STATUS_EXCEPTION == status || Constants.Stock.STATUS_INV_ALLOCATED == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.Stock.STATUS_NORMAL == status;
    }

    public boolean showBlue() {
	if (status == null) {
	    return false;
	}
	return Constants.Stock.STATUS_DELIVERY == status;
    }

    public String getCardPwdDesc() {
	if (StringUtils.isEmpty(cardPwd)) {
	    return null;
	}
	return replacePwd(cardPwd);
    }

    public String replacePwd(String str) {
	if (str.length() <= 2) {
	    return str;
	}
	StringBuffer sb = new StringBuffer();
	try {
	    String str1 = str.substring(0, 3);
	    String str2 = "";
	    if (str.length() > 6) {
		str2 = str.substring(6, str.length());
	    }
	    sb.append(str1);
	    sb.append("***").append(str2);
	} catch (Exception e) {
	    return null;
	}
	return sb.toString();
    }

    public boolean canReturnToStorage() {
	return status == Constants.Stock.STATUS_INV_ALLOCATED || status == Constants.Stock.STATUS_EXCEPTION
		|| status == Constants.Stock.STATUS_DELIVERY;
    }

    public boolean canDeliveryFromStorage() {
	return status == Constants.Stock.STATUS_INV_ALLOCATED;
    }

    public boolean canSetStorageInvalid() {
	return status == Constants.Stock.STATUS_INV_ALLOCATED || status == Constants.Stock.STATUS_EXCEPTION
		|| status == Constants.Stock.STATUS_NORMAL;
    }
}