package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class TargetOrderForm {
    @NotBlank(message = "用户编号不能为空")
    private String userId;

    @NotBlank(message = "运行日期不能为空")
    private String orderDate;

    @NotBlank(message = "运行时间不能为空")
    private String orderTime;

    @RegExp(value = "^\\d{1,6}$", message = "移动10元数目必须是1到6位数字")
    private String item10;

    @RegExp(value = "^\\d{1,6}$", message = "移动20元数目必须是1到6位数字")
    private String item20;

    @RegExp(value = "^\\d{1,6}$", message = "移动30元数目必须是1到6位数字")
    private String item30;

    @RegExp(value = "^\\d{1,6}$", message = "移动50元数目必须是1到6位数字")
    private String item50;

    @RegExp(value = "^\\d{1,6}$", message = "移动100元数目必须是1到6位数字")
    private String item100;

    @RegExp(value = "^\\d{1,6}$", message = "移动200元数目必须是1到6位数字")
    private String item200;

    @RegExp(value = "^\\d{1,6}$", message = "移动300元数目必须是1到6位数字")
    private String item300;

    @RegExp(value = "^\\d{1,6}$", message = "移动500元数目必须是1到6位数字")
    private String item500;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getOrderDate() {
	return orderDate;
    }

    public void setOrderDate(String orderDate) {
	this.orderDate = orderDate;
    }

    public String getOrderTime() {
	return orderTime;
    }

    public void setOrderTime(String orderTime) {
	this.orderTime = orderTime;
    }

    public String getItem10() {
	return item10;
    }

    public void setItem10(String item10) {
	this.item10 = item10;
    }

    public String getItem20() {
	return item20;
    }

    public void setItem20(String item20) {
	this.item20 = item20;
    }

    public String getItem30() {
	return item30;
    }

    public void setItem30(String item30) {
	this.item30 = item30;
    }

    public String getItem50() {
	return item50;
    }

    public void setItem50(String item50) {
	this.item50 = item50;
    }

    public String getItem100() {
	return item100;
    }

    public void setItem100(String item100) {
	this.item100 = item100;
    }

    public String getItem200() {
	return item200;
    }

    public void setItem200(String item200) {
	this.item200 = item200;
    }

    public String getItem300() {
	return item300;
    }

    public void setItem300(String item300) {
	this.item300 = item300;
    }

    public String getItem500() {
	return item500;
    }

    public void setItem500(String item500) {
	this.item500 = item500;
    }
}
