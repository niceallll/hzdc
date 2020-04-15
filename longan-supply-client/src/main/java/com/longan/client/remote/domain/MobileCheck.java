package com.longan.client.remote.domain;

import java.io.Serializable;

public class MobileCheck implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CHARGE_NO = 0;
    public static final int CHARGE_YES = 1;
    public static final int CHARGE_UNKNOWN = 2;

    private String mobile;
    private String msg;
    private Integer charge = CHARGE_UNKNOWN;

    public boolean disableCharge() {
	return charge == CHARGE_NO;
    }

    public boolean enableCharge() {
	return charge == CHARGE_YES;
    }

    public boolean unknownCharge() {
	return charge == CHARGE_UNKNOWN;
    }

    public String getMobile() {
	return mobile;
    }

    public void setMobile(String mobile) {
	this.mobile = mobile;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public Integer getCharge() {
	return charge;
    }

    public void setCharge(Integer charge) {
	this.charge = charge;
    }

    public String toString() {
	return "手机号：" + mobile + "，验证结果：" + msg;
    }
}
