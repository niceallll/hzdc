package com.longan.biz.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({ "success" })
public class JsonResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String desc;

    public boolean isSuccess() {
	return "00".equals(code);
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }
}
