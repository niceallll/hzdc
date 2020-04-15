package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.dao.ItemDAO;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemExample;
import com.longan.biz.utils.Constants;

public class LocalItemCached extends LocalCached<Integer, Item> {
    @Resource
    private ItemDAO itemDAO;

    @Override
    public void init() {
	super.init(180L, 180L);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<Integer, Item> cached) {
	ItemExample itemExample = new ItemExample();
	itemExample.createCriteria().andStatusNotEqualTo(Constants.Item.STATUS_DEL);
	try {
	    List<Item> itemList = (List<Item>) itemDAO.selectByExample(itemExample);
	    for (Item item : itemList) {
		cached.put(item.getId(), item);
	    }
	} catch (SQLException e) {
	    logger.error("reload Item", e);
	}
    }
}
