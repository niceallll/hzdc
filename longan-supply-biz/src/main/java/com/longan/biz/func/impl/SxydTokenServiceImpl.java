package com.longan.biz.func.impl;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.longan.biz.func.BaseTokenService;
import com.longan.biz.func.SxydTokenService;
import com.longan.biz.pojo.SxydAccessToken;
import com.longan.biz.pojo.SxydCacheToken;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.Utils;

public class SxydTokenServiceImpl extends BaseTokenService implements SxydTokenService {
    private final static Long sxyd_user = Utils.getLong("sxyd.id");
    private final static Boolean sxyd_token = Utils.getBoolean("sxyd.token");
    private final static String sxyd_url = Utils.getProperty("sxyd.url");

    private final static long service_gap = 5l;
    private final static long token_gap = 20l;
    private static ScheduledExecutorService service_token;
    private static Future<?> future_token;

    void init() {
	if (sxyd_token) {
	    service_token = new ScheduledThreadPoolExecutor(1);
	    future_token = service_token.scheduleAtFixedRate(new Runnable() {
		public void run() {
		    try {
			Object object = memcachedService.get(Constants.CacheKey.SXYD_TOKEN_KEY);
			if (object == null) {
			    logger.warn("缓存的sxyd token是空、首次获取");
			    boolean success = refreshToken();
			    if (success) {
				logger.error("获取sxyd token成功");
			    } else {
				logger.error("获取sxyd token失败");
			    }
			} else {
			    SxydCacheToken token = (SxydCacheToken) object;
			    Date deadline = token.getDeadline();
			    Long resttime = getRestTime(deadline);
			    if (resttime <= token_gap) {
				logger.warn("开始刷新sxyd token");
				boolean success = refreshToken();
				if (success) {
				    logger.error("刷新sxyd token成功");
				} else {
				    logger.error("刷新sxyd token失败");
				}
			    } else {
				logger.warn("不需要刷新sxyd token");
			    }
			}
		    } catch (Exception ex) {
			logger.error("获取sxyd token异常：", ex);
		    }
		}
	    }, 1, service_gap, TimeUnit.MINUTES);
	}
    }

    void clear() {
	if (sxyd_token) {
	    future_token.cancel(true);
	    service_token.shutdownNow();
	}
    }

    private Boolean refreshToken() {
	SxydAccessToken request = new SxydAccessToken();
	String data = JSONObject.fromObject(request).toString();
	String result = sendData(sxyd_url, data, "V1.0.0");
	return processToken(result);
    }

    private Boolean processToken(String data) {
	if (StringUtils.isNotBlank(data)) {
	    try {
		JSONObject json = JSONObject.fromObject(data);
		String code = json.get("returnCode") + "";
		if ("1000".equals(code)) {
		    String accessToken = json.getJSONObject("dataInfo").get("accessToken") + "";
		    if (StringUtils.isBlank(accessToken)) {
			logger.error("读取sxyd accessToken是空");
			return false;
		    }

		    SxydCacheToken token = new SxydCacheToken();
		    Integer expiresTime = Integer.parseInt(json.getJSONObject("dataInfo").get("expiresTime") + "");
		    token.setAccessToken(accessToken);
		    token.setDeadline(expiresTime);// 单位秒
		    token.setExpiresTime(expiresTime);
		    memcachedService.set(Constants.CacheKey.SXYD_TOKEN_KEY, 0, token);
		    saveUserToken(sxyd_user, accessToken, null, token.getDeadline());
		    return true;
		} else {
		    logger.error("处理sxyd token错误：" + json.get("returnCode") + "-" + json.get("message"));
		}
	    } catch (Exception ex) {
		logger.error("解析sxyd token失败：", data);
	    }
	}
	return false;
    }
}
