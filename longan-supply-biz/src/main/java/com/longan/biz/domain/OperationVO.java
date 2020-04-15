package com.longan.biz.domain;

import com.longan.biz.dataobject.UserInfo;

public class OperationVO {
    private String operationMemo;
    private UserInfo userInfo;

    public String getOperationMemo() {
	return operationMemo;
    }

    public void setOperationMemo(String operationMemo) {
	this.operationMemo = operationMemo;
    }

    public UserInfo getUserInfo() {
	return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
	this.userInfo = userInfo;
    }

}
