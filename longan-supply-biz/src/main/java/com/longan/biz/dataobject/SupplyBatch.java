package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class SupplyBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer status;
    private Integer bizId;
    private Long upstreamId;
    private String upstreamName;
    private Long operId;
    private String operName;
    private Long verifyOperId;
    private String verifyOperName;
    private String fileName;
    private Integer costTime;
    private Integer result;
    private String errorMsg;
    private String memo;

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

    public String getUpstreamName() {
	return upstreamName;
    }

    public void setUpstreamName(String upstreamName) {
	this.upstreamName = upstreamName;
    }

    public Long getOperId() {
	return operId;
    }

    public void setOperId(Long operId) {
	this.operId = operId;
    }

    public String getOperName() {
	return operName;
    }

    public void setOperName(String operName) {
	this.operName = operName;
    }

    public Long getVerifyOperId() {
	return verifyOperId;
    }

    public void setVerifyOperId(Long verifyOperId) {
	this.verifyOperId = verifyOperId;
    }

    public String getVerifyOperName() {
	return verifyOperName;
    }

    public void setVerifyOperName(String verifyOperName) {
	this.verifyOperName = verifyOperName;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public Integer getCostTime() {
	return costTime;
    }

    public void setCostTime(Integer costTime) {
	this.costTime = costTime;
    }

    public Integer getResult() {
	return result;
    }

    public void setResult(Integer result) {
	this.result = result;
    }

    public String getErrorMsg() {
	return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    public String getMemo() {
	return memo;
    }

    public void setMemo(String memo) {
	this.memo = memo;
    }

    public Integer computCostTime(Date date) {
	if (date != null) {
	    Long costTime = (System.currentTimeMillis() - date.getTime()) / 1000;
	    if (costTime == 0) {
		costTime = 1L;
	    }
	    return costTime.intValue();
	}
	return null;
    }

    public boolean showRed() {
	if (status == null) {
	    return false;
	}
	return Constants.SupplyBatch.STATUS_INIT == status;
    }

    public boolean showGreen() {
	if (status == null) {
	    return false;
	}
	return Constants.SupplyBatch.STATUS_SUCCESS == status;
    }

    public String getStatusDesc() {
	if (status == null) {
	    return null;
	}

	if (Constants.SupplyBatch.STATUS_INIT == status) {
	    return Constants.SupplyBatch.STATUS_INIT_DESC;
	} else if (Constants.SupplyBatch.STATUS_SUCCESS == status) {
	    return Constants.SupplyBatch.STATUS_SUCCESS_DESC;
	} else if (Constants.SupplyBatch.STATUS_FAILED == status) {
	    return Constants.SupplyBatch.STATUS_FAILED_DESC;
	}
	return null;
    }

    public String getCostTimeDesc() {
	if (costTime == null) {
	    return null;
	}
	return DateTool.secondToDate(costTime);
    }

    public String getResultDesc() {
	if (result == null) {
	    return null;
	}

	if (Constants.SupplyBatch.RESULT_INIT == result) {
	    return Constants.SupplyBatch.RESULT_INIT_DESC;
	} else if (Constants.SupplyBatch.RESULT_NORMAL == result) {
	    return Constants.SupplyBatch.RESULT_NORMAL_DESC;
	} else if (Constants.SupplyBatch.RESULT_SUCCESS == result) {
	    return Constants.SupplyBatch.RESULT_SUCCESS_DESC;
	} else if (Constants.SupplyBatch.RESULT_FAILED == result) {
	    return Constants.SupplyBatch.RESULT_FAILED_DESC;
	} else if (Constants.SupplyBatch.RESULT_PARTS == result) {
	    return Constants.SupplyBatch.RESULT_PARTS_DESC;
	} else if (Constants.SupplyBatch.RESULT_CLOSE == result) {
	    return Constants.SupplyBatch.RESULT_CLOSE_DESC;
	}
	return null;
    }

    public boolean canDeal() {
	if (status == null) {
	    return false;
	}
	return Constants.SupplyBatch.STATUS_INIT == status;
    }
}
