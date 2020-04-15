package com.longan.biz.dataobject;

import java.util.Date;

import com.longan.biz.domain.QueryBase;
import com.longan.biz.utils.DateTool;

public class UserInfoQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private Date startGmtCreate;
    private Date endGmtCreate;
    private Long id;
    private String loginId;
    private Long acctId;
    private String userName;
    private String status;
    private Integer type;
    private String salesPrice;
    private String roleId;

    public Integer getType() {
	return type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getLoginId() {
	return loginId;
    }

    public void setLoginId(String loginId) {
	this.loginId = loginId;
    }

    public Long getAcctId() {
	return acctId;
    }

    public void setAcctId(Long acctId) {
	this.acctId = acctId;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
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

    public String getSalesPrice() {
	return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
	this.salesPrice = salesPrice;
    }

    public String getRoleId() {
	return roleId;
    }

    public void setRoleId(String roleId) {
	this.roleId = roleId;
    }
}
