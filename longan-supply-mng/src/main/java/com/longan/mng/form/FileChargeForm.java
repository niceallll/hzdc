package com.longan.mng.form;

import org.apache.commons.lang.StringUtils;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class FileChargeForm {
    @NotNull(message = "充值账户不能为空")
    private Long userId;

    @NotBlank(message = "短信验证码不能为空")
    @NotNull(message = "短信验证码不能为空")
    @RegExp(value = "^\\d{6}$", message = "短信验证码必须是6位数字")
    private String smsCode;

    private String memo;

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public String getSmsCode() {
	return smsCode;
    }

    public void setSmsCode(String smsCode) {
	this.smsCode = smsCode;
    }

    public String getMemo() {
	if (StringUtils.isBlank(memo)) {
	    return "";
	}
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }
}
