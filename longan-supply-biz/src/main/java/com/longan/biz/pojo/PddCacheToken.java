package com.longan.biz.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class PddCacheToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date deadline;
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;

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

    public String getRefreshToken() {
	return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
	this.refreshToken = refreshToken;
    }

    public Integer getExpiresIn() {
	return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
	this.expiresIn = expiresIn;
    }

    public void setDeadline(Integer expiresTime) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.SECOND, expiresTime);
	this.deadline = cal.getTime();
    }
}
