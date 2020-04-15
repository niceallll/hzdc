package com.longan.biz.cached;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.AreaInfo;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.OperationInfo;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.UserInfo;

public interface LocalCachedService {
    public Item getItem(Integer id);

    public Map<Integer, String> getItem();

    public Map<Integer, String> getItemByBizId(Integer bizId);

    public ItemSupply getItemSupply(Long id);

    public AcctInfo getAcctInfoNot4Trade(Long id);

    public UserInfo getUserInfo(Long id);

    public Map<Long, String> getDownStreamUser();

    public Map<Long, String> getUpStreamUser();

    public Map<Long, String> getAdminUser();

    public Set<String> getAuthUrlByUserId(Long id);

    public Set<Integer> getAuthBizByUserId(Long id);

    public Set<String> getAuthCatalogByUserId(Long id);

    public Map<String, AreaInfo> getProvinceMap();

    public AreaInfo getProvinceByCode(String code);

    public TraderInfo getTraderInfo(Long userId);

    public Item getItemForPhone(String key);

    public Item getItemForSize(String key);

    public OperationInfo getOperationInfo(String operationUrl);

    public List<TraderInfo> getTraderInfoList();
}
