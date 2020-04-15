package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class CashOrderForm {
    @NotBlank(message = "用户编号不能为空")
    private String userId;

    @NotBlank(message = "账户编号不能为空")
    private String acctId;

    @RegExp(value = "[1-9]\\d+", message = "金额格式不对")
    @Length(max = 11, message = "金额超过最大长度")
    private String amount;

    @NotBlank(message = "登录名不能为空")
    private String loginId;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    private String memo;

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getLoginId() {
	return loginId;
    }

    public void setLoginId(String loginId) {
	this.loginId = loginId;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getAcctId() {
	return acctId;
    }

    public void setAcctId(String acctId) {
	this.acctId = acctId;
    }

    public String getAmount() {
	return amount;
    }

    public void setAmount(String amount) {
	this.amount = amount;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }

}
