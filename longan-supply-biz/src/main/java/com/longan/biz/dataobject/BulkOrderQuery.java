package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class BulkOrderQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer status;
    private Long supplyTraderId;
    private String upstreamSerialno;
    private Date startGmtCreate;
    private Date endGmtCreate;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public String getUpstreamSerialno() {
	return upstreamSerialno;
    }

    public void setUpstreamSerialno(String upstreamSerialno) {
	this.upstreamSerialno = upstreamSerialno;
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
