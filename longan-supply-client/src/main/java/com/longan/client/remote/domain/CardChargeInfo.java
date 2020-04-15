package com.longan.client.remote.domain;

import java.io.Serializable;

public class CardChargeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String serviceNum;
    private String money;
    private String time;
    private String status;
    private String statusDesc;

    public String getServiceNum() {
	return serviceNum;
    }

    public void setServiceNum(String serviceNum) {
	this.serviceNum = serviceNum;
    }

    public String getMoney() {
	return money;
    }

    public void setMoney(String money) {
	this.money = money;
    }

    public String getTime() {
	return time;
    }

    public void setTime(String time) {
	this.time = time;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getStatusDesc() {
	return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
	this.statusDesc = statusDesc;
    }

    public String toString() {
	return "充值手机号 ：" + serviceNum + "  金额 ：" + money + "  时间：" + time + "  状态码：" + status + "  描述：" + statusDesc + "<br />";
    }

}
