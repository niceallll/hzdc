package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

public class UpDownForm extends BaseTaskForm {
    private static final int DOWN = 0;
    private static final int UP = 1;

    private static final String DOWN_DESC = "下架";
    private static final String UP_DESC = "上架";

    @NotBlank(message = "编号不能为空")
    @NotNull(message = "编号不能为空")
    private String ids;

    private String names;

    private String url;
    private String requestType;
    private String type;
    private String moduleName;
    private String bizId;
    private String provinceCode ;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getRequestType() {
	return requestType;
    }

    public void setRequestType(String requestType) {
	this.requestType = requestType;
    }

    public String getIds() {
	return ids;
    }

    public void setIds(String ids) {
	this.ids = ids;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getModuleName() {
	return moduleName;
    }

    public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
    }

    public void setTypeDown() {
	this.type = DOWN + "";
    }

    public void setTypeOn() {
	this.type = UP + "";
    }

    public String getBizId() {
	return bizId;
    }

    public void setBizId(String bizId) {
	this.bizId = bizId;
    }

    public String getTypeDesc() {
	if ((DOWN + "").equals(type)) {
	    return DOWN_DESC;
	}

	if ((UP + "").equals(type)) {
	    return UP_DESC;
	}
	return "";
    }

    public String getNames() {
	return names;
    }

    public void setNames(String names) {
	this.names = names;
    }

    @Override
    public String toString() {
	String result = "[ID :" + ids + "] ";
	result = result + "requestType:" + requestType + ";";
	return result;
    }

}
