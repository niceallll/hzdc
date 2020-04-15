package com.longan.biz.pojo;

import com.longan.biz.utils.Utils;

public class PddBaseToken {
    private final static String client = Utils.getProperty("pdd.client");
    private final static String secret = Utils.getProperty("pdd.secret");

    private String client_id;
    private String client_secret;
    private String grant_type;

    public PddBaseToken() {
	this.client_id = client;
	this.client_secret = secret;
    }

    public String getClient_id() {
	return client_id;
    }

    public void setClient_id(String client_id) {
	this.client_id = client_id;
    }

    public String getClient_secret() {
	return client_secret;
    }

    public void setClient_secret(String client_secret) {
	this.client_secret = client_secret;
    }

    public String getGrant_type() {
	return grant_type;
    }

    public void setGrant_type(String grant_type) {
	this.grant_type = grant_type;
    }
}
