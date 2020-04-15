package com.longan.biz.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.longan.biz.dataobject.OperationLog;
import com.longan.biz.dataobject.UserInfo;

public class OperLogUtils {
    private static final Logger logger = LoggerFactory.getLogger(OperLogUtils.class);

    public static OperationLog operationLogDeal(Object older, Object newer, UserInfo userInfo, String moduleName, Integer bizId,
	    String loginIp) {// 没有bizId则为null
	try {
	    if (older == null && newer != null) {// 新增 older为null
		String description = getInfo(newer);
		OperationLog operationLog = new OperationLog();
		operationLog.setBizId(bizId);
		operationLog.setDescription(description);
		operationLog.setLoginIp(loginIp);
		operationLog.setModuleName(moduleName);
		operationLog.setOperType(Constants.OperationLog.TYPE_ADD);
		operationLog.setUserId(userInfo.getId());
		operationLog.setUserName(userInfo.getUserName());
		return operationLog;
	    }
	    if (older != null && newer != null) {// 修改
		String description = diff(older, newer);
		OperationLog operationLog = new OperationLog();
		operationLog.setBizId(bizId);
		operationLog.setDescription(description);
		operationLog.setLoginIp(loginIp);
		operationLog.setModuleName(moduleName);
		operationLog.setOperType(Constants.OperationLog.TYPE_UPDATE);
		operationLog.setUserId(userInfo.getId());
		operationLog.setUserName(userInfo.getUserName());
		return operationLog;
	    }
	    if (older != null && newer == null) {// 删除 newer为null
		String description = getInfo(older);
		OperationLog operationLog = new OperationLog();
		operationLog.setBizId(bizId);
		operationLog.setDescription(description);
		operationLog.setLoginIp(loginIp);
		operationLog.setModuleName(moduleName);
		operationLog.setOperType(Constants.OperationLog.TYPE_DELETE);
		operationLog.setUserId(userInfo.getId());
		operationLog.setUserName(userInfo.getUserName());
		return operationLog;
	    }
	    if (older == null && newer == null) {// 其它情况 older为null, newer为null
		OperationLog operationLog = new OperationLog();
		operationLog.setBizId(bizId);
		operationLog.setDescription(moduleName);
		operationLog.setLoginIp(loginIp);
		operationLog.setModuleName(moduleName);
		operationLog.setOperType(Constants.OperationLog.TYPE_OTHER);
		operationLog.setUserId(userInfo.getId());
		operationLog.setUserName(userInfo.getUserName());
		return operationLog;
	    }
	} catch (Exception e) {
	    logger.error("operationLogDeal error", e);
	}
	return null;
    }

    public static OperationLog operationLogDeal(String description, UserInfo userInfo, String moduleName, Integer bizId,
	    String loginIp, Integer operType) {// 没有bizId则为null
	try {
	    OperationLog operationLog = new OperationLog();
	    operationLog.setBizId(bizId);
	    operationLog.setDescription(description);
	    operationLog.setLoginIp(loginIp);
	    operationLog.setModuleName(moduleName);
	    operationLog.setOperType(operType);
	    operationLog.setUserId(userInfo.getId());
	    operationLog.setUserName(userInfo.getUserName());
	    return operationLog;
	} catch (Exception e) {
	    logger.error("operationLogDeal error", e);
	}
	return null;
    }

    public static String diff(Object older, Object newer) {
	Class<?> clazz1 = older.getClass();
	Class<?> clazz2 = newer.getClass();
	if (!clazz1.equals(clazz2)) {
	    return "class不匹配";
	}

	StringBuffer sb = new StringBuffer();
	try {
	    sb.append("[ID: " + clazz1.getDeclaredMethod("getId").invoke(older) + "] ");
	} catch (Exception e) {
	    logger.error("operationLog getDeclaredMethod error", e);
	}
	Field[] fields = clazz1.getDeclaredFields();
	Method method1 = null, method2 = null;
	String methodName;
	Object property1, property2;
	for (Field field : fields) {
	    methodName = field.getName();
	    if (methodName.equalsIgnoreCase("serialVersionUID")) {
		continue;
	    }
	    if ((methodName.length() > 2 // 过滤不显示的字段
	    && "gmt".equals(methodName.substring(0, 3).toLowerCase()))
		    || (methodName.length() > 3 && "time".equalsIgnoreCase(methodName.substring(methodName.length() - 4,
			    methodName.length()))) || "pwd".equalsIgnoreCase(methodName)
		    || "lastLoginIp".equals(methodName)
		    || (methodName.length() > 3 && "list".equalsIgnoreCase(methodName.substring(// 过滤不显示的字段
			    methodName.length() - 4, methodName.length())))) {
		continue;
	    }
	    methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
	    try {
		method1 = clazz1.getDeclaredMethod(methodName);
		method2 = clazz2.getDeclaredMethod(methodName);
	    } catch (Exception e) {
		continue;
	    }

	    try {
		property1 = method1.invoke(older);
		property2 = method2.invoke(newer);
		if (null == property2) {
		    continue;
		}
		if ((null != property1 && !property1.equals(property2)) || (null != property2 && !property2.equals(property1))) {
		    sb.append(field.getName());
		    sb.append(":");
		    sb.append(property1);
		    sb.append("->");
		    sb.append(property2);
		    sb.append("; ");
		}
	    } catch (Exception e) {
		logger.error("method.invoke error", e);
	    }
	}
	return new String(sb);
    }

    public static String getInfo(Object obj) throws Exception {
	Class<?> clazz = obj.getClass();
	StringBuffer sb = new StringBuffer();
	Field[] fields = clazz.getDeclaredFields();
	Method method = null;
	String methodName;
	Object property;
	for (Field field : fields) {
	    methodName = field.getName();
	    /*
	     * if (methodName.equalsIgnoreCase("id") || (methodName.length() > 3 && "name".equalsIgnoreCase(methodName.substring(
	     * methodName.length() - 4, methodName.length())))) {
	     */
	    if ((methodName.length() > 3 && ("list".equalsIgnoreCase(methodName.substring(methodName.length() - 4,
		    methodName.length())) || "type".equalsIgnoreCase(methodName.substring(methodName.length() - 4,
		    methodName.length()))))
		    || (methodName.length() > 4 && "bizId".equalsIgnoreCase(methodName.substring(methodName.length() - 5,
			    methodName.length()))) || "pwd".equalsIgnoreCase(methodName)) {
		continue;
	    }
	    methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
	    try {
		method = clazz.getDeclaredMethod(methodName);
	    } catch (Exception e) {
		continue;
	    }
	    property = method.invoke(obj);
	    if (null != property || !StringUtils.isEmpty((String) property)) {
		sb.append(field.getName());
		sb.append("=");
		sb.append(property + ";");
	    }
	}
	return new String(sb);
    }
}
