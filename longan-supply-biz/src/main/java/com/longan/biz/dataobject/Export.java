package com.longan.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;

public class Export implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date dateStart;
    private Date dateEnd;
    private Integer status;
    private Long userId;
    private Integer exportType;
    private Integer bizId;
    private Integer itemId;
    private String itemName;
    private Long upstreamId;
    private String upstreamName;
    private Long downstreamId;
    private String downstreamName;
    private String fileName;
    private Integer costTime;
    private Date gmtCreate;
    private Date gmtModify;

    private Integer pageCount;
    private Boolean isDownStream;
    private Boolean isPartner;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getDateStart() {
	return dateStart;
    }

    public void setDateStart(Date dateStart) {
	this.dateStart = dateStart;
    }

    public Date getDateEnd() {
	return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
	this.dateEnd = dateEnd;
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

    public String getItemName() {
	return itemName;
    }

    public void setItemName(String itemName) {
	this.itemName = itemName;
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

    public Long getDownstreamId() {
	return downstreamId;
    }

    public void setDownstreamId(Long downstreamId) {
	this.downstreamId = downstreamId;
    }

    public String getDownstreamName() {
	return downstreamName;
    }

    public void setDownstreamName(String downstreamName) {
	this.downstreamName = downstreamName;
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

    public String getStatusDesc() {
	String result = null;
	if (status == null) {
	    return null;
	}
	if (Constants.Export.STATUS_EXPORT == status) {
	    result = Constants.Export.STATUS_EXPORT_DESC;
	} else if (Constants.Export.STATUS_SUCCESS == status) {
	    result = Constants.Export.STATUS_SUCCESS_DESC;
	} else if (Constants.Export.STATUS_FAILED == status) {
	    result = Constants.Export.STATUS_FAILED_DESC;
	} else if (Constants.Export.STATUS_EXCEPTION == status) {
	    result = Constants.Export.STATUS_EXCEPTION_DESC;
	}
	return result;
    }

    public String getCostTimeDesc() {
	if (costTime == null) {
	    return null;
	}
	return DateTool.secondToDate(costTime);
    }

    public boolean canDownload() {
	if (status == null) {
	    return false;
	}
	return (status == Constants.Export.STATUS_SUCCESS);
    }

    public boolean canDelete() {
	if (status == null) {
	    return false;
	}
	Long now = System.currentTimeMillis();
	return (status != Constants.Export.STATUS_EXPORT || now - gmtCreate.getTime() > 60 * 60 * 1000);
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

    public Integer getPageCount() {
	return pageCount;
    }

    public void setPageCount(Integer pageCount) {
	this.pageCount = pageCount;
    }

    public Boolean getIsDownStream() {
	return isDownStream;
    }

    public void setIsDownStream(Boolean isDownStream) {
	this.isDownStream = isDownStream;
    }

    public Boolean getIsPartner() {
	return isPartner;
    }

    public void setIsPartner(Boolean isPartner) {
	this.isPartner = isPartner;
    }
}
