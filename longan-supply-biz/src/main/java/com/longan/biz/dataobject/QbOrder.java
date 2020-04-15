package com.longan.biz.dataobject;

import java.io.Serializable;

public class QbOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serialNum;

    private String time;

    private String areaCallerId;

    private String qqNum;

    private String qbValue;

    private String qbType;

    private String status;

    private String reTime;

    private String reStatus;

    private String result;

    public String getSerialNum() {
	return serialNum;
    }

    public void setSerialNum(String serialNum) {
	this.serialNum = serialNum;
    }

    public String getTime() {
	return time;
    }

    public void setTime(String time) {
	this.time = time;
    }

    public String getAreaCallerId() {
	return areaCallerId;
    }

    public void setAreaCallerId(String areaCallerId) {
	this.areaCallerId = areaCallerId;
    }

    public String getQqNum() {
	return qqNum;
    }

    public void setQqNum(String qqNum) {
	this.qqNum = qqNum;
    }

    public String getQbValue() {
	return qbValue;
    }

    public void setQbValue(String qbValue) {
	this.qbValue = qbValue;
    }

    public String getQbType() {
	return qbType;
    }

    public void setQbType(String qbType) {
	this.qbType = qbType;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getReTime() {
	return reTime;
    }

    public void setReTime(String reTime) {
	this.reTime = reTime;
    }

    public String getReStatus() {
	return reStatus;
    }

    public void setReStatus(String reStatus) {
	this.reStatus = reStatus;
    }

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

}
