package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class OperationInfoForm {
	private String id;
	private String operationCode;
	@NotBlank(message = "操作名称不能为空")
	private String operationName;
	private String operationUrl;
	private String bizId;
	@NotBlank(message = "类型不能为空")
	private String type;
	@NotBlank(message = "状态不能为空")
	private String status;
	private String belongMenu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationUrl() {
		return operationUrl;
	}

	public void setOperationUrl(String operationUrl) {
		this.operationUrl = operationUrl;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBelongMenu() {
		return belongMenu;
	}

	public void setBelongMenu(String belongMenu) {
		this.belongMenu = belongMenu;
	}

}
