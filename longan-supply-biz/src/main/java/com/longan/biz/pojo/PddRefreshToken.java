package com.longan.biz.pojo;

public class PddRefreshToken extends PddBaseToken {
    private String refresh_token;

    public PddRefreshToken() {
	setGrant_type("refresh_token");
    }

    public String getRefresh_token() {
	return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
	this.refresh_token = refresh_token;
    }
}
