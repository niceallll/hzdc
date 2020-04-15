package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.dao.ItemDAO;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemExample;
import com.longan.biz.utils.Constants;

public class LocalItemForPhoneCached extends LocalCached<String, Item> {
    private static final String SPLIT = "_";
    private static final String NATIONWIDE = "ALL";
    private static final String SHOPID = "SID";

    @Resource
    private ItemDAO itemDAO;

    @Override
    public void init() {
	super.init(300L, 300L);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<String, Item> cached) {
	ItemExample itemExample = new ItemExample();
	List<Integer> list = new ArrayList<Integer>();
	list.add(Constants.BizInfo.CODE_PHONE_MOBILE);
	list.add(Constants.BizInfo.CODE_PHONE_TELECOM);
	list.add(Constants.BizInfo.CODE_PHONE_UNICOM);
	itemExample.createCriteria().andStatusEqualTo(Constants.Item.STATUS_UP).andBizIdIn(list);
	try {
	    List<Item> itemList = (List<Item>) itemDAO.selectByExample(itemExample);
	    for (Item item : itemList) {
		Long userId = item.getUserId();
		if (item.isNationwide()) {
		    if (userId != null && userId != 0) {
			cached.put(getNationwideCachedKey2(userId, item.getItemFacePrice(), item.getCarrierType()), item);
			// 临时使用itemUnit
			if (StringUtils.isNotBlank(item.getItemUnit())) {
			    cached.put(getShopCachedKey(userId, item.getItemUnit()), item);
			}
		    } else {
			cached.put(getNationwideCachedKey1(item.getItemFacePrice(), item.getCarrierType()), item);
		    }
		} else {
		    List<String> salesAreaList = item.getSalesAreaList();
		    if (salesAreaList != null) {
			for (String provinceCode : salesAreaList) {
			    if (userId != null && userId != 0) {
				cached.put(getCachedKey2(userId, provinceCode, item.getItemFacePrice(), item.getCarrierType()),
					item);
				// 临时使用itemUnit
				if (StringUtils.isNotBlank(item.getItemUnit())) {
				    cached.put(getShopCachedKey(userId, item.getItemUnit()), item);
				}
			    } else {
				cached.put(getCachedKey1(provinceCode, item.getItemFacePrice(), item.getCarrierType()), item);
			    }
			}
		    }
		}
	    }
	} catch (SQLException e) {
	    logger.error("reload Item For PhoneCached error", e);
	}
    }

    public static String getCachedKey1(String provinceCode, Integer itemFacePrice, Integer carrierType) {
	return provinceCode + SPLIT + itemFacePrice + SPLIT + carrierType;
    }

    public static String getCachedKey2(Long userId, String provinceCode, Integer itemFacePrice, Integer carrierType) {
	return userId + SPLIT + provinceCode + SPLIT + itemFacePrice + SPLIT + carrierType;
    }

    public static String getNationwideCachedKey1(Integer itemFacePrice, Integer carrierType) {
	return NATIONWIDE + SPLIT + itemFacePrice + SPLIT + carrierType;
    }

    public static String getNationwideCachedKey2(Long userId, Integer itemFacePrice, Integer carrierType) {
	return NATIONWIDE + SPLIT + userId + SPLIT + itemFacePrice + SPLIT + carrierType;
    }

    public static String getShopCachedKey(Long userId, String itemUnit) {
	return SHOPID + SPLIT + userId + SPLIT + itemUnit;
    }
}
