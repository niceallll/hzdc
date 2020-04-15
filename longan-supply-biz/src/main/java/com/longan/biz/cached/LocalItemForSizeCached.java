package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.dao.ItemDAO;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemExample;
import com.longan.biz.utils.Constants;

public class LocalItemForSizeCached extends LocalCached<String, Item> {
    private static final String SPLIT = "_";
    private static final String NATIONWIDE = "ALL";

    @Resource
    private ItemDAO itemDAO;

    @Override
    public void init() {
	super.init(3600l, 3600l);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<String, Item> cached) {
	ItemExample itemExample = new ItemExample();
	List<Integer> list = new ArrayList<Integer>();
	list.add(Constants.BizInfo.CODE_FLOW_UNICOM);
	list.add(Constants.BizInfo.CODE_FLOW_TELECOM);
	list.add(Constants.BizInfo.CODE_FLOW_MOBILE);
	itemExample.createCriteria().andStatusEqualTo(Constants.Item.STATUS_UP).andItemSizeIsNotNull().andItemRangeIsNotNull()
		.andBizIdIn(list);
	try {
	    List<Item> itemList = (List<Item>) itemDAO.selectByExample(itemExample);
	    for (Item item : itemList) {
		String key = null;
		if (item.isNationwide()) {
		    key = getNationwideCachedKey(item.getItemSize(), item.getItemRange(), item.getCarrierType());
		    cached.put(key, item);
		} else {
		    List<String> salesAreaList = item.getSalesAreaList();
		    if (salesAreaList != null) {
			for (String provinceCode : salesAreaList) {
			    key = getCachedKey(provinceCode, item.getItemSize(), item.getItemRange(), item.getCarrierType());
			    cached.put(key, item);
			}
		    }
		}
	    }
	} catch (SQLException e) {
	    logger.error("reload Item For SizeCached error", e);
	}
    }

    public static String getCachedKey(String provinceCode, Integer itemSize, Integer itemRange, Integer carrierType) {
	return provinceCode + SPLIT + itemSize + SPLIT + carrierType + itemRange;
    }

    public static String getNationwideCachedKey(Integer itemSize, Integer itemRange, Integer carrierType) {
	return NATIONWIDE + SPLIT + itemSize + SPLIT + carrierType + itemRange;
    }
}
