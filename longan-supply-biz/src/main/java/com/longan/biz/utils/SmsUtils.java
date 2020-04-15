package com.longan.biz.utils;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.domain.JsonResponse;

public class SmsUtils {
    public static boolean rightFormat(String mobile, String text) {
	if (StringUtils.isBlank(text)) {
	    return false;
	}
	return single(mobile, text) || batch(mobile, text) || multi(mobile, text);
    }

    public static boolean single(String mobile, String text) {
	return StringUtils.split(mobile, Constants.BUY_REQUEST_SPLIT).length == 1
		&& StringUtils.split(text, Constants.BUY_REQUEST_SPLIT).length == 1;
    }

    public static boolean batch(String mobile, String text) {
	return StringUtils.split(mobile, Constants.BUY_REQUEST_SPLIT).length > 1
		&& StringUtils.split(text, Constants.BUY_REQUEST_SPLIT).length == 1;
    }

    public static boolean multi(String mobile, String text) {
	return StringUtils.split(mobile, Constants.BUY_REQUEST_SPLIT).length > 1
		&& StringUtils.split(mobile, Constants.BUY_REQUEST_SPLIT).length == StringUtils.split(text,
			Constants.BUY_REQUEST_SPLIT).length;
    }

    public static int itemCount(String str) {
	return StringUtils.split(str, Constants.BUY_REQUEST_SPLIT).length;
    }

    public static String[] itemList(String str) {
	return StringUtils.split(str, Constants.BUY_REQUEST_SPLIT);
    }

    public static int totalCount(String smsUid, String smsText) {
	int totalCount = 0;
	if (single(smsUid, smsText)) {
	    totalCount = smsCount(smsText);
	} else if (batch(smsUid, smsText)) {
	    totalCount = itemCount(smsUid) * smsCount(smsText);
	} else {
	    String[] texts = StringUtils.split(smsText);
	    for (String text : texts) {
		totalCount += smsCount(text);
	    }
	}
	return totalCount;
    }

    public static int smsCount(String text) {
	int count = 1;
	try {
	    text = URLDecoder.decode(text, "UTF-8");
	    int length = StringUtils.length(text);
	    if (length > 70) {
		if (length % 67 == 0) {
		    count = length / 67;
		} else {
		    count = length / 67 + 1;
		}
	    }
	} catch (Exception ex) {
	}
	return count;
    }

    public static String getText(String text) {
	try {
	    text = URLDecoder.decode(text, "UTF-8");
	} catch (Exception ex) {
	}
	return text;
    }

    public static JsonResponse getRightCode(JsonResponse response, String desc) {
	response.setCode(Constants.ErrorCode.CODE_SUCCESS);
	if (StringUtils.isEmpty(desc)) {
	    response.setDesc(Constants.ErrorCode.DESC_SUCCESS);
	} else {
	    response.setDesc(desc);
	}
	return response;
    }

    public static JsonResponse getErrResponse(String code, String desc) {
	JsonResponse response = new JsonResponse();
	response.setCode(code);
	response.setDesc(desc);
	return response;
    }

    public static Integer computCostTime(Date date) {
	if (date != null) {
	    Long costTime = (System.currentTimeMillis() - date.getTime()) / 1000;
	    if (costTime == 0) {
		costTime = 1L;
	    }
	    return costTime.intValue();
	}
	return null;
    }

    public static void setSupplyFail(List<SmsSupply> supplyList, String code, String msg) {
	for (SmsSupply smsSupply : supplyList) {
	    smsSupply.setSupplyStatus(Constants.SupplyOrder.STATUS_FAILED);
	    smsSupply.setUpstreamMemo(code + ":" + msg);
	}
    }

    public static void setSupplyFail(SmsSupply smsSupply, String code, String msg) {
	smsSupply.setSupplyStatus(Constants.SupplyOrder.STATUS_FAILED);
	smsSupply.setUpstreamMemo(code + ":" + msg);
    }

    public static void setSupplyUnConfirm(List<SmsSupply> supplyList, String code, String msg) {
	for (SmsSupply smsSupply : supplyList) {
	    smsSupply.setSupplyStatus(Constants.SupplyOrder.STATUS_CHARGING);// 暂作成功处理
	    smsSupply.setUpstreamMemo(code + ":" + msg);
	}
    }

    public static void setSupplyUnConfirm(SmsSupply smsSupply, String code, String msg) {
	smsSupply.setSupplyStatus(Constants.SupplyOrder.STATUS_CHARGING);// 暂作成功处理
	smsSupply.setUpstreamMemo(code + ":" + msg);
    }

    public static void checkResultCount(int succ, SmsOrder smsOrder) {
	if (smsOrder.getSuccCount() != succ) {
	    smsOrder.setSuccCount(succ);
	    smsOrder.setFailCount(smsOrder.getTotalCount() - succ);
	    if (succ != 0) {
		smsOrder.setAmount(succ * smsOrder.getItemPrice() + 0l);
		smsOrder.setCostPrice(succ * smsOrder.getItemCostPrice() + 0l);
	    }
	}
    }

    public static void checkResultCount(int succ, SmsSupply smsSupply) {
	if (succ != 0 && smsSupply.getCount() != succ) {
	    smsSupply.setCount(succ);
	    smsSupply.setAmount(succ * smsSupply.getItemPrice() + 0l);
	    smsSupply.setCostPrice(succ * smsSupply.getItemCostPrice() + 0l);
	}
    }

    public static String getSystemExtend(String extend) {
	return "00" + extend.substring(0, 2);
    }

    public static String getCustomExtend(String extend) {
	return extend.substring(2);
    }

    public static Long getUserIdByExtend(String extend, List<TraderInfo> list) {
	for (TraderInfo traderInfo : list) {
	    if (extend.equals(traderInfo.getSmsExtend())) {
		return traderInfo.getUserId();
	    }
	}
	return null;
    }

    public static String getSmsCode() {
	String num = "0123456789";
	char[] rands = new char[6];
	for (int i = 0; i < 6; i++) {
	    int rand = (int) (Math.random() * 10);
	    rands[i] = num.charAt(rand);
	}
	return new String(rands);
    }
}
