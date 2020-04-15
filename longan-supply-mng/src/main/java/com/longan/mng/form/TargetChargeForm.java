package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class TargetChargeForm {
    @NotBlank(message = "用户编号不能为空")
    private String userId;

    @NotBlank(message = "充值日期不能为空")
    private String chargeDate;

    @NotBlank(message = "充值时间不能为空")
    private String chargeTime;

    @RegExp(value = "[1-9]\\d+", message = "金额格式不对")
    @Length(max = 11, message = "金额超过最大长度")
    private String chargeAmount;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getChargeDate() {
	return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
	this.chargeDate = chargeDate;
    }

    public String getChargeTime() {
	return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
	this.chargeTime = chargeTime;
    }

    public String getChargeAmount() {
	return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
	this.chargeAmount = chargeAmount;
    }
}
