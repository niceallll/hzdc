package com.longan.biz.pojo;

import java.util.Map;
import java.util.TreeMap;

import com.longan.biz.utils.Md5Encrypt;

public class PddItemsApi extends PddBaseApi {
    private final static String type = "pdd.goods.sale.status.set";

    private Long goodsId;
    private Integer isOnsale;

    private Map<String, String> map = new TreeMap<String, String>();

    public Long getGoodsId() {
	return goodsId;
    }

    public void setGoodsId(Long goodsId) {
	this.goodsId = goodsId;
    }

    public Integer getIsOnsale() {
	return isOnsale;
    }

    public void setIsOnsale(Integer isOnsale) {
	this.isOnsale = isOnsale;
    }

    public Map<String, String> getMap() {
	return map;
    }

    public void createSign() {
	StringBuffer sb = new StringBuffer();
	sb.append(getSecret()).append("access_token" + getAccessToken()).append("client_id" + getClient())
		.append("data_type" + getDataType()).append("goods_id" + goodsId).append("is_onsale" + isOnsale)
		.append("timestamp" + getTimestamp()).append("type" + type).append(getSecret());
	String signStr = Md5Encrypt.md5(sb.toString()).toUpperCase();
	setSign(signStr);
    }

    public void createMap() {
	map.put("type", type);
	map.put("client_id", getClient());
	map.put("access_token", getAccessToken());
	map.put("timestamp", getTimestamp() + "");
	map.put("data_type", getDataType());
	map.put("goods_id", goodsId + "");
	map.put("is_onsale", isOnsale + "");
	map.put("sign", getSign());
    }
}
