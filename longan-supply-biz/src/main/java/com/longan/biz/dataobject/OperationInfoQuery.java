package com.longan.biz.dataobject;

import com.longan.biz.domain.QueryBase;

public class OperationInfoQuery extends QueryBase {
    private static final long serialVersionUID = 1L;

    private String operationName;
    private Integer type;
    private Integer status;
    private Integer belongMenu;

    public String getOperationName() {
	return operationName;
    }

    public void setOperationName(String operationName) {
	this.operationName = operationName;
    }

    public Integer getType() {
	return type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getBelongMenu() {
	return belongMenu;
    }

    public void setBelongMenu(Integer belongMenu) {
	this.belongMenu = belongMenu;
    }

}
