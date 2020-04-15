package com.longan.biz.pojo;

import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.longan.biz.utils.Md5Encrypt;

public class PddItemApi extends PddBaseApi {
    private final static String type = "pdd.goods.sku.price.update";

    private Long goodsId;
    private Integer isOnsale;
    private Long singlePrice;
    private Long groupPrice;
    private Long skuId;

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

    public Long getSinglePrice() {
	return singlePrice;
    }

    public void setSinglePrice(Long singlePrice) {
	this.singlePrice = singlePrice;
    }

    public Long getGroupPrice() {
	return groupPrice;
    }

    public void setGroupPrice(Long groupPrice) {
	this.groupPrice = groupPrice;
    }

    public Long getSkuId() {
	return skuId;
    }

    public void setSkuId(Long skuId) {
	this.skuId = skuId;
    }

    public Map<String, String> getMap() {
	return map;
    }

    public void createSign() {
	StringBuffer sb = new StringBuffer();
	sb.append(getSecret()).append("access_token" + getAccessToken()).append("client_id" + getClient())
		.append("data_type" + getDataType()).append("goods_id" + goodsId).append("sku_price_list" + getJson())
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
	map.put("sku_price_list", getJson());
	map.put("sign", getSign());
    }

    private String getJson() {
	JSONObject json = new JSONObject();
	json.put("is_onsale", isOnsale);
	json.put("single_price", singlePrice);
	json.put("group_price", groupPrice);
	json.put("sku_id", skuId);
	return "[" + json.toString() + "]";
    }
}
