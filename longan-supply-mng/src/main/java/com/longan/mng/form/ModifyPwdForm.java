package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

public class ModifyPwdForm {
    private String userId;

    @NotNull(message = "旧密码不能为空")
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;

    @NotNull(message = "新密码不能为空")
    @NotBlank(message = "新密码不能为空")
    private String newPwd;

    public String getOldPwd() {
	return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
	this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
	return newPwd;
    }

    public void setNewPwd(String newPwd) {
	this.newPwd = newPwd;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

}
