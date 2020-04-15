package com.longan.mng.form;

import org.apache.commons.lang.StringUtils;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class ItemPriceListForm extends BaseTaskForm {
    private String itemSupplyIdList;
    @RegExp(value = "(([1-9][\\d]{0,2}|0)(\\.[\\d]{1,2})?)?", message = "折扣格式不对")
    private String itemCostPriceDiscount;
    @RegExp(value = "(([1-9][\\d]{0,2}|0)(\\.[\\d]{1,2})?)?", message = "折扣格式不对")
    private String itemSalesPriceDiscount;
    @RegExp(value = "(([1-9][\\d]{0,2}|0)(\\.[\\d]{1,2})?)?", message = "折扣格式不对")
    private String itemSalesPrice2Discount;
    @RegExp(value = "(([1-9][\\d]{0,2}|0)(\\.[\\d]{1,2})?)?", message = "折扣格式不对")
    private String itemSalesPrice3Discount;
    @RegExp(value = "(([1-9][\\d]{0,2}|0)(\\.[\\d]{1,2})?)?", message = "折扣格式不对")
    private String itemSalesPrice4Discount;
    @RegExp(value = "(([1-9][\\d]{0,2}|0)(\\.[\\d]{1,2})?)?", message = "折扣格式不对")
    private String itemDummyPriceDiscount;

    public String getItemCostPriceDiscount() {
	return itemCostPriceDiscount;
    }

    public void setItemCostPriceDiscount(String itemCostPriceDiscount) {
	this.itemCostPriceDiscount = itemCostPriceDiscount;
    }

    public String getItemSalesPriceDiscount() {
	return itemSalesPriceDiscount;
    }

    public void setItemSalesPriceDiscount(String itemSalesPriceDiscount) {
	this.itemSalesPriceDiscount = itemSalesPriceDiscount;
    }

    public String getItemSalesPrice2Discount() {
	return itemSalesPrice2Discount;
    }

    public void setItemSalesPrice2Discount(String itemSalesPrice2Discount) {
	this.itemSalesPrice2Discount = itemSalesPrice2Discount;
    }

    public String getItemSalesPrice3Discount() {
	return itemSalesPrice3Discount;
    }

    public void setItemSalesPrice3Discount(String itemSalesPrice3Discount) {
	this.itemSalesPrice3Discount = itemSalesPrice3Discount;
    }

    public String getItemSalesPrice4Discount() {
	return itemSalesPrice4Discount;
    }

    public void setItemSalesPrice4Discount(String itemSalesPrice4Discount) {
	this.itemSalesPrice4Discount = itemSalesPrice4Discount;
    }

    public String getItemDummyPriceDiscount() {
	return itemDummyPriceDiscount;
    }

    public void setItemDummyPriceDiscount(String itemDummyPriceDiscount) {
	this.itemDummyPriceDiscount = itemDummyPriceDiscount;
    }

    public String getItemSupplyIdList() {
	return itemSupplyIdList;
    }

    public void setItemSupplyIdList(String itemSupplyIdList) {
	this.itemSupplyIdList = itemSupplyIdList;
    }

    @Override
    public String toString() {
	String result = "[ID :" + getItemSupplyIdList() + "] ";
	if (StringUtils.isNotBlank(itemCostPriceDiscount)) {
	    result = result + "itemCostPriceDiscount: -> " + itemCostPriceDiscount + ";";
	}
	if (StringUtils.isNotBlank(itemSalesPriceDiscount)) {
	    result = result + "itemSalesPrice2Discount: -> " + itemSalesPrice2Discount + ";";
	}
	if (StringUtils.isNotBlank(itemSalesPrice2Discount)) {
	    result = result + "itemSalesPriceDiscount: -> " + itemSalesPrice2Discount + ";";
	}
	if (StringUtils.isNotBlank(itemSalesPrice3Discount)) {
	    result = result + "itemSalesPrice3Discount: -> " + itemSalesPrice3Discount + ";";
	}
	if (StringUtils.isNotBlank(itemSalesPrice4Discount)) {
	    result = result + "itemSalesPrice4Discount: -> " + itemSalesPrice4Discount + ";";
	}
	if (StringUtils.isNotBlank(itemDummyPriceDiscount)) {
	    result = result + "itemDummyPriceDiscount: -> " + itemDummyPriceDiscount + ";";
	}
	return result;
    }
}
