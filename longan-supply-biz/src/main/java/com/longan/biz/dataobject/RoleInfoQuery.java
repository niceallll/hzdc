package com.longan.biz.dataobject;

import com.longan.biz.domain.QueryBase;

public class RoleInfoQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private String roleDesc;
    private Integer status;

    public String getRoleDesc() {
	return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
	this.roleDesc = roleDesc;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }
}
