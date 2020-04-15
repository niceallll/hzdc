package com.hzdc.biz.data.object;

import java.util.Date;

public class TargetDate {
    private Date create;
    private Date modify;
    private Integer costTime;

    public Date getCreate() {
	return create;
    }

    public void setCreate(Date create) {
	this.create = create;
    }

    public Date getModify() {
	return modify;
    }

    public void setModify(Date modify) {
	this.modify = modify;
    }

    public Integer getCostTime() {
	return costTime;
    }

    public void setCostTime(Integer costTime) {
	this.costTime = costTime;
    }
}
