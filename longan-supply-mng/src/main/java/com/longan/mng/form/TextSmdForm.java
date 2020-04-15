package com.longan.mng.form;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class TextSmdForm {
    @NotBlank(message = "商品编号不能为空")
    @NotNull(message = "商品编号不能为空")
    @RegExp(value = "^\\d{1,8}$", message = "商品编号必须是1到8位数字")
    private String itemId;

    @NotBlank(message = "手机号不能为空")
    @NotNull(message = "手机号不能为空")
    private String mobiles;

    @NotBlank(message = "短信内容不能为空")
    private String texts;

    public String getItemId() {
	return itemId;
    }

    public void setItemId(String itemId) {
	this.itemId = itemId;
    }

    public String getMobiles() {
	return mobiles;
    }

    public void setMobiles(String mobiles) {
	this.mobiles = mobiles;
    }

    public String getTexts() {
	return texts;
    }

    public void setTexts(String texts) {
	this.texts = texts;
    }
}
