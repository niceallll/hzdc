package com.longan.mng.form;

import java.util.List;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class ItemSupplyForm {
    private String id;
    @NotBlank(message = "供货商不能为空")
    private String supplyTraderId;

    @NotBlank(message = "商品编号不能为空")
    private String itemId;

    @NotBlank(message = "业务编号不能为空")
    private String bizId;

    @RegExp(value = "(([1-9][\\d]{0,6}|0)(\\.[\\d]{1,3})?)?", message = "金额格式不对")
    private String itemCostPrice;

    @RegExp(value = "[\\d]{1,2}", message = "优先级必须是2位正整数")
    private String priority = "10";

    private Boolean supplyWay;
    private String warnQuantity;
    private String supplyProductCode;

    private String lossType;
    @RegExp(value = "([1-9][\\d]{0,8})?", message = "损失笔数应为数字")
    private String lossTime;

    private String supplyAreaType;
    private List<String> supplyAreaList;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(String supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public String getItemId() {
	return itemId;
    }

    public void setItemId(String itemId) {
	this.itemId = itemId;
    }

    public String getBizId() {
	return bizId;
    }

    public void setBizId(String bizId) {
	this.bizId = bizId;
    }

    public String getItemCostPrice() {
	if (itemCostPrice == null) {
	    return null;
	}
	return itemCostPrice.trim();
    }

    public void setItemCostPrice(String itemCostPrice) {
	this.itemCostPrice = itemCostPrice;
    }

    public String getPriority() {
	if (priority == null) {
	    return null;
	}
	return priority.trim();
    }

    public void setPriority(String priority) {
	this.priority = priority;
    }

    public Boolean getSupplyWay() {
	return supplyWay;
    }

    public void setSupplyWay(Boolean supplyWay) {
	this.supplyWay = supplyWay;
    }

    public String getWarnQuantity() {
	return warnQuantity;
    }

    public void setWarnQuantity(String warnQuantity) {
	this.warnQuantity = warnQuantity;
    }

    public String getSupplyProductCode() {
	return supplyProductCode;
    }

    public void setSupplyProductCode(String supplyProductCode) {
	this.supplyProductCode = supplyProductCode;
    }

    public String getLossType() {
	return lossType;
    }

    public void setLossType(String lossType) {
	this.lossType = lossType;
    }

    public String getLossTime() {
	return lossTime;
    }

    public void setLossTime(String lossTime) {
	this.lossTime = lossTime;
    }

    public String getSupplyAreaType() {
	return supplyAreaType;
    }

    public void setSupplyAreaType(String supplyAreaType) {
	this.supplyAreaType = supplyAreaType;
    }

    public List<String> getSupplyAreaList() {
	return supplyAreaList;
    }

    public void setSupplyAreaList(List<String> supplyAreaList) {
	this.supplyAreaList = supplyAreaList;
    }
}
