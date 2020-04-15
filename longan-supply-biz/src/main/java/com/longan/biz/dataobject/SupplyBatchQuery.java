package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class SupplyBatchQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Date startGmtCreate;
    private Date endGmtCreate;
    private Integer status;
    private Integer bizId;
    private Long upstreamId;
    private Long operId;
    private Long verifyOperId;
    private Integer result;

    public Date getStartGmtCreate() {
	return startGmtCreate;
    }

    public void setStartGmtCreate(Date startGmtCreate) {
	this.startGmtCreate = DateTool.formatStartDate(startGmtCreate);
    }

    public Date getEndGmtCreate() {
	return endGmtCreate;
    }

    public void setEndGmtCreate(Date endGmtCreate) {
	this.endGmtCreate = DateTool.formateEndDate(endGmtCreate);
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Long getUpstreamId() {
	return upstreamId;
    }

    public void setUpstreamId(Long upstreamId) {
	this.upstreamId = upstreamId;
    }

    public Long getOperId() {
	return operId;
    }

    public void setOperId(Long operId) {
	this.operId = operId;
    }

    public Long getVerifyOperId() {
	return verifyOperId;
    }

    public void setVerifyOperId(Long verifyOperId) {
	this.verifyOperId = verifyOperId;
    }

    public Integer getResult() {
	return result;
    }

    public void setResult(Integer result) {
	this.result = result;
    }
}
