package com.longan.biz.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.beanutils.ConvertUtils;

public class SwitchUtils {
    private static Map<String, Class<?>> cache = null;

    public static void setStaticFieldValue(String className, String fieldName, String value) throws Exception {
	Object[] fieldData = getClassField(className, fieldName, true);
	Class<?> classObject = (Class<?>) fieldData[0];
	Field field = (Field) fieldData[1];

	try {
	    // 将参数转换为对应值
	    Object targetValue = ConvertUtils.convert(value, field.getType());

	    field.setAccessible(true);
	    field.set(classObject, targetValue);
	} catch (Exception e) {
	    throw new Exception("error occured during setting value." + e.getMessage());
	}
    }

    public static Object getStaticFieldValue(String className, String fieldName) throws Exception {
	Object[] fieldData = getClassField(className, fieldName, true);
	Class<?> classObject = (Class<?>) fieldData[0];
	Field field = (Field) fieldData[1];

	try {
	    field.setAccessible(true);
	    return field.get(classObject);
	} catch (Exception e) {
	    throw new Exception("error occured during getting value." + e.getMessage());
	}
    }

    protected static Object[] getClassField(String className, String fieldName, boolean mustStatic) throws Exception {
	Class<?> classObject = null;
	try {
	    if (cache == null) {
		cache = new ConcurrentHashMap<String, Class<?>>();
	    }

	    classObject = cache.get(className);
	    if (classObject == null) {
		classObject = Class.forName(className);
		cache.put(className, classObject);
	    }
	} catch (Exception e) {
	    throw new Exception("class [" + className + "] not found.");
	}

	Field field = null;
	try {
	    // 取得public字段
	    field = classObject.getField(fieldName);
	} catch (Exception e1) {
	    try {
		// 尝试取当前类里面定义的隐藏字段，父类中的不考虑
		field = classObject.getDeclaredField(fieldName);
	    } catch (Exception e2) {
		// not found
	    }
	}

	if (field == null) {
	    // not found
	    throw new Exception("field [" + fieldName + "] not found.");
	}

	if (mustStatic && (field.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
	    // not static field
	    throw new Exception("field [" + fieldName + "] is not static.");
	}

	return new Object[] { classObject, field };
    }

    private final static String CLASS = "com.longan.biz.utils.SwitchConstant";

    public static void setCheckAreaSwitch(Boolean flag) {
	try {
	    SwitchUtils.setStaticFieldValue(CLASS, "CHECK_AREA_SWITCH", flag.toString());
	} catch (Exception e) {
	}
    }

    public static void setCheckCarrierTypeSwitch(Boolean flag) {
	try {
	    SwitchUtils.setStaticFieldValue(CLASS, "CHECK_CARRIER_TYPE_SWITCH", flag.toString());
	} catch (Exception e) {
	}
    }

    public static void main(String[] args) {
	String className = "com.longan.biz.utils.SwitchConstant";
	try {
	    System.out.println(SwitchUtils.getStaticFieldValue(className, "CHECK_AREA_SWITCH").toString());
	    SwitchUtils.setStaticFieldValue(className, "CHECK_AREA_SWITCH", "false");
	    System.out.println(SwitchUtils.getStaticFieldValue(className, "CHECK_AREA_SWITCH").toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
