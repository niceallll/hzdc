package com.longan.mng.api.request;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class CallbackBizOrderRequest extends MiniBizOrderRequest {
    @NotNull
    @NotBlank
    private String status;
    @NotNull
    @NotBlank
    private String phone;
    @NotNull
    @NotBlank
    private String amount;
    private String no;
    @NotNull
    @NotBlank
    @RegExp(value = "[\\d]{1,18}", message = "订单编号不对")
    private String bizOrderId;

    private String card;
    private String pwd;

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getAmount() {
	return amount;
    }

    public void setAmount(String amount) {
	this.amount = amount;
    }

    public String getNo() {
	return no;
    }

    public void setNo(String no) {
	this.no = no;
    }

    public String getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(String bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public String getCard() {
	return card;
    }

    public void setCard(String card) {
	this.card = card;
    }

    public String getPwd() {
	return pwd;
    }

    public void setPwd(String pwd) {
	this.pwd = pwd;
    }
}
