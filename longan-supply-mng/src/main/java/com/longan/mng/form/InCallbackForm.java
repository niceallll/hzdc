package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class InCallbackForm {
    @NotBlank(message = "用户编号不能为空")
    private String userId;

    private String userName;
    private String compayInfo;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getCompayInfo() {
	return compayInfo;
    }

    public void setCompayInfo(String compayInfo) {
	this.compayInfo = compayInfo;
    }
}
