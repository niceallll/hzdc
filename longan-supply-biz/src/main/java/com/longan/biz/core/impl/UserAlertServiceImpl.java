package com.longan.biz.core.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.core.UserAlertService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.func.BaseTokenService;
import com.longan.biz.utils.Constants;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.FuncUtils;
import com.longan.biz.utils.Utils;

public class UserAlertServiceImpl extends BaseTokenService implements UserAlertService {
    private Boolean alert_user = Utils.getBoolean("alert.user");
    private String alert_url = Utils.getProperty("alert.url");
    private String alert_key = Utils.getProperty("alert.key");

    @Override
    public String getRequestData(Long userId, String key, String type) {
	String timestamp = DateTool.parseDates2(new Date());
	String sign = FuncUtils.getUserAlertSign(alert_key, userId, timestamp);
	String data = new StringBuilder(alert_url).append(key + "?type=" + type).append("&userId=" + userId)
		.append("&timestamp=" + timestamp).append("&sign=" + sign).toString();
	return data;
    }

    @Override
    public Result<Boolean> alertRequest(Long userId, String key, String type) {
	Result<Boolean> result = new Result<Boolean>();
	if (userId == null || StringUtils.isBlank(key) || StringUtils.isBlank(type)) {
	    result.setResultMsg("入参是空");
	    return result;
	}

	try {
	    String url = alert_url + key + "?type=" + type;
	    Map<String, String> map = getRootData(userId);
	    String response = sendData(url, map);
	    if (StringUtils.isBlank(response)) {
		result.setResultMsg("请求失败");
		return result;
	    }
	    JSONObject json = JSONObject.fromObject(response);
	    String code = json.get("code") + "";
	    if (!"0000".equals(code)) {
		result.setResultMsg(json.get("message") + "");
		return result;
	    }

	    UserInfo userInfo = new UserInfo();
	    userInfo.setId(userId);
	    userInfo.setAlertStatus(getAlertStatus(type));
	    int row = userInfoDAO.updateByPrimaryKeySelective(userInfo);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (Exception ex) {
	    result.setResultMsg("预警操作异常：" + ex.getMessage());
	}
	return result;
    }

    private int getAlertStatus(String type) {
	if (type.equals("add")) {
	    return Constants.UserInfo.ASTATUS_YES;
	} else if (type.equals("open")) {
	    return Constants.UserInfo.ASTATUS_OPEN;
	} else if (type.equals("close")) {
	    return Constants.UserInfo.ASTATUS_CLOSE;
	}
	return Constants.UserInfo.ASTATUS_YES;
    }

    @Override
    public Result<Boolean> bizOrderRequest(BizOrder bizOrder) {
	Result<Boolean> result = new Result<Boolean>();
	if (bizOrder == null || bizOrder.getId() == null || bizOrder.getUserId() == null) {
	    result.setResultMsg("入参是空");
	    return result;
	}
	if (!alert_user) {
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}

	try {
	    UserInfo userInfo = localCachedService.getUserInfo(bizOrder.getUserId());
	    if (userInfo == null) {
		result.setResultMsg("用户不存在");
		return result;
	    }
	    if (userInfo.getAlertStatus() != Constants.UserInfo.ASTATUS_OPEN) {
		result.setModule(true);
		result.setSuccess(true);
		return result;
	    }

	    Integer status = bizOrder.getStatus();
	    if (status == Constants.BizOrder.STATUS_INIT) {
		String url = alert_url + "/alert/order?method=bor";
		Map<String, String> map = getRootData(bizOrder.getUserId());
		map.put("orderId", bizOrder.getId() + "");
		map.put("date", DateTool.parseDates2(bizOrder.getGmtCreate()));
		map.put("uid", bizOrder.getItemUid());
		map.put("provinceCode", bizOrder.getProvinceCode());
		map.put("itemName", bizOrder.getItemName());
		String response = sendData(url, map);
		if (!"ok".equals(response)) {
		    logger.error("预警请求失败：" + response);
		}
	    } else if (status == Constants.BizOrder.STATUS_SUCCESS || status == Constants.BizOrder.STATUS_FAILED) {
		String url = alert_url + "/alert/order?method=bos";
		Map<String, String> map = getRootData(bizOrder.getUserId());
		map.put("orderId", bizOrder.getId() + "");
		map.put("date", DateTool.parseDates2(bizOrder.getGmtCreate()));
		map.put("status", status + "");
		map.put("uid", bizOrder.getItemUid());
		map.put("provinceCode", bizOrder.getProvinceCode());
		String response = sendData(url, map);
		if (!"ok".equals(response)) {
		    logger.error("预警请求失败：" + response);
		}
	    } else {
		logger.warn("非预警状态、不处理status：" + status);
	    }
	} catch (Exception ex) {
	    logger.error("预警请求异常：", ex);
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> supplyOrderRequest(SupplyOrder supplyOrder) {
	Result<Boolean> result = new Result<Boolean>();
	if (supplyOrder == null || supplyOrder.getId() == null || supplyOrder.getSupplyTraderId() == null) {
	    result.setResultMsg("入参是空");
	    return result;
	}
	if (!alert_user) {
	    result.setModule(true);
	    result.setSuccess(true);
	    return result;
	}

	try {
	    UserInfo userInfo = localCachedService.getUserInfo(supplyOrder.getSupplyTraderId());
	    if (userInfo == null) {
		result.setResultMsg("供货商不存在");
		return result;
	    }
	    if (userInfo.getAlertStatus() != Constants.UserInfo.ASTATUS_OPEN) {
		result.setModule(true);
		result.setSuccess(true);
		return result;
	    }

	    Integer status = supplyOrder.getSupplyStatus();
	    if (status == Constants.SupplyOrder.STATUS_INIT) {
		String url = alert_url + "/alert/order?method=sor";
		Map<String, String> map = getRootData(supplyOrder.getSupplyTraderId());
		map.put("orderId", supplyOrder.getId() + "");
		map.put("date", DateTool.parseDates2(supplyOrder.getGmtCreate()));
		map.put("uid", supplyOrder.getItemUid());
		map.put("provinceCode", supplyOrder.getProvinceCode());
		map.put("itemName", supplyOrder.getItemName());
		String response = sendData(url, map);
		if (!"ok".equals(response)) {
		    logger.error("预警请求失败：" + response);
		}
	    } else if (status == Constants.SupplyOrder.STATUS_SUCCESS || status == Constants.SupplyOrder.STATUS_FAILED) {
		String url = alert_url + "/alert/order?method=sos";
		Map<String, String> map = getRootData(supplyOrder.getSupplyTraderId());
		map.put("orderId", supplyOrder.getId() + "");
		map.put("date", DateTool.parseDates2(supplyOrder.getGmtCreate()));
		map.put("status", status + "");
		map.put("uid", supplyOrder.getItemUid());
		map.put("provinceCode", supplyOrder.getProvinceCode());
		String response = sendData(url, map);
		if (!"ok".equals(response)) {
		    logger.error("预警请求失败：" + response);
		}
	    } else {
		logger.warn("非预警状态、不处理status：" + status);
	    }
	} catch (Exception ex) {
	    logger.error("预警请求异常：", ex);
	}

	result.setModule(true);
	result.setSuccess(true);
	return result;
    }

    private Map<String, String> getRootData(Long userId) {
	String timestamp = DateTool.parseDates2(new Date());
	String sign = FuncUtils.getUserAlertSign(alert_key, userId, timestamp);
	Map<String, String> map = new HashMap<String, String>();
	map.put("userId", userId + "");
	map.put("timestamp", timestamp);
	map.put("sign", sign);
	return map;
    }
}
