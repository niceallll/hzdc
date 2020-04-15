package com.longan.mng.utils;

import com.longan.biz.utils.Utils;

import java.util.Date;

public class Constants {
    public final static String SESSION_USER = "LoginUser";
    public final static String NO_NEED_LOGIN_URL = "index.do|login.do|checkCode.do|authError.do|loginOut.do|lockBizOrder.do|callbackBizOrder.do|admin/initCharingLimit.do|admin/downloadExportFile|admin/deleteExportFile|third/pddCode.do";

    public final static String STATIC_SERVER_KEY = "staticServer";
    public final static String DOMAIN_SERVER_KEY = "domainServer";

    public final static String STATIC_SERVER = Utils.getProperty(STATIC_SERVER_KEY);
    public final static String DOMAIN_SERVER = Utils.getProperty(DOMAIN_SERVER_KEY);

    public final static String UPLOAD_PATH = Utils.getProperty("upload.url");

    public final static String ENV = Utils.getProperty("env");

    public static boolean isProduct() {
	return "product".equals(ENV);
    }
    public static Date startTime = null;
    public static Date endTiem = null;
}
