package com.longan.mng.api.response;

public class CallbackBizOrderResponse extends MiniBizOrderResponse {
    private String bizOrderId;

    public String getBizOrderId() {
	return bizOrderId;
    }

    public void setBizOrderId(String bizOrderId) {
	this.bizOrderId = bizOrderId;
    }
}
