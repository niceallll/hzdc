package com.longan.biz.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class SxydCacheToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date deadline;
    private String accessToken;
    private Integer expiresTime;

    public Date getDeadline() {
	return deadline;
    }

    public void setDeadline(Date deadline) {
	this.deadline = deadline;
    }

    public String getAccessToken() {
	return accessToken;
    }

    public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
    }

    public Integer getExpiresTime() {
	return expiresTime;
    }

    public void setExpiresTime(Integer expiresTime) {
	this.expiresTime = expiresTime;
    }

    public void setDeadline(Integer expiresTime) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.SECOND, expiresTime);
	this.deadline = cal.getTime();
    }
}
