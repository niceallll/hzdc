package com.longan.biz.dataobject;

import java.io.Serializable;

public class ItemSalesQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private Integer bizId;
    private String provinceCode;

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

    public String getProvinceCode() {
	return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
    }
}
