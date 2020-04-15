package com.longan.biz.func.impl;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.longan.biz.func.BaseTokenService;
import com.longan.biz.func.PddTokenService;
import com.longan.biz.pojo.PddAccessToken;
import com.longan.biz.pojo.PddCacheToken;
import com.longan.biz.pojo.PddRefreshToken;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.Utils;

public class PddTokenServiceImpl extends BaseTokenService implements PddTokenService {
    private final static Long pdd_user = Utils.getLong("pdd.id");
    private final static Boolean pdd_refresh = Utils.getBoolean("pdd.refresh");
    private final static String pdd_url = Utils.getProperty("pdd.url");

    private final static long service_gap = 5l;// 单位分
    private final static long refresh_gap = 20l;// 单位分
    private static ScheduledExecutorService service_refresh;
    private static Future<?> future_refresh;

    void init() {
	if (pdd_refresh) {
	    service_refresh = new ScheduledThreadPoolExecutor(1);
	    future_refresh = service_refresh.scheduleAtFixedRate(new Runnable() {
		public void run() {
		    try {
			Object object = memcachedService.get(Constants.CacheKey.PDD_TOKEN_KEY);
			if (object == null) {
			    logger.error("缓存的pdd token是空");
			} else {
			    PddCacheToken token = (PddCacheToken) object;
			    Date deadline = token.getDeadline();
			    Long resttime = getRestTime(deadline);
			    if (resttime <= refresh_gap) {
				logger.warn("开始刷新pdd token");
				boolean success = refreshToken(token.getRefreshToken());
				if (success) {
				    logger.error("刷新pdd token成功");
				} else {
				    logger.error("刷新pdd token失败");
				}
			    } else {
				logger.warn("不需要刷新pdd token");
			    }
			}
		    } catch (Exception ex) {
			logger.error("刷新pdd token异常：", ex);
		    }
		}
	    }, 2, service_gap, TimeUnit.MINUTES);
	}
    }

    void clear() {
	if (pdd_refresh) {
	    future_refresh.cancel(true);
	    service_refresh.shutdownNow();
	}
    }

    @Override
    public Boolean accessToken(String code) {
	PddAccessToken request = new PddAccessToken();
	request.setCode(code);
	String data = JSONObject.fromObject(request).toString();
	String result = sendData(pdd_url, data, null);
	return processToken(result);
    }

    private Boolean refreshToken(String refreshToken) {
	PddRefreshToken request = new PddRefreshToken();
	request.setRefresh_token(refreshToken);
	String data = JSONObject.fromObject(request).toString();
	String result = sendData(pdd_url, data, null);
	return processToken(result);
    }

    private Boolean processToken(String data) {
	if (StringUtils.isNotBlank(data)) {
	    try {
		JSONObject json = JSONObject.fromObject(data);
		if (json.containsKey("error_response")) {
		    logger.error("处理pdd token错误：" + json.getJSONObject("error_response").get("error_msg"));
		} else {
		    String accessToken = json.get("access_token") + "";
		    String refreshToken = json.get("refresh_token") + "";
		    if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(refreshToken)) {
			logger.error("读取pdd accessToken或者refreshToken是空");
			return false;
		    }

		    PddCacheToken token = new PddCacheToken();
		    Integer expiresIn = json.getInt("expires_in");
		    token.setAccessToken(accessToken);
		    token.setRefreshToken(refreshToken);
		    token.setDeadline(expiresIn);// 单位秒
		    token.setExpiresIn(expiresIn);
		    memcachedService.set(Constants.CacheKey.PDD_TOKEN_KEY, 0, token);
		    saveUserToken(pdd_user, accessToken, refreshToken, token.getDeadline());
		    return true;
		}
	    } catch (Exception ex) {
		logger.error("解析pdd token失败：", data);
	    }
	}
	return false;
    }
}
