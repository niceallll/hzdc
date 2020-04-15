package com.longan.biz.dataobject;

import java.util.Date;
import java.util.List;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class SupplyOrderQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Date startGmtCreate;
    private Date endGmtCreate;
    private Integer supplyStatus;
    private Integer itemId;
    private String itemUid;
    private Long userId;
    private Integer bizId;
    private Long id;
    private Long bulkId;
    private Long supplyTraderId;
    private Integer supplyTermPeriod;
    private Integer lessCostTime;
    private Integer moreCostTime;
    private Long bizOrderId;
    private String upstreamSerialno;
    private Integer upstreamStatus;
    private Long lockOperId;
    private Long dealOperId;
    private Integer supplyType;
    private List<Integer> statusList;
    private List<Integer> upstatusList;
    private String provinceCode;
    private Integer saleStatus;

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

    public Integer getSupplyStatus() {
	return supplyStatus;
    }

    public void setSupplyStatus(Integer supplyStatus) {
	this.supplyStatus = supplyStatus;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public String getItemUid() {
	return itemUid;
    }

    public void setItemUid(String itemUid) {
	this.itemUid = itemUid;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getBulkId() {
	return bulkId;
    }

    public void setBulkId(Long bulkId) {
	this.bulkId = bulkId;
    }

    public Long getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(Long supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

    public Integer getSupplyTermPeriod() {
	return supplyTermPeriod;
    }

    public void setSupplyTermPeriod(Integer supplyTermPeriod) {
	this.supplyTermPeriod = supplyTermPeriod;
    }

    public Integer getLessCostTime() {
	return lessCostTime;
    }

    public void setLessCostTime(Integer lessCostTime) {
	this.lessCostTime = lessCostTime;
    }

    public Integer getMoreCostTime() {
	return moreCostTime;
    }

    public void setMoreCostTime(Integer moreCostTime) {
	this.moreCostTime = moreCostTime;
    }

    public Long getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public String getUpstreamSerialno() {
	return upstreamSerialno;
    }

    public void setUpstreamSerialno(String upstreamSerialno) {
	this.upstreamSerialno = upstreamSerialno;
    }

    public Integer getUpstreamStatus() {
	return upstreamStatus;
    }

    public void setUpstreamStatus(Integer upstreamStatus) {
	this.upstreamStatus = upstreamStatus;
    }

    public Long getLockOperId() {
	return lockOperId;
    }

    public void setLockOperId(Long lockOperId) {
	this.lockOperId = lockOperId;
    }

    public Long getDealOperId() {
	return dealOperId;
    }

    public void setDealOperId(Long dealOperId) {
	this.dealOperId = dealOperId;
    }

    public Integer getSupplyType() {
	return supplyType;
    }

    public void setSupplyType(Integer supplyType) {
	this.supplyType = supplyType;
    }

    public List<Integer> getStatusList() {
	return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
	this.statusList = statusList;
    }

    public List<Integer> getUpstatusList() {
	return upstatusList;
    }

    public void setUpstatusList(List<Integer> upstatusList) {
	this.upstatusList = upstatusList;
    }

    public String getProvinceCode() {
	return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
    }

    public Integer getSaleStatus() {
	return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
	this.saleStatus = saleStatus;
    }
}
