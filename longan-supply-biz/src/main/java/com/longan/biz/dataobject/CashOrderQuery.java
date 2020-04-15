package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class CashOrderQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Date startGmtCreate;
    private Date endGmtCreate;
    private Long userId;
    private Long acctId;
    private String acctDate;
    private Integer status;
    private Long acctLogId;
    private String bankSerialno;
    private Long id;

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

    public String getAcctDate() {
	return acctDate;
    }

    public void setAcctDate(String acctDate) {
	this.acctDate = acctDate;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Long getAcctLogId() {
	return acctLogId;
    }

    public void setAcctLogId(Long acctLogId) {
	this.acctLogId = acctLogId;
    }

    public String getBankSerialno() {
	return bankSerialno;
    }

    public void setBankSerialno(String bankSerialno) {
	this.bankSerialno = bankSerialno;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

}
