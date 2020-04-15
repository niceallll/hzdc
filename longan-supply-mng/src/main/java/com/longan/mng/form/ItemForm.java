package com.longan.mng.form;

import java.util.List;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class ItemForm {
    private String id;
    @NotBlank(message = "商品名不能为空")
    private String itemName;
    @NotBlank(message = "商品类型不能为空")
    private String itemType;
    @RegExp(value = "([1-9][\\d]{0,6}|0)(\\.[\\d]{1,3})?", message = "金额格式不对")
    private String itemFacePrice;
    @RegExp(value = "(([1-9][\\d]{0,6}|0)(\\.[\\d]{1,3})?)?", message = "金额格式不对")
    private String itemSalesPrice;
    @RegExp(value = "(([1-9][\\d]{0,6}|0)(\\.[\\d]{1,3})?)?", message = "金额格式不对")
    private String itemSalesPrice2;
    @RegExp(value = "(([1-9][\\d]{0,6}|0)(\\.[\\d]{1,3})?)?", message = "金额格式不对")
    private String itemSalesPrice3;
    @RegExp(value = "(([1-9][\\d]{0,6}|0)(\\.[\\d]{1,3})?)?", message = "金额格式不对")
    private String itemSalesPrice4;
    @RegExp(value = "(([1-9][\\d]{0,6}|0)(\\.[\\d]{1,3})?)?", message = "金额格式不对")
    private String itemDummyPrice;
    private String bizId;
    @NotBlank(message = "运营商不能为空")
    private String carrierType;
    @NotBlank(message = "销售区域不能为空")
    private String salesAreaType;
    private List<String> salesAreaList;
    private String canSync;
    private String itemSize;
    private String itemRange;
    private String itemCategoryId;
    private String userId;
    private String itemUnit;// 临时使用itemUnit

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public String getItemType() {
	return itemType;
    }

    public void setItemType(String itemType) {
	this.itemType = itemType;
    }

    public String getItemFacePrice() {
	return itemFacePrice;
    }

    public void setItemFacePrice(String itemFacePrice) {
	this.itemFacePrice = itemFacePrice;
    }

    public String getItemSalesPrice() {
	return itemSalesPrice;
    }

    public void setItemSalesPrice(String itemSalesPrice) {
	this.itemSalesPrice = itemSalesPrice;
    }

    public String getItemSalesPrice2() {
	return itemSalesPrice2;
    }

    public void setItemSalesPrice2(String itemSalesPrice2) {
	this.itemSalesPrice2 = itemSalesPrice2;
    }

    public String getItemSalesPrice3() {
	return itemSalesPrice3;
    }

    public void setItemSalesPrice3(String itemSalesPrice3) {
	this.itemSalesPrice3 = itemSalesPrice3;
    }

    public String getItemSalesPrice4() {
	return itemSalesPrice4;
    }

    public void setItemSalesPrice4(String itemSalesPrice4) {
	this.itemSalesPrice4 = itemSalesPrice4;
    }

    public String getItemDummyPrice() {
	return itemDummyPrice;
    }

    public void setItemDummyPrice(String itemDummyPrice) {
	this.itemDummyPrice = itemDummyPrice;
    }

    public String getBizId() {
	return bizId;
    }

    public void setBizId(String bizId) {
	this.bizId = bizId;
    }

    public String getCarrierType() {
	return carrierType;
    }

    public void setCarrierType(String carrierType) {
	this.carrierType = carrierType;
    }

    public String getSalesAreaType() {
	return salesAreaType;
    }

    public void setSalesAreaType(String salesAreaType) {
	this.salesAreaType = salesAreaType;
    }

    public List<String> getSalesAreaList() {
	return salesAreaList;
    }

    public void setSalesAreaList(List<String> salesAreaList) {
	this.salesAreaList = salesAreaList;
    }

    public String getCanSync() {
	return canSync;
    }

    public void setCanSync(String canSync) {
	this.canSync = canSync;
    }

    public String getItemSize() {
	return itemSize;
    }

    public void setItemSize(String itemSize) {
	this.itemSize = itemSize;
    }

    public String getItemRange() {
	return itemRange;
    }

    public void setItemRange(String itemRange) {
	this.itemRange = itemRange;
    }

    public String getItemCategoryId() {
	return itemCategoryId;
    }

    public void setItemCategoryId(String itemCategoryId) {
	this.itemCategoryId = itemCategoryId;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getItemUnit() {
	return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
	this.itemUnit = itemUnit;
    }
}
