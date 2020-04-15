package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.Constants;

public class ManualOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date gmtCreate;
    private Integer status;
    private Long userId;
    private String serialno;
    private Long logId;
    private Long bizOrderId;
    private String itemUid;
    private Integer itemId;
    private String itemName;
    private Integer itemFacePrice;
    private String upstreamMemo;
    private Integer repeatType;

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

    public String getSerialno() {
	return serialno;
    }

    public void setSerialno(String serialno) {
	this.serialno = serialno;
    }

    public Long getLogId() {
	return logId;
    }

    public void setLogId(Long logId) {
	this.logId = logId;
    }

    public Long getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public String getItemUid() {
	return itemUid;
    }

    public void setItemUid(String itemUid) {
	this.itemUid = itemUid;
    }

    public Integer getItemId() {
	return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
    }

    public Integer getItemFacePrice() {
	return itemFacePrice;
    }

    public void setItemFacePrice(Integer itemFacePrice) {
	this.itemFacePrice = itemFacePrice;
    }

    public String getUpstreamMemo() {
	return upstreamMemo;
    }

    public void setUpstreamMemo(String upstreamMemo) {
	this.upstreamMemo = upstreamMemo;
    }

    public Integer getRepeatType() {
	return repeatType;
    }

    public void setRepeatType(Integer repeatType) {
	this.repeatType = repeatType;
    }

    public String getStatusDesc() {
	String result = "";
	if (status == null) {
	    return null;
	}

	if (Constants.ManualOrder.STATUS_INIT == status) {
	    result = Constants.ManualOrder.STATUS_INIT_DESC;
	} else if (Constants.ManualOrder.STATUS_CHARGING == status) {
	    result = Constants.ManualOrder.STATUS_CHARGING_DESC;
	} else if (Constants.ManualOrder.STATUS_SUCCESS == status) {
	    result = Constants.ManualOrder.STATUS_SUCCESS_DESC;
	} else if (Constants.ManualOrder.STATUS_FAILED == status) {
	    result = Constants.ManualOrder.STATUS_FAILED_DESC;
	} else if (Constants.ManualOrder.STATUS_REPEAT == status) {
	    result = Constants.ManualOrder.STATUS_REPEAT_DESC;
	}
	return result;
    }

    public String getRepeatTypeDesc() {
	String result = "";
	if (repeatType == null) {
	    return result;
	}

	if (repeatType == Constants.ManualOrder.REPEAT_TYPE_YES) {
	    result = Constants.ManualOrder.REPEAT_TYPE_YES_DESC;
	} else if (repeatType == Constants.ManualOrder.REPEAT_TYPE_NO) {
	    result = Constants.ManualOrder.REPEAT_TYPE_NO_DESC;
	}
	return result;
    }

    public String getItemFacePriceDesc() {
	Double result = BigDecimalUtils.doubleDiveid(itemFacePrice + "");
	return result.toString();
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return Constants.ManualOrder.STATUS_FAILED == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.ManualOrder.STATUS_SUCCESS == status || Constants.ManualOrder.STATUS_REPEAT == status;
    }

    public boolean canRepeatCharge() {
	if (status == null || gmtCreate == null) {
	    return false;
	}

	Long now = System.currentTimeMillis();
	return Constants.ManualOrder.STATUS_FAILED == status && now - gmtCreate.getTime() < 60 * 1000 * 60 * 3;// 3小时内可补充
    }
}
