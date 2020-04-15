package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class SupplyBatchForm {
    @NotBlank(message = "业务编号不能为空")
    private String bizId;

    @NotBlank(message = "上游供货商不能为空")
    private String upstreamId;

    private String memo;

    public String getBizId() {
	return bizId;
    }

    public void setBizId(String bizId) {
	this.bizId = bizId;
    }

    public String getUpstreamId() {
	return upstreamId;
    }

    public void setUpstreamId(String upstreamId) {
	this.upstreamId = upstreamId;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }
}
