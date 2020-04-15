package com.longan.biz.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class FuncUtils {
    public static int getCarrierType(String uid) {
	int carrierType = 0;
	for (int key : Constants.MOBILE_CMCC__KEY) {
	    if (uid.startsWith(key + "")) {
		carrierType = Constants.Item.CARRIER_TYPE_MOBILE;
		return carrierType;
	    }
	}
	for (int key : Constants.MOBILE_UNICOM__KEY) {
	    if (uid.startsWith(key + "")) {
		carrierType = Constants.Item.CARRIER_TYPE_UNICOM;
		return carrierType;
	    }
	}
	for (int key : Constants.MOBILE_TELECOM__KEY) {
	    if (uid.startsWith(key + "")) {
		carrierType = Constants.Item.CARRIER_TYPE_TELECOM;
		return carrierType;
	    }
	}
	return carrierType;
    }

    public static String getLetterAndNumber(String data) {
	try {
	    if (StringUtils.isNotBlank(data)) {
		data = data.replaceAll("[^0-9|a-z|A-Z]", "");
	    }
	    return data;
	} catch (Exception ex) {
	}
	return "";
    }

    public static boolean noneNotified(Integer notifyStatus) {
	if (notifyStatus == null) {
	    return true;
	}
	return notifyStatus != Constants.BizOrder.NOTIFY_SUCCESS && notifyStatus != Constants.BizOrder.NOTIFY_FAILED;
    }

    public static String getQueryItem(String name, String data) {
	if (StringUtils.isBlank(name) || StringUtils.isBlank(data)) {
	    return "";
	}

	try {
	    String[] list = data.split("&");
	    for (String items : list) {
		String[] item = items.split("=");
		if (name.equals(item[0])) {
		    return item[1].trim();
		}
	    }
	} catch (Exception ex) {
	}
	return "";
    }

    public static String mapToString(Map<String, String> map) {
	if (map == null) {
	    return null;
	}
	StringBuffer sb = new StringBuffer("");
	for (Entry<String, String> e : map.entrySet()) {
	    sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
	}
	sb.replace(sb.length() - 1, sb.length(), "");
	return sb.toString();
    }

    public static String getUserAlertSign(String key, Long userId, String timestamp) {
	return Md5Encrypt.md5(key + userId + timestamp + key);
    }
}
