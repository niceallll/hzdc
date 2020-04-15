package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

public class BulkOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer status;
    private String bizIds;
    private String supplyOrderIds;
    private Long supplyTraderId;
    private String supplyTraderName;
    private String upstreamSerialno;
    private Date upstreamDate;
    private String upstreamMemo;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
	return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
	this.gmtModify = gmtModify;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public String getBizIds() {
	return bizIds;
    }

    public void setBizIds(String bizIds) {
	this.bizIds = bizIds;
    }

    public String getSupplyOrderIds() {
	return supplyOrderIds;
    }

    public void setSupplyOrderIds(String supplyOrderIds) {
	this.supplyOrderIds = supplyOrderIds;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public String getSupplyTraderName() {
	return supplyTraderName;
    }

    public void setSupplyTraderName(String supplyTraderName) {
	this.supplyTraderName = supplyTraderName;
    }

    public String getUpstreamSerialno() {
	return upstreamSerialno;
    }

    public void setUpstreamSerialno(String upstreamSerialno) {
	this.upstreamSerialno = upstreamSerialno;
    }

    public Date getUpstreamDate() {
	return upstreamDate;
    }

    public void setUpstreamDate(Date upstreamDate) {
	this.upstreamDate = upstreamDate;
    }

    public String getUpstreamMemo() {
	return upstreamMemo;
    }

    public void setUpstreamMemo(String upstreamMemo) {
	this.upstreamMemo = upstreamMemo;
    }
}