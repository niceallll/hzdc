package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;

public class TaskQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String userName;
    private Integer bizId;
    private String moduleName;
    private Integer status;
    private Date startGmtCreate;
    private Date endGmtCreate;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public String getModuleName() {
	return moduleName;
    }

    public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Date getStartGmtCreate() {
	return startGmtCreate;
    }

    public void setStartGmtCreate(Date startGmtCreate) {
	this.startGmtCreate = startGmtCreate;
    }

    public Date getEndGmtCreate() {
	return endGmtCreate;
    }

    public void setEndGmtCreate(Date endGmtCreate) {
	this.endGmtCreate = endGmtCreate;
    }
}
