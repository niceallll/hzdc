package com.longan.biz.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Utils {
    private static final String filename = "/config.properties";

    private static Properties properties = new Properties();

    static {
	try {
	    InputStream is = Utils.class.getResourceAsStream(filename);
	    BufferedReader bf = new BufferedReader(new InputStreamReader(is, "utf-8"));
	    properties.load(bf);
	    bf.close();
	    is.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static String getProperty(String key) {
	if (properties != null) {
	    return (String) properties.get(key);
	}
	return null;
    }

    public static Long getLong(String key) {
	try {
	    String value = getProperty(key);
	    if (value != null) {
		return Long.parseLong(value);
	    }
	} catch (Exception ex) {
	}
	return null;
    }

    public static Boolean getBoolean(String key) {
	try {
	    String value = getProperty(key);
	    if (value != null) {
		return Boolean.parseBoolean(value);
	    }
	} catch (Exception ex) {
	}
	return false;
    }

    public static List<Long> getLongList(String key) {
	try {
	    String value = getProperty(key);
	    if (value != null) {
		String[] slist = value.split(Constants.BUY_REQUEST_SPLIT);
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < slist.length; i++) {
		    list.add(Long.parseLong(slist[i].trim()));
		}
		return list;
	    }
	} catch (Exception ex) {
	}
	return null;
    }

    public static String getFileName(String name) {
	return Utils.class.getResource(name).getFile();
    }

    public static boolean checkMobileFormat(String mobile) {
	if (StringUtils.isBlank(mobile)) {
	    return false;
	}

	Pattern pattern = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
	Matcher matcher = pattern.matcher(mobile);
	return matcher.matches();
    }
}