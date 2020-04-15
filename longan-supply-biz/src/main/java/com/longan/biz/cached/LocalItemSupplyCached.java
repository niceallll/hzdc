package com.longan.biz.cached;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import com.longan.biz.dao.ItemSupplyDAO;
import com.longan.biz.dao.TraderInfoDAO;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.ItemSupplyExample;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.TraderInfoExample;
import com.longan.biz.utils.Constants;

public class LocalItemSupplyCached extends LocalCached<Long, ItemSupply> {
    @Resource
    private ItemSupplyDAO itemSupplyDAO;

    @Resource
    private TraderInfoDAO traderInfoDAO;

    @Override
    public void init() {
	super.init(60l, 60l);
    }

    @SuppressWarnings("unchecked")
    @Override
    void reloadFromDb(ConcurrentMap<Long, ItemSupply> cached) {
	ItemSupplyExample itemSupplyExample = new ItemSupplyExample();
	itemSupplyExample.createCriteria().andStatusNotEqualTo(Constants.ItemSupply.STATUS_DEL);
	try {
	    List<ItemSupply> itemSupplyList = (List<ItemSupply>) itemSupplyDAO.selectByExample(itemSupplyExample);
	    Map<Long, TraderInfo> tempMap = new HashMap<Long, TraderInfo>();

	    for (ItemSupply itemSupply : itemSupplyList) {
		TraderInfo traderInfo = tempMap.get(itemSupply.getSupplyTraderId());
		if (traderInfo == null) {
		    TraderInfoExample example = new TraderInfoExample();
		    example.createCriteria().andUserIdEqualTo(itemSupply.getSupplyTraderId());
		    List<TraderInfo> list = (List<TraderInfo>) traderInfoDAO.selectByExample(example);
		    if (list != null && !list.isEmpty()) {
			traderInfo = list.get(0);
		    }
		}

		if (traderInfo != null) {
		    tempMap.put(itemSupply.getSupplyTraderId(), traderInfo);
		    itemSupply.setItemSupplyType(traderInfo.getSupplyType());
		    itemSupply.setIsAsyncSupply(traderInfo.getIsAsyncSupply());
		}
		cached.put(itemSupply.getId(), itemSupply);
	    }
	} catch (SQLException e) {
	    logger.error("reload ItemSupply", e);
	}
    }
}
