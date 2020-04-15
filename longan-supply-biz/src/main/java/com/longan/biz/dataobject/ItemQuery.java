package com.longan.biz.dataobject;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.BigDecimalUtils;

import java.util.List;

public class ItemQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private Long id;
    private String itemName;
    private Integer bizId;
    private Integer carrierType;
    private Integer itemType;
    private Integer itemFacePrice;
    private String salesArea;
    private String requesytType;
    private List<String> salesAreas;

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

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

    public Integer getCarrierType() {
	return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
	this.carrierType = carrierType;
    }

    public Integer getItemType() {
	return itemType;
    }

    public void setItemType(Integer itemType) {
	this.itemType = itemType;
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

    public String getPriceDesc(Integer price) {
	if (price == null) {
	    return null;
	}
	Double result = BigDecimalUtils.doubleDiveid(price + "");
	return result.toString();
    }

    public String getRequesytType() {
	return requesytType;
    }

    public void setRequesytType(String requesytType) {
	this.requesytType = requesytType;
    }

    public List<String> getSalesAreas() {
        return salesAreas;
    }

    public void setSalesAreas(List<String> salesAreas) {
        this.salesAreas = salesAreas;
    }
}
