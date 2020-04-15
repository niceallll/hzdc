package com.longan.biz.domain;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isSuccess = false;
    private String resultCode;
    private String resultMsg;
    private T module;

    public synchronized static <T> Result<T> createResult() {
	return new Result<T>();
    }

    public boolean isSuccess() {
	return isSuccess;
    }

    public void setSuccess(boolean success) {
	isSuccess = success;
    }

    public T getModule() {
	return module;
    }

    public void setModule(T module) {
	this.module = module;
    }

    public String getResultCode() {
	return resultCode;
    }

    public void setResultCode(String resultCode) {
	this.resultCode = resultCode;
    }

    public String getResultMsg() {
	return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
	this.resultMsg = resultMsg;
    }
}
