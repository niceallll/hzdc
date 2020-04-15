package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class StockLogQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Date startGmtCreate;
    private Date endGmtCreate;
    private Long id;
    private Long supplyTraderId;
    private Long itemSupplyId;
    private Integer itemId;
    private String itemName;
    private Integer type;
    private Integer operId;
    private String operName;
    private Integer bizId;
    private Integer status;

    public Long getItemSupplyId() {
	return itemSupplyId;
    }

    public void setItemSupplyId(Long itemSupplyId) {
	this.itemSupplyId = itemSupplyId;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public String getOperName() {
	return operName;
    }

    public void setOperName(String operName) {
	this.operName = operName;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
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

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public Integer getType() {
	return type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    public Integer getOperId() {
	return operId;
    }

    public void setOperId(Integer operId) {
	this.operId = operId;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

}
