package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

public class SmsNotify implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date gmtCreate;
    private Long userId;
    private String itemUid;
    private String text;
    private String extend;
    private String destId;
    private Long upstreamId;
    private String upstreamSerialno;

    private String upstreamName;

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

    public String getItemUid() {
	return itemUid;
    }

    public void setItemUid(String itemUid) {
	this.itemUid = itemUid;
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

    public Long getUpstreamId() {
	return upstreamId;
    }

    public void setUpstreamId(Long upstreamId) {
	this.upstreamId = upstreamId;
    }

    public String getUpstreamSerialno() {
	return upstreamSerialno;
    }

    public void setUpstreamSerialno(String upstreamSerialno) {
	this.upstreamSerialno = upstreamSerialno;
    }

    public String getUpstreamName() {
	return upstreamName;
    }

    public void setUpstreamName(String upstreamName) {
	this.upstreamName = upstreamName;
    }
}
