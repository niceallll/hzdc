package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class StockQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date startGmtCreate;
    private Date endGmtCreate;
    private Integer status;
    private Integer ItemId;
    private String cardId;
    private Long bizOrderId;
    private Date startOutTime;
    private Date endOutTime;
    private Long itemSupplyId;
    private Long inSerialno;
    private Integer bizId;
    private Long supplyTraderId;
    private Long outSerialno;
    private Long supplyOrderId;

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

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getItemId() {
	return ItemId;
    }

    public void setItemId(Integer itemId) {
	ItemId = itemId;
    }

    public String getCardId() {
	return cardId;
    }

    public void setCardId(String cardId) {
	this.cardId = cardId;
    }

    public Long getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public Date getStartOutTime() {
	return startOutTime;
    }

    public void setStartOutTime(Date startOutTime) {
	this.startOutTime = DateTool.formatStartDate(startOutTime);
    }

    public Date getEndOutTime() {
	return endOutTime;
    }

    public void setEndOutTime(Date endOutTime) {
	this.endOutTime = DateTool.formateEndDate(endOutTime);
    }

    public Long getItemSupplyId() {
	return itemSupplyId;
    }

    public void setItemSupplyId(Long itemSupplyId) {
	this.itemSupplyId = itemSupplyId;
    }

    public Long getInSerialno() {
	return inSerialno;
    }

    public void setInSerialno(Long inSerialno) {
	this.inSerialno = inSerialno;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public Long getOutSerialno() {
	return outSerialno;
    }

    public void setOutSerialno(Long outSerialno) {
	this.outSerialno = outSerialno;
    }

    public Long getSupplyOrderId() {
	return supplyOrderId;
    }

    public void setSupplyOrderId(Long supplyOrderId) {
	this.supplyOrderId = supplyOrderId;
    }
}
