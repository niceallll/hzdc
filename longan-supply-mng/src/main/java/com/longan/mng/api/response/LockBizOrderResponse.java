package com.longan.mng.api.response;

public class LockBizOrderResponse extends MiniBizOrderResponse {
    private Long bizOrderId;

    private String telephone;

    private Integer itemFacePrice;

    public String getTelephone() {
	return telephone;
    }

    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }

    public Integer getItemFacePrice() {
	return itemFacePrice;
    }

    public void setItemFacePrice(Integer itemFacePrice) {
	this.itemFacePrice = itemFacePrice;
    }

    public Long getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
	this.bizOrderId = bizOrderId;
    }

    public boolean checkSign() {
	// ef174ebb9539b73c83e2e0bc70c85a0371741bdf74684cf1f4cb24689a6c72e5
	return true;
    }
}
