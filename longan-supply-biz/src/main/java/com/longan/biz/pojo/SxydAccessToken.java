package com.longan.biz.pojo;

import com.longan.biz.utils.Utils;

public class SxydAccessToken {
    private final static String user = Utils.getProperty("sxyd.user");
    private final static String client = Utils.getProperty("sxyd.client");
    private final static String secret = Utils.getProperty("sxyd.secret");

    private String userId;
    private String clientId;
    private String clientSecret;

    public SxydAccessToken() {
	this.userId = user;
	this.clientId = client;
	this.clientSecret = secret;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getClientId() {
	return clientId;
    }

    public void setClientId(String clientId) {
	this.clientId = clientId;
    }

    public String getClientSecret() {
	return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
	this.clientSecret = clientSecret;
    }
}
