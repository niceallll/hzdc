package com.longan.biz.domain;

import java.io.Serializable;

public class SubCallback implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private String statusDesc;
    private Integer count;
    private String uid;
    private String sid;

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public String getStatusDesc() {
	return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
	this.statusDesc = statusDesc;
    }

    public Integer getCount() {
	return count;
    }

    public void setCount(Integer count) {
	this.count = count;
    }

    public String getUid() {
	return uid;
    }

    public void setUid(String uid) {
	this.uid = uid;
    }

    public String getSid() {
	return sid;
    }

    public void setSid(String sid) {
	this.sid = sid;
    }
}
