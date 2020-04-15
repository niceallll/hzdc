package com.longan.biz.dataobject;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.BigDecimalUtils;

public class ItemPriceQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer itemId;
    private Long supplyTraderId;
    private Integer bizId;
    private Integer carrierType;
    private String itemName;
    private Integer status;
    private Integer itemFacePrice;
    private String salesArea;
    private Integer exceType;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
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

    public Integer getCarrierType() {
	return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
	this.carrierType = carrierType;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getItemFacePrice() {
	return itemFacePrice;
    }

    public void setItemFacePrice(Integer itemFacePrice) {
	this.itemFacePrice = itemFacePrice;
    }

    public String getSalesArea() {
	return salesArea;
    }

    public void setSalesArea(String salesArea) {
	this.salesArea = salesArea;
    }

    public Integer getExceType() {
	return exceType;
    }

    public void setExceType(Integer exceType) {
	this.exceType = exceType;
    }

    public String getPriceDesc(Integer price) {
	if (price == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(price + "");
	return result.toString();
    }

}
