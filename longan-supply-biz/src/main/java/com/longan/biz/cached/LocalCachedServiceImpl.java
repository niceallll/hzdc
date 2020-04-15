package com.longan.biz.cached;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.utils.Constants;

public class LocalCachedServiceImpl extends BaseService implements LocalCachedService {
    @Resource
    private LocalAuthCached localAuthCached;

    @Resource
    private LocalAcctInfoCached localAcctInfoCached;

    @Resource
    private LocalBizAuthCached localBizAuthCached;

    @Resource
    private LocalCatalogAuthCached localCatalogAuthCached;

    @Resource
    private LocalItemCached localItemCached;

    @Resource
    private LocalItemSupplyCached localItemSupplyCached;

    @Resource
    private LocalUserInfoCached localUserInfoCached;

    @Resource
    private LocalAreaInfoCached localAreaInfoCached;

    @Resource
    private LocalTraderInfoCached localTraderInfoCached;

    @Resource
    private LocalItemForPhoneCached localItemForPhoneCached;

    @Resource
    private LocalItemForSizeCached localItemForSizeCached;

    @Resource
    private LocalOperationInfoCached localOperationInfoCached;

    @Override
    public Item getItem(Integer id) {
	if (id == null) {
	    return null;
	}
	return localItemCached.get(id);
    }

    @Override
    public Map<Integer, String> getItem() {
	Map<Integer, String> map = new HashMap<Integer, String>();
	List<Item> itemList = localItemCached.getList();
	for (Item item : itemList) {
	    map.put(item.getId(), item.getItemName());
	}
	return map;
    }

    @Override
    public Map<Integer, String> getItemByBizId(Integer bizId) {
	Map<Integer, String> map = new HashMap<Integer, String>();
	List<Item> itemList = localItemCached.getList();
	for (Item item : itemList) {
	    if (item.getBizId().equals(bizId)) {
		map.put(item.getId(), item.getItemName());
	    }
	}
	return map;
    }

    @Override
    public AcctInfo getAcctInfoNot4Trade(Long id) {
	if (id == null) {
	    return null;
	}
	return localAcctInfoCached.get(id);
    }

    @Override
    public UserInfo getUserInfo(Long id) {
	if (id == null) {
	    return null;
	}
	return localUserInfoCached.get(id);
    }

    @Override
    public Map<Long, String> getDownStreamUser() {
	Map<Long, String> map = new HashMap<Long, String>();
	List<UserInfo> userList = localUserInfoCached.getList();
	for (UserInfo user : userList) {
		//代理商
	    if (user.getType() == Constants.UserInfo.TYPE_DOWNSTREAM) {
		map.put(user.getId(), user.getUserName());
	    }
	}
	return map;
    }

    @Override
    public Map<Long, String> getUpStreamUser() {
	Map<Long, String> map = new HashMap<Long, String>();
	List<UserInfo> userList = localUserInfoCached.getList();
	for (UserInfo user : userList) {
	    if (user.getType() == Constants.UserInfo.TYPE_UPSTREAM) {
		map.put(user.getId(), user.getUserName());
	    }
	}
	return map;
    }

    @Override
    public Map<Long, String> getAdminUser() {
	Map<Long, String> map = new HashMap<Long, String>();
	List<UserInfo> userList = localUserInfoCached.getList();
	for (UserInfo user : userList) {
	    if (user.getType() == Constants.UserInfo.TYPE_ADMIN) {
		map.put(user.getId(), user.getUserName());
	    }
	}
	return map;
    }

    @Override
    public Set<String> getAuthUrlByUserId(Long id) {
	if (id == null) {
	    return null;
	}
	return localAuthCached.get(id);
    }

    @Override
    public Set<Integer> getAuthBizByUserId(Long id) {
	if (id == null) {
	    return null;
	}
	return localBizAuthCached.get(id);
    }

    @Override
    public Set<String> getAuthCatalogByUserId(Long id) {
	if (id == null) {
	    return null;
	}
	return localCatalogAuthCached.get(id);
    }

    @Override
    public ItemSupply getItemSupply(Long id) {
	if (id == null) {
	    return null;
	}
	return localItemSupplyCached.get(id);
    }

    @Override
    public Map<String, AreaInfo> getProvinceMap() {// 仅返回省
		//缓存获取
	Map<String, AreaInfo> mapAll = localAreaInfoCached.getMap();
	Map<String, AreaInfo> map = new HashMap<String, AreaInfo>();
	for (Map.Entry<String, AreaInfo> entry : mapAll.entrySet()) {
	    if (entry.getValue().getType() == Constants.AreaInfo.AREA_TYPE_PROVINCE) {
			map.put(entry.getKey(), entry.getValue());
		}
	}
	return map;
    }

    @Override
    public AreaInfo getProvinceByCode(String code) {
	if (code == null) {
	    return null;
	}
	return localAreaInfoCached.get(code);
    }

    @Override
    public TraderInfo getTraderInfo(Long userId) {
	if (userId == null) {
	    return null;
	}
	return localTraderInfoCached.get(userId);
    }

    @Override
    public Item getItemForPhone(String key) {
	if (key == null) {
	    return null;
	}
	return localItemForPhoneCached.get(key);
    }

    @Override
    public Item getItemForSize(String key) {
	if (key == null) {
	    return null;
	}
	return localItemForSizeCached.get(key);
    }

    @Override
    public OperationInfo getOperationInfo(String operationUrl) {
	if (operationUrl == null) {
	    return null;
	}
	return localOperationInfoCached.get(operationUrl);
    }

    @Override
    public List<TraderInfo> getTraderInfoList() {
	return localTraderInfoCached.getList();
    }
}
