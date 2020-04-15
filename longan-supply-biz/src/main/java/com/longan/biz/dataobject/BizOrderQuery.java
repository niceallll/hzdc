package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class BizOrderQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Date startGmtCreate;
    private Date endGmtCreate;
    private Integer status;
    private Integer notifyStatus;
    private Integer itemId;
    private String itemUid;
    private Long payOrderId;
    private Long userId;
    private Integer bizId;
    private Long id;
    private Long itemSupplyId;
    private Long stockId;
    private Long lockOperId;
    private Long dealOperId;
    private String upstreamSerialno;
    private String downstreamSerialno;
    private String itemPosId;
    private Long upstreamId;
    private String itemPcId;
    private String provinceCode;
    private Integer lessCostTime;
    private Integer moreCostTime;
    private Integer lessNotifyTime;
    private Integer moreNotifyTime;
    private Integer supplyType;
    private Integer carrierType;
    private String memo;
    //时，分，秒的封装
    private String startHour;
    private String startMinute;
    private String startSecond;
    private String endHour;
    private String endMinute;
    private String endSecond;

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public String getStartSecond() {
        return startSecond;
    }

    public void setStartSecond(String startSecond) {
        this.startSecond = startSecond;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public String getEndSecond() {
        return endSecond;
    }

    public void setEndSecond(String endSecond) {
        this.endSecond = endSecond;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getItemSupplyId() {
	return itemSupplyId;
    }

    public void setItemSupplyId(Long itemSupplyId) {
	this.itemSupplyId = itemSupplyId;
    }

    public Long getStockId() {
	return stockId;
    }

    public void setStockId(Long stockId) {
	this.stockId = stockId;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getBizId() {
	return bizId;
    }

    public void setBizId(Integer bizId) {
	this.bizId = bizId;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getNotifyStatus() {
	return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
	this.notifyStatus = notifyStatus;
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

    public Long getPayOrderId() {
	return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
	this.payOrderId = payOrderId;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public Date getStartGmtCreate() {
	return startGmtCreate;
    }
    //字段转换了
    public void setStartGmtCreate(Date startGmtCreate) {

	this.startGmtCreate = DateTool.formatStartDate(startGmtCreate);
    }

    public Date getEndGmtCreate() {
	return endGmtCreate;
    }
    //字段发生了改变
    public void setEndGmtCreate(Date endGmtCreate) {
	this.endGmtCreate = DateTool.formateEndDate(endGmtCreate);
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

    public String getUpstreamSerialno() {
	return upstreamSerialno;
    }

    public void setUpstreamSerialno(String upstreamSerialno) {
	this.upstreamSerialno = upstreamSerialno;
    }

    public String getDownstreamSerialno() {
	return downstreamSerialno;
    }

    public void setDownstreamSerialno(String downstreamSerialno) {
	this.downstreamSerialno = downstreamSerialno;
    }

    public String getItemPosId() {
	return itemPosId;
    }

    public void setItemPosId(String itemPosId) {
	this.itemPosId = itemPosId;
    }

    public Long getUpstreamId() {
	return upstreamId;
    }

    public void setUpstreamId(Long upstreamId) {
	this.upstreamId = upstreamId;
    }

    public String getItemPcId() {
	return itemPcId;
    }

    public void setItemPcId(String itemPcId) {
	this.itemPcId = itemPcId;
    }

    public String getProvinceCode() {
	return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
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

    public Integer getLessNotifyTime() {
	return lessNotifyTime;
    }

    public void setLessNotifyTime(Integer lessNotifyTime) {
	this.lessNotifyTime = lessNotifyTime;
    }

    public Integer getMoreNotifyTime() {
	return moreNotifyTime;
    }

    public void setMoreNotifyTime(Integer moreNotifyTime) {
	this.moreNotifyTime = moreNotifyTime;
    }

    public Integer getSupplyType() {
	return supplyType;
    }

    public void setSupplyType(Integer supplyType) {
	this.supplyType = supplyType;
    }

    public Integer getCarrierType() {
	return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
	this.carrierType = carrierType;
    }
}
