package com.longan.biz.dataobject;

public class MobileBelong {
    private Long id;

    private String mobilePart;

    private String provinceName;

    private String cityName;

    private String mobileTypeName;

    private String areaCode;

    private String carrierName;

    private Integer carrierType;

    private String provinceCode;

    private String cityCode;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getMobilePart() {
	return mobilePart;
    }

    public void setMobilePart(String mobilePart) {
	this.mobilePart = mobilePart;
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

    public String getMobileTypeName() {
	return mobileTypeName;
    }

    public void setMobileTypeName(String mobileTypeName) {
	this.mobileTypeName = mobileTypeName;
    }

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    public String getCarrierName() {
	return carrierName;
    }

    public void setCarrierName(String carrierName) {
	this.carrierName = carrierName;
    }

    public Integer getCarrierType() {
	return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
	this.carrierType = carrierType;
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
}