package com.longan.biz.pojo;

import com.longan.biz.utils.Utils;

public class PddAccessToken extends PddBaseToken {
    private final static String uri = Utils.getProperty("pdd.uri");

    private String redirect_uri;
    private String code;

    public PddAccessToken() {
	this.redirect_uri = uri;
	setGrant_type("authorization_code");
    }

    public String getRedirect_uri() {
	return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
	this.redirect_uri = redirect_uri;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }
}
