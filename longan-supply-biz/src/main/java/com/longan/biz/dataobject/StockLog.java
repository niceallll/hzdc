package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class StockLog {
    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Long supplyTraderId;
    private Integer bizId;
    private Long itemSupplyId;
    private Integer itemId;
    private String itemName;
    private Integer itemCostPrice;
    private Long operId;
    private String operName;
    private Integer stockCount;
    private Integer type;
    private String stockMemo;
    private String memo;
    private Integer status;

    private String cardFinalDate;
    private String cardNote;

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

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
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

    public Integer getItemCostPrice() {
	return itemCostPrice;
    }

    public void setItemCostPrice(Integer itemCostPrice) {
	this.itemCostPrice = itemCostPrice;
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

    public Integer getStockCount() {
	return stockCount;
    }

    public void setStockCount(Integer stockCount) {
	this.stockCount = stockCount;
    }

    public Integer getType() {
	return type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    public String getStockMemo() {
	return stockMemo;
    }

    public void setStockMemo(String stockMemo) {
	this.stockMemo = stockMemo;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public String getItemCostPriceDesc() {
	if (itemCostPrice == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(itemCostPrice + "");
	return result.toString();
    }

    public String getTypeDesc() {
	String result = null;
	if (type == null) {
	    return null;
	}
	if (Constants.StockLog.TYPE_IN == type) {
	    result = Constants.StockLog.TYPE_IN_DESC;
	} else if (Constants.StockLog.TYPE_OUT == type) {
	    result = Constants.StockLog.TYPE_OUT_DESC;
	}
	return result;
    }

    public String getStatusDesc() {
	String result = null;
	if (status == null || type == null) {
	    return null;
	}
	if (Constants.StockLog.STATUS_INIT == status) {
	    result = Constants.StockLog.STATUS_INIT_DESC;
	} else if (Constants.StockLog.STATUS_INVALID == status) {
	    result = Constants.StockLog.STATUS_INVALID_DESC;
	} else if (Constants.StockLog.STATUS_SUCCESS == status) {
	    result = Constants.StockLog.STATUS_SUCCESS_DESC;
	} else if (Constants.StockLog.STATUS_FAILED == status) {
	    result = Constants.StockLog.STATUS_FAILED_DESC;
	} else if (Constants.StockLog.STATUS_ACTIVATED == status) {
	    result = Constants.StockLog.STATUS_ACTIVATED_DESC;
	}
	return result;
    }

    public boolean canActivate() {
	if (status == null) {
	    return false;
	}
	return type == Constants.StockLog.TYPE_IN && (status == Constants.StockLog.STATUS_SUCCESS);
    }

    public boolean canInvalid() {
	if (status == null || type == null) {
	    return false;
	}
	return type == Constants.StockLog.TYPE_IN
		&& (status == Constants.StockLog.STATUS_ACTIVATED || status == Constants.StockLog.STATUS_SUCCESS);
    }

    public boolean canDownload() {
	if (status == null || type == null) {
	    return false;
	}
	return type == Constants.StockLog.TYPE_OUT && status == Constants.StockLog.STATUS_SUCCESS;
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return status == Constants.StockLog.STATUS_SUCCESS;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return status == Constants.StockLog.STATUS_ACTIVATED;
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
}