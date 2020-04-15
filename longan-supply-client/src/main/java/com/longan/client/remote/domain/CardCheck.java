package com.longan.client.remote.domain;

import java.io.Serializable;

public class CardCheck implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String desc;

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

    public String toString() {
	return "状态代码 ： " + code + "   描述 ：  " + desc;
    }
}
