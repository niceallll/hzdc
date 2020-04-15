package com.longan.biz.dataobject;

import org.apache.commons.lang.StringUtils;

public class AreaInfo {
    private Long id;
    private String provinceCode;
    private String cityCode;
    private String provinceName;
    private String cityName;
    private String areaCode;
    private Integer type;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getProvinceCode() {
	return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
    }

    public String getCityCode() {
	return cityCode;
    }

    public void setCityCode(String cityCode) {
	this.cityCode = cityCode;
    }

    public String getProvinceName() {
	return provinceName;
    }

    public void setProvinceName(String provinceName) {
	this.provinceName = provinceName;
    }

    public String getCityName() {
	return cityName;
    }

    public void setCityName(String cityName) {
	this.cityName = cityName;
    }

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    public Integer getType() {
	return type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    @Override
    public String toString() {
	String province = StringUtils.isEmpty(provinceName) ? "" : provinceName;
	String city = StringUtils.isEmpty(cityName) ? "" : cityName;
	return province + city;
    }
}