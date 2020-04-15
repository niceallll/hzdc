package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class ExportQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private Long userId;
    private Integer exportType;
    private Integer bizId;
    private Integer itemId;
    private Long upstreamId;
    private Long downstreamId;
    private Date startGmtCreate;
    private Date endGmtCreate;

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Integer getExportType() {
	return exportType;
    }

    public void setExportType(Integer exportType) {
	this.exportType = exportType;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public Long getUpstreamId() {
	return upstreamId;
    }

    public void setUpstreamId(Long upstreamId) {
	this.upstreamId = upstreamId;
    }

    public Long getDownstreamId() {
	return downstreamId;
    }

    public void setDownstreamId(Long downstreamId) {
	this.downstreamId = downstreamId;
    }

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
}
