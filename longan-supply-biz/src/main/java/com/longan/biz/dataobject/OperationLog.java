package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date gmtCreate;

    private Long userId;

    private String userName;

    private String moduleName;

    private Integer bizId;

    private Integer operType;

    private String description;

    private String loginIp;

    private String bizName;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getModuleName() {
	return moduleName;
    }

    public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Integer getOperType() {
	return operType;
    }

    public void setOperType(Integer operType) {
	this.operType = operType;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getLoginIp() {
	return loginIp;
    }

    public void setLoginIp(String loginIp) {
	this.loginIp = loginIp;
    }

    public String getBizName() {
	return bizName;
    }

    public void setBizName(String bizName) {
	this.bizName = bizName;
    }

}
