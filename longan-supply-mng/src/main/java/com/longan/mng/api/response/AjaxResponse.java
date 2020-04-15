package com.longan.mng.api.response;

import net.sf.json.JSONObject;

public class AjaxResponse {
    private final static String SUCCESS = "success";
    private final static String ERROR = "error";

    private String status = ERROR;
    private String errorMsg;
    private String errorCode;
    private Object module;

    public void setSuccess() {
	status = SUCCESS;
    }

    public void setError() {
	status = ERROR;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getErrorMsg() {
	return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }

    public Object getModule() {
	return module;
    }

    public void setModule(Object module) {
	this.module = module;
    }

    @Override
    public String toString() {
	return JSONObject.fromObject(this).toString();
    }
}
