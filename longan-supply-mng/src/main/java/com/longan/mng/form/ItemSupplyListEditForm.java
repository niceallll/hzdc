package com.longan.mng.form;

import org.apache.commons.lang.StringUtils;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class ItemSupplyListEditForm {
    @NotBlank(message = "供货商品列表不能为空")
    private String ids;

    private String names;

    @NotBlank(message = "业务编号不能为空")
    private String bizId;

    private String itemCostPrice;

    @RegExp(value = "[\\d]{1,2}", message = "优先级必须是2位正整数")
    private String priority;

    private String warnQuantity;

    private String supplyProductCode;

    private String requestType;

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

    public String getIds() {
	return ids;
    }

    public void setIds(String ids) {
	this.ids = ids;
    }

    public String getRequestType() {
	return requestType;
    }

    public void setRequestType(String requestType) {
	this.requestType = requestType;
    }

    public String getNames() {
	return names;
    }

    public void setNames(String names) {
	this.names = names;
    }

    @Override
    public String toString() {
	String result = "[ID :" + ids + "] ";
	if (StringUtils.isNotBlank(priority)) {
	    result = result + "priority: -> " + priority + ";";
	}
	if (StringUtils.isNotBlank(supplyProductCode)) {
	    result = result + "supplyProductCode: -> s" + supplyProductCode + ";";
	}

	return result;
    }

}
