package com.longan.mng.api.response;

import java.lang.reflect.Field;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longan.biz.utils.DateTool;

public class MiniBizOrderResponse {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String status = "failed";
    private String msg;

    public void setSuccess() {
	this.status = "success";
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
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
