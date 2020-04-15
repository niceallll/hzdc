package com.longan.biz.dataobject;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.BigDecimalUtils;

public class ItemSupplyQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long supplyTraderId;
    private Integer bizId;
    private Integer itemId;
    private Integer status;
    private Integer lessQuantity;
    private Integer moreQuantity;
    private Integer quantity;
    private Integer itemType;
    private String itemName;
    private Integer supplyType;
    private Integer isAsyncSupply;
    private String requestType;
    private String salesArea;
    private Integer itemFacePrice;
    private String supplyArea;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getLessQuantity() {
	return lessQuantity;
    }

    public void setLessQuantity(Integer lessQuantity) {
	this.lessQuantity = lessQuantity;
    }

    public Integer getMoreQuantity() {
	return moreQuantity;
    }

    public void setMoreQuantity(Integer moreQuantity) {
	this.moreQuantity = moreQuantity;
    }

    public Integer getQuantity() {
	return quantity;
    }

    public void setQuantity(Integer quantity) {
	this.quantity = quantity;
    }

    public Integer getItemType() {
	return itemType;
    }

    public void setItemType(Integer itemType) {
	this.itemType = itemType;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public Integer getSupplyType() {
	return supplyType;
    }

    public void setSupplyType(Integer supplyType) {
	this.supplyType = supplyType;
    }

    public Integer getIsAsyncSupply() {
	return isAsyncSupply;
    }

    public void setIsAsyncSupply(Integer isAsyncSupply) {
	this.isAsyncSupply = isAsyncSupply;
    }

    public String getRequestType() {
	return requestType;
    }

    public void setRequestType(String requestType) {
	this.requestType = requestType;
    }

    public String getSalesArea() {
	return salesArea;
    }

    public void setSalesArea(String salesArea) {
	this.salesArea = salesArea;
    }

    public Integer getItemFacePrice() {
	return itemFacePrice;
    }

    public void setItemFacePrice(Integer itemFacePrice) {
	this.itemFacePrice = itemFacePrice;
    }

    public String getPriceDesc(Integer price) {
	if (price == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(price + "");
	return result.toString();
    }

    public String getSupplyArea() {
	return supplyArea;
    }

    public void setSupplyArea(String supplyArea) {
	this.supplyArea = supplyArea;
    }
}
