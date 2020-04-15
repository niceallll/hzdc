package com.longan.biz.func;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.longan.biz.cached.MemcachedService;
import com.longan.biz.core.BaseService;
import com.longan.biz.dao.UserInfoDAO;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.FuncUtils;

public class BaseTokenService extends BaseService {
    private static PoolingHttpClientConnectionManager http_pool = new PoolingHttpClientConnectionManager();
    private static RequestConfig request_config = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(20000)
	    .setConnectionRequestTimeout(20000).build();
    private static DefaultHttpRequestRetryHandler http_handler = new DefaultHttpRequestRetryHandler(1, false);

    static {
	http_pool.setMaxTotal(128);// 连接池最大并发连接数
	http_pool.setDefaultMaxPerRoute(64);// 单路由最大并发数
    }

    @Resource
    protected UserInfoDAO userInfoDAO;

    @Resource
    protected MemcachedService memcachedService;

    protected String sendData(String url, String data, String version) {
	CloseableHttpResponse response = null;
	try {
	    HttpPost post = new HttpPost(url);
	    post.setHeader("Content-Type", "application/json");
	    if (StringUtils.isNotBlank(version)) {
		post.setHeader("version", "V1.0.0");
	    }
	    CloseableHttpClient client = HttpClients.custom().setConnectionManager(http_pool).setRetryHandler(http_handler)
		    .build();
	    post.setConfig(request_config);
	    logger.warn("请求token：" + data);
	    post.setEntity(new StringEntity(data, "utf-8"));
	    response = client.execute(post);
	    Integer status = response.getStatusLine().getStatusCode();
	    if (status != 200) {
		logger.error("发送get token失败：" + status);
	    } else {
		String result = EntityUtils.toString(response.getEntity(), "utf-8");
		logger.warn("接收token：" + result);
		return result;
	    }
	} catch (Exception ex) {
	    logger.error("发送get token异常：", ex);
	} finally {
	    try {
		if (response != null) {
		    response.close();
		}
	    } catch (Exception ex) {
	    }
	}
	return null;
    }

    protected String sendData(String url, Map<String, String> map) {
	CloseableHttpResponse response = null;
	try {
	    HttpPost post = new HttpPost(url);
	    // post.setHeader("Content-Type", "application/json");
	    CloseableHttpClient client = HttpClients.custom().setConnectionManager(http_pool).setRetryHandler(http_handler)
		    .build();
	    post.setConfig(request_config);
	    logger.warn("接口请求：" + FuncUtils.mapToString(map));
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    for (Map.Entry<String, String> m : map.entrySet()) {
		params.add(new BasicNameValuePair(m.getKey(), m.getValue()));
	    }
	    post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
	    response = client.execute(post);
	    Integer status = response.getStatusLine().getStatusCode();
	    if (status != 200) {
		logger.error("发送失败：" + status);
	    } else {
		String result = EntityUtils.toString(response.getEntity(), "utf-8");
		logger.warn("接收结果：" + result);
		return result;
	    }
	} catch (Exception ex) {
	    logger.error("发送异常：", ex);
	} finally {
	    try {
		if (response != null) {
		    response.close();
		}
	    } catch (Exception ex) {
	    }
	}
	return null;
    }

    protected Long getRestTime(Date deadline) {
	return (deadline.getTime() - System.currentTimeMillis()) / (60 * 1000);// 单位分
    }

    protected void saveUserToken(Long userId, String accessToken, String refreshToken, Date deadline) {
	try {
	    StringBuffer sb = new StringBuffer();
	    sb.append("accessToken：").append(accessToken);
	    if (StringUtils.isNotBlank(refreshToken)) {
		sb.append("，refreshToken：").append(refreshToken);
	    }
	    sb.append("，deadline：").append(DateTool.parseDates(deadline));
	    UserInfo user = new UserInfo();
	    user.setId(userId);
	    user.setReferer(sb.toString());
	    userInfoDAO.updateByPrimaryKeySelective(user);
	} catch (Exception ex) {
	    logger.error("saveUserToken异常：" + ex);
	}
    }
}
