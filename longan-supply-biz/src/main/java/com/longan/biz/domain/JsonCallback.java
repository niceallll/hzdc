package com.longan.biz.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonCallback implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String id;
    private Integer count;
    private String downstreamSerialno;
    private String sign;

    private List<SubCallback> data = null;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public Integer getCount() {
	return count;
    }

    public void setCount(Integer count) {
	this.count = count;
    }

    public String getDownstreamSerialno() {
	return downstreamSerialno;
    }

    public void setDownstreamSerialno(String downstreamSerialno) {
	this.downstreamSerialno = downstreamSerialno;
    }

    public String getSign() {
	return sign;
    }

    public void setSign(String sign) {
	this.sign = sign;
    }

    public void addData(SubCallback callback) {
	if (data == null) {
	    this.data = new ArrayList<SubCallback>();
	}
	data.add(callback);
    }

    public List<SubCallback> getData() {
	return data;
    }

    public void setData(List<SubCallback> data) {
	this.data = data;
    }
}
