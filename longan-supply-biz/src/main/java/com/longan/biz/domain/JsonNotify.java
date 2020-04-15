package com.longan.biz.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonNotify implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String sign;

    private List<SubNotify> data = null;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getSign() {
	return sign;
    }

    public void setSign(String sign) {
	this.sign = sign;
    }

    public void addData(SubNotify notify) {
	if (data == null) {
	    this.data = new ArrayList<SubNotify>();
	}
	data.add(notify);
    }

    public List<SubNotify> getData() {
	return data;
    }

    public void setData(List<SubNotify> data) {
	this.data = data;
    }
}
