package com.longan.mng.api.request;

import java.lang.reflect.Field;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;
import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

import com.longan.biz.utils.DateTool;

public class MiniBizOrderRequest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NotNull
    @NotBlank
    private String pcId;
    @NotNull
    @NotBlank
    @RegExp(value = "[\\d]{1,4}", message = "pos机编号不对")
    private String posId;
    @NotNull
    @NotBlank
    private String sign;

    public String getPcId() {
	return pcId;
    }

    public void setPcId(String pcId) {
	this.pcId = pcId;
    }

    public String getPosId() {
	return posId;
    }

    public void setPosId(String posId) {
	this.posId = posId;
    }

    public String getSign() {
	return sign;
    }

    public void setSign(String sign) {
	this.sign = sign;
    }

    public String toString() {
	Field[] fields = this.getClass().getDeclaredFields(); // 获取所有属性

	StringBuffer sb = new StringBuffer("");
	for (Field filed : fields) {
	    filed.setAccessible(true); // 设置对私有的访问权限
	    try {
		if (filed.get(this) != null && !filed.getName().equals("serialVersionUID")) {
		    if (filed.get(this) instanceof Date) {
			sb.append(filed.getName()).append("=").append(DateTool.parseDate((Date) filed.get(this))).append("&");
		    } else {
			sb.append(filed.getName()).append("=").append(filed.get(this)).append("&");
		    }
		}
	    } catch (IllegalArgumentException e1) {
		logger.error("Request toString error", e1);
	    } catch (IllegalAccessException e1) {
		logger.error("Request toString error", e1);
	    }
	}

	if (this.getClass().getGenericSuperclass() != null) {
	    Class<?> superClass = this.getClass().getSuperclass();// 父类
	    Field[] superFields = superClass.getDeclaredFields();// 父类变量
	    for (Field filed : superFields) {
		filed.setAccessible(true); // 设置对私有的访问权限
		try {
		    if (filed.get(this) != null && !filed.getName().equals("logger")) {
			if (filed.get(this) instanceof Date) {
			    sb.append(filed.getName()).append("=").append(DateTool.parseDate((Date) filed.get(this))).append("&");
			} else {
			    sb.append(filed.getName()).append("=").append(filed.get(this)).append("&");
			}
		    }
		} catch (IllegalArgumentException e1) {
		    logger.error("Request toString error", e1);
		} catch (IllegalAccessException e1) {
		    logger.error("Request toString error", e1);
		}
	    }
	}

	return sb.toString();
    }

}
