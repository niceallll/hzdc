package com.longan.biz.pojo;

import com.longan.biz.utils.Utils;

public class PddBaseApi {
    private final static String client = Utils.getProperty("pdd.client");
    private final static String secret = Utils.getProperty("pdd.secret");
    private final static String dataType = "JSON";

    private String accessToken;
    private Long timestamp;
    private String sign;

    public PddBaseApi() {
	this.timestamp = System.currentTimeMillis() / 1000;
    }

    public String getAccessToken() {
	return accessToken;
    }

    public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
    }

    public Long getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Long timestamp) {
	this.timestamp = timestamp;
    }

    public String getSign() {
	return sign;
    }

    public void setSign(String sign) {
	this.sign = sign;
    }

    public String getClient() {
	return client;
    }

    public String getSecret() {
	return secret;
    }

    public String getDataType() {
	return dataType;
    }
}
