package com.longan.mng.api.request;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class LockBizOrderRequest extends MiniBizOrderRequest {
    @NotNull
    @NotBlank
    @RegExp(value = "[\\d]{1,7}", message = "供货商编号不对")
    private String supplyTraderId;

    public String getSupplyTraderId() {
	return supplyTraderId;
    }

    public void setSupplyTraderId(String supplyTraderId) {
	this.supplyTraderId = supplyTraderId;
    }

}
