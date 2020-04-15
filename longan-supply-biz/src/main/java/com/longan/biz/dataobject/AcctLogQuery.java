package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class AcctLogQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Date startGmtCreate;
    private Date endGmtCreate;
    private Long userId;
    private Long acctId;
    private Integer billType;
    private Long billId;
    private Integer bizId;
    private Integer tradeType;
    private Long id;
    private Long bizOrderId;
    private Long upStreamId;
    private Integer itemId;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Long getAcctId() {
	return acctId;
    }

    public void setAcctId(Long acctId) {
	this.acctId = acctId;
    }

    public Integer getBillType() {
	return billType;
    }

    public void setBillType(Integer billType) {
	this.billType = billType;
    }

    public Long getBillId() {
	return billId;
    }

    public void setBillId(Long billId) {
	this.billId = billId;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Integer getTradeType() {
	return tradeType;
    }

    public void setTradeType(Integer tradeType) {
	this.tradeType = tradeType;
    }

    public Long getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public Long getUpStreamId() {
	return upStreamId;
    }

    public void setUpStreamId(Long upStreamId) {
	this.upStreamId = upStreamId;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

}
