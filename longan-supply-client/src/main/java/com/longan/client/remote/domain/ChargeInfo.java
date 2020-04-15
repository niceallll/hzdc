package com.longan.client.remote.domain;

import java.io.Serializable;

public class ChargeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String msg;
    private String status;
    private String statusDesc;
    private Integer actualCost; // 上游实际扣款金额 单位厘

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
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

    public Integer getActualCost() {
	return actualCost;
    }

    public void setActualCost(Integer actualCost) {
	this.actualCost = actualCost;
    }

    public String toString() {
	return "状态码 : " + status + " 描述： " + statusDesc;
    }
}
