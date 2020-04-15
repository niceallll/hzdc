package com.longan.biz.domain;

import java.io.Serializable;

public class SubNotify implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sid;
    private String uid;
    private String text;
    private String extend;
    private String destId;

    public String getSid() {
	return sid;
    }

    public void setSid(String sid) {
	this.sid = sid;
    }

    public String getUid() {
	return uid;
    }

    public void setUid(String uid) {
	this.uid = uid;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public String getExtend() {
	return extend;
    }

    public void setExtend(String extend) {
	this.extend = extend;
    }

    public String getDestId() {
	return destId;
    }

    public void setDestId(String destId) {
	this.destId = destId;
    }
}
