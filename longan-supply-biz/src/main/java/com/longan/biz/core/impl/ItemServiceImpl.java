package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.longan.biz.cached.LocalCachedService;
import com.longan.biz.cached.MemcachedService;
import com.longan.biz.core.AcctService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.ChargingLimitService;
import com.longan.biz.core.ItemService;
import com.longan.biz.dao.ItemDAO;
import com.longan.biz.dao.ItemSalesDAO;
import com.longan.biz.dao.ItemSupplyDAO;
import com.longan.biz.dao.TraderInfoDAO;
import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemExample;
import com.longan.biz.dataobject.ItemQuery;
import com.longan.biz.dataobject.ItemSales;
import com.longan.biz.dataobject.ItemSalesQuery;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.ItemSupplyExample;
import com.longan.biz.dataobject.ItemSupplyExample.Criteria;
import com.longan.biz.dataobject.ItemSupplyQuery;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.TraderInfo;
import com.longan.biz.dataobject.TraderInfoExample;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;
import com.longan.biz.utils.CachedUtils;
import com.longan.biz.utils.Constants;

public class ItemServiceImpl extends BaseService implements ItemService {
    @Resource
    private ItemDAO itemDAO;

    @Resource
    private ItemSupplyDAO itemSupplyDAO;

    @Resource
    private ItemSalesDAO itemSalesDAO;

    @Resource
    private LocalCachedService localCachedService;

    @Resource
    private MemcachedService memcachedService;

    @Resource
    private TraderInfoDAO traderInfoDAO;

    @Resource
    private AcctService acctService;

    @Resource
    private ChargingLimitService chargingLimitService;

    private final static long MAX_REPEAT_TIME = 60 * 60 * 1000; // 默认 60分钟
    private final static int MAX_REPEAT_COUNT = 6; // 默认6次
    private static final Integer ITEM_PRICE_EXP = 5 * 60;// 5 * 60; // 5分种

    @Override
    public Result<Item> createNewItem(Item item) {
	Result<Item> result = new Result<Item>();
	try {
	    item.setStatus(Constants.Item.STATUS_INIT);
	    Integer id = itemDAO.insert(item);
	    if (id != null) {
		item.setId(id);
		result.setSuccess(true);
		result.setModule(item);
	    } else {
		result.setResultMsg("新增商品失败");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("新增商品失败 msg :" + e.getMessage());
	    logger.error("createNewItem error itemName : " + item.getItemName(), e);
	}
	return result;
    }
	//返回结果集的商品
    @Override
    public Result<Item> getItem(Integer itemId) {
	Result<Item> result = new Result<Item>();
	try {
	    Item item = itemDAO.selectByPrimaryKey(itemId);
	    if (item != null) {
		result.setSuccess(true);
		result.setModule(item);
	    } else {
		result.setResultMsg("没有该商品  itemId : " + itemId);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("查询商品失败 ： msg :" + e.getMessage());
	    logger.error("getItem error itemId : " + itemId, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<Item>> queryItemList(Integer bizId) {
	Result<List<Item>> result = new Result<List<Item>>();
	ItemExample itemExample = new ItemExample();//list集合
	//查询条件封装
	itemExample.createCriteria().andBizIdEqualTo(bizId).andStatusNotEqualTo(Constants.Item.STATUS_DEL);
	try {
	    List<Item> list = (List<Item>) itemDAO.selectByExample(itemExample);
	    result.setSuccess(true);
	    result.setModule(list);
	} catch (SQLException e) {
	    result.setResultMsg("查询商品列表失败  msg :" + e.getMessage());
	    logger.error("queryItemList error bizId : " + bizId, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<Item>> queryItemList(Long userId, Integer bizId, String provinceCode) {
	Result<List<Item>> result = new Result<List<Item>>();
	ItemExample itemExample = new ItemExample();
	if (provinceCode == null) {
	    itemExample.createCriteria().andUserIdEqualTo(userId).andBizIdEqualTo(bizId);
	} else {
	    itemExample.createCriteria().andUserIdEqualTo(userId).andBizIdEqualTo(bizId).andSalesAreaEqualTo(provinceCode);
	}

	try {
	    List<Item> list = (List<Item>) itemDAO.selectByExample(itemExample);
	    result.setSuccess(true);
	    result.setModule(list);
	} catch (SQLException e) {
	    result.setResultMsg("查询商品列表失败  msg :" + e.getMessage());
	    logger.error("queryItemList error bizId : " + bizId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> upItem(Integer itemId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    Item item = new Item();
	    item.setStatus(Constants.Item.STATUS_UP);
	    item.setId(itemId);
	    int row = itemDAO.updateByPrimaryKeySelective(item);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("商品上架失败，没有该商品 id : " + itemId);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("商品上架失败 msg :" + e.getMessage());
	    logger.error("up Item error itemId : " + itemId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> downItem(Integer itemId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    Item item = new Item();
	    item.setStatus(Constants.Item.STATUS_DOWN);
	    item.setId(itemId);
	    int row = itemDAO.updateByPrimaryKeySelective(item);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("商品下架失败，没有该商品 id : " + itemId);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("商品下架失败   msg :" + e.getMessage());
	    logger.error("down Item error  itemId : " + itemId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> hasCombinedItem(Integer itemId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    ItemExample example = new ItemExample();
	    example.createCriteria().andItemCategoryIdEqualTo(itemId);
	    int count = itemDAO.countByExample(example);
	    if (count > 0) {
		result.setResultMsg("商品已经用于其它商品的拆分、不允许嵌套拆分");
		result.setModule(true);
		return result;
	    }
	    result.setSuccess(true);
	} catch (SQLException e) {
	    result.setResultMsg("商品检查失败   msg :" + e.getMessage());
	    logger.error("has combined Item error itemId : " + itemId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> toCombineItem(Integer itemId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    Item item = new Item();
	    item.setId(itemId);
	    item.setStatus(Constants.Item.STATUS_DOWN);
	    item.setItemType(Constants.Item.TYPE_ITEM_COMBINE);
	    item.setCombineType(Constants.Item.COMBINE_TYPE_YES);
	    int row = itemDAO.updateByPrimaryKeySelective(item);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("商品转拆分失败，没有该商品 id : " + itemId);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("商品转拆分失败   msg :" + e.getMessage());
	    logger.error("to combine Item error itemId : " + itemId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> toChargeItem(Integer itemId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    Item item = new Item();
	    item.setId(itemId);
	    item.setStatus(Constants.Item.STATUS_DOWN);
	    item.setItemType(Constants.Item.TYPE_ITEM_CHARGE);
	    item.setCombineType(Constants.Item.COMBINE_TYPE_NO);
	    item.setItemCategoryId(0);
	    int row = itemDAO.updateByPrimaryKeySelective(item);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("商品转直充失败，没有该商品 id : " + itemId);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("商品转直充失败   msg :" + e.getMessage());
	    logger.error("to charge Item error itemId : " + itemId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> toCardItem(Integer itemId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    Item item = new Item();
	    item.setId(itemId);
	    item.setStatus(Constants.Item.STATUS_DOWN);
	    item.setItemType(Constants.Item.TYPE_ITEM_CARD);
	    item.setCombineType(Constants.Item.COMBINE_TYPE_NO);
	    item.setItemCategoryId(0);
	    int row = itemDAO.updateByPrimaryKeySelective(item);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("商品转卡密失败，没有该商品 id : " + itemId);
	    }
	} catch (SQLException e) {
	    result.setResultMsg("商品转卡密失败   msg :" + e.getMessage());
	    logger.error("to card Item error itemId : " + itemId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateItem(Item item) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    int row = itemDAO.updateByPrimaryKeySelective(item);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("更新商品失败    msg: " + e.getMessage());
	    logger.error("updateItem error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> upItem(List<Integer> ids) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    int row = itemDAO.batchUpdateStatusItem(ids, Constants.Item.STATUS_UP);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("批量上架商品失败   数据库异常");
	    logger.error("upItem error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> downItem(List<Integer> ids) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    int row = itemDAO.batchUpdateStatusItem(ids, Constants.Item.STATUS_DOWN);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("批量下架商品失败   数据库异常");
	    logger.error("downItem error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> upItemSupply(List<Integer> ids) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    int row = itemSupplyDAO.batchUpdateStatusItemSupply(ids, Constants.ItemSupply.STATUS_UP);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("批量下架供货商品失败   数据库异常");
	    logger.error("upItemSupply error ", e);
	}
	return result;
    }

    @Override
    public Result<Boolean> downItemSupply(List<Integer> ids) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    int row = itemSupplyDAO.batchUpdateStatusItemSupply(ids, Constants.ItemSupply.STATUS_DOWN);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("批量下架供货商品失败   数据库异常");
	    logger.error("downItemSupply error ", e);
	}
	return result;
    }

    @Override
    public Result<Integer> getSalesPrice(Item item, AcctInfo acctInfo) {
	Result<Integer> result = new Result<Integer>();
	Integer itemSalePrice = null;
	if (acctInfo == null || item == null) {
	    result.setResultMsg("item or acctInfo is null");
	    return result;
	}

	Long userId = acctInfo.getUserId();
	//商品id
	Integer itemId = item.getId();
	//拼接一个唯一键
	String itemPriceKey = CachedUtils.getItemPriceKey(userId, itemId);
	//从缓存里面获取
	itemSalePrice = memcachedService.getIntValue(itemPriceKey);
	if (itemSalePrice == null) {//缓存记录
	    itemSalePrice = -1;
	    try {
	    //查询状态
		ItemSales itemSales = itemSalesDAO.selectByIndex(userId, itemId);
		if (itemSales != null) {
		    itemSalePrice = itemSales.getItemSalesPrice();
		}
		//设置缓存
		memcachedService.set(itemPriceKey, ITEM_PRICE_EXP, itemSalePrice);
	    } catch (Exception ex) {
		logger.error("getSalesPrice error ", ex);
	    }
	}

	if (itemSalePrice <= 0) {// 临时方案，后期商品未开通处理
	    if (acctInfo.getSalesPrice() == null || acctInfo.getSalesPrice() == Constants.AcctInfo.SALES_PRICE_1) {
		itemSalePrice = item.getItemSalesPrice();
	    } else if (acctInfo.getSalesPrice() == Constants.AcctInfo.SALES_PRICE_2) {
		itemSalePrice = item.getItemSalesPrice2();
	    } else if (acctInfo.getSalesPrice() == Constants.AcctInfo.SALES_PRICE_3) {
		itemSalePrice = item.getItemSalesPrice3();
	    } else if (acctInfo.getSalesPrice() == Constants.AcctInfo.SALES_PRICE_4) {
		itemSalePrice = item.getItemSalesPrice4();
	    }

	    // result.setResultCode(Constants.ErrorCode.CODE_ERROR_ITEM_SALES);
	    // result.setResultMsg(Constants.ErrorCode.DESC_ERROR_ITEM_SALES);
	    // return result;
	}

	if (itemSalePrice == null) {
	    result.setResultMsg("商品渠道价格未配置");
	    return result;
	}

	result.setModule(itemSalePrice);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> checkItem(BizOrder bizOrder) {
	Result<Boolean> result = new Result<Boolean>();

	// 验证价格
	Result<Boolean> checkPrice = checkPrice(bizOrder);
	if (!checkPrice.isSuccess() || !checkPrice.getModule()) {
	    result.setResultMsg(checkPrice.getResultMsg());
	    return result;
	}

	Result<Boolean> checkItemStatusResult = null;
	if (bizOrder.isCombine()) {
	    // 拆分商品、验证子商品状态
	    checkItemStatusResult = checkItemStatus(bizOrder.getItemCategoryId());
	} else {
	    // 验证状态
	    checkItemStatusResult = checkItemStatus(bizOrder.getItemId());
	}

	if (!checkItemStatusResult.isSuccess() || !checkItemStatusResult.getModule()) {
	    result.setResultMsg(checkItemStatusResult.getResultMsg());
	    return result;
	}

	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    public Result<Boolean> checkPrice(BizOrder bizOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (bizOrder == null || bizOrder.getItemId() == null || bizOrder.getUserId() == null) {
	    result.setResultMsg("验证价格失败，没有相关信息。");
	    return result;
	}

	Item item = null;
	try {
	    item = itemDAO.selectByPrimaryKey(bizOrder.getItemId());
	} catch (SQLException e) {
	    logger.error("queryItem error", e);
	    result.setResultMsg("查询商品失败 msg : " + e.getMessage());
	    return result;
	}

	UserInfo userInfo = localCachedService.getUserInfo(bizOrder.getUserId());
	if (userInfo == null) {
	    result.setResultMsg("验证价格失败，没有用户信息。");
	    return result;
	}

	Result<AcctInfo> acctInfoResult = acctService.getAcctInfo(userInfo.getAcctId());
	if (!acctInfoResult.isSuccess() || acctInfoResult.getModule() == null) {
	    result.setResultMsg("账户信息为空。");
	    return result;
	}
	AcctInfo acctInfo = acctInfoResult.getModule();

	Result<Integer> priceResult = getSalesPrice(item, acctInfo);
	if (!priceResult.isSuccess()) {
	    result.setResultMsg(priceResult.getResultMsg());
	    return result;
	}
	Integer price = priceResult.getModule();

	if (acctInfo == null || item == null || price == null) {
	    result.setResultMsg("验证价格失败，没有相关信息。");
	    return result;
	}
	boolean flag = bizOrder.getItemPrice().equals(price)
		&& bizOrder.getItemPrice() * bizOrder.getAmt() == bizOrder.getAmount();
	result.setSuccess(true);
	result.setModule(flag);
	if (!flag) {
	    result.setResultMsg("验证价格失败，价格设置错误。");
	}
	return result;
    }

    @Override
    public Result<Boolean> checkItemStatus(Integer itemId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (itemId == null) {
	    result.setResultMsg("商品状态验证失败，商品编号为空");
	    return result;
	}

	Item item = null;
	try {
	    item = itemDAO.selectByPrimaryKey(itemId);
	} catch (SQLException e) {
	    logger.error("queryItem error", e);
	    result.setResultMsg("查询商品失败 msg : " + e.getMessage());
	    return result;
	}

	result.setSuccess(true);
	if (item.getStatus() != Constants.Item.STATUS_UP) {
	    result.setModule(false);
	    result.setResultMsg("商品未上架");
	    return result;
	}

	Result<List<ItemSupply>> supplyResult = queryItemSupplyOnSale(item.getId());
	if (!supplyResult.isSuccess() || supplyResult.getModule() == null || supplyResult.getModule().size() <= 0) {
	    result.setModule(false);
	    result.setResultMsg("供货商品未上架");
	    return result;
	}
	result.setModule(true);
	return result;
    }

    @Override
    public Result<List<Item>> queryItemList(ItemQuery itemQuery) {
	Result<List<Item>> result = new Result<List<Item>>();
	try {
	    List<Item> queryResult = itemDAO.queryByPage(itemQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("商品查询失败    msg: " + e.getMessage());
	    logger.error("queryItemList error ", e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<ItemSupply>> queryItemSupplyOnSale(Integer itemId) {
	Result<List<ItemSupply>> result = new Result<List<ItemSupply>>();
	ItemSupplyExample example = new ItemSupplyExample();
	example.createCriteria().andItemIdEqualTo(itemId).andStatusEqualTo(Constants.ItemSupply.STATUS_UP);
	example.setOrderByClause("priority");

	try {
	    List<ItemSupply> list = (List<ItemSupply>) itemSupplyDAO.selectByExample(example);
	    result.setSuccess(true);
	    result.setModule(list);
	} catch (SQLException e) {
	    result.setResultMsg("供货商品查询失败    msg: " + e.getMessage());
	    logger.error("queryItemSupplyOnSale error ", e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<ItemSupply>> queryItemSupplyByBizId(Integer bizId) {
	Result<List<ItemSupply>> result = new Result<List<ItemSupply>>();
	ItemSupplyExample example = new ItemSupplyExample();
	example.createCriteria().andBizIdEqualTo(bizId).andStatusEqualTo(Constants.ItemSupply.STATUS_UP);

	try {
	    List<ItemSupply> list = (List<ItemSupply>) itemSupplyDAO.selectByExample(example);
	    result.setSuccess(true);
	    result.setModule(list);
	} catch (SQLException e) {
	    result.setResultMsg("供货商品查询失败    msg: " + e.getMessage());
	    logger.error("queryItemSupplyByBizId error ", e);
	}
	return result;
    }

    @Override
    public Result<List<ItemSupply>> queryStockItemSupplyByBiz(Integer bizId) {
	Result<List<ItemSupply>> result = new Result<List<ItemSupply>>();
	Result<List<ItemSupply>> itemSupplyResult = queryItemSupplyByBizId(bizId);
	if (!itemSupplyResult.isSuccess()) {
	    result.setResultMsg(itemSupplyResult.getResultMsg());
	    return result;
	}

	List<ItemSupply> itemSupplyList = itemSupplyResult.getModule();
	List<ItemSupply> list = new ArrayList<ItemSupply>();
	if (itemSupplyList != null) {
	    for (ItemSupply itemSupply : itemSupplyList) {
		TraderInfo traderInfo = localCachedService.getTraderInfo(itemSupply.getSupplyTraderId());
		if (traderInfo != null) {
		    itemSupply.setItemSupplyType(traderInfo.getSupplyType());
		    itemSupply.setIsAsyncSupply(traderInfo.getIsAsyncSupply());
		    if (itemSupply.hasStock()) {
			list.add(itemSupply);
		    }
		}
	    }
	}
	result.setSuccess(true);
	result.setModule(list);
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<ItemSupply>> queryItemSuppyByItemSetting(Item item) {
	Result<List<ItemSupply>> result = new Result<List<ItemSupply>>();
	ItemSupplyExample example = new ItemSupplyExample();
	Criteria c = example.createCriteria();
	c.andItemIdEqualTo(item.getId()).andStatusEqualTo(Constants.ItemSupply.STATUS_UP);
	if (item.getSupplyFilterType() != null && item.getSupplyFilterType() == Constants.Item.SUPPLY_FILTER_TYPE_PRIORITY) {
	    example.setOrderByClause("priority");
	} else {
	    example.setOrderByClause("item_cost_price");
	    c.andItemCostPriceIsNotNull();
	}

	try {
	    List<ItemSupply> list = (List<ItemSupply>) itemSupplyDAO.selectByExample(example);
	    if (list != null) {
		for (ItemSupply itemSupply : list) {
		    TraderInfo traderInfo = localCachedService.getTraderInfo(itemSupply.getSupplyTraderId());
		    if (traderInfo != null) {
			itemSupply.setItemSupplyType(traderInfo.getSupplyType());
			itemSupply.setIsAsyncSupply(traderInfo.getIsAsyncSupply());
		    }
		}
	    }
	    result.setSuccess(true);
	    result.setModule(list);
	} catch (SQLException e) {
	    result.setResultMsg("供货商品查询失败    msg: " + e.getMessage());
	    logger.error("queryItemSuppyByItemSetting error ", e);
	}
	return result;
    }

    @Override
    public Result<ItemSupply> filterItemSupply(Item item, BizOrder bizOrder) {
	Result<ItemSupply> result = new Result<ItemSupply>();
	if (item == null || bizOrder == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	Result<List<ItemSupply>> queryItemSuppyByItemSettingResult = queryItemSuppyByItemSetting(item);
	if (!queryItemSuppyByItemSettingResult.isSuccess() || queryItemSuppyByItemSettingResult.getModule() == null) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_FAILD);
	    result.setResultMsg(queryItemSuppyByItemSettingResult.getResultMsg());
	    return result;
	}
	if (queryItemSuppyByItemSettingResult.getModule().isEmpty()) {
	    logger.error("itemSupply is null item id : " + item.getId());
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_FAILD);
	    result.setResultMsg("供货商品可能已经下架");
	    return result;
	}

	Result<Boolean> checkRepeatResult = checkRepeat(item, bizOrder);
	if (!checkRepeatResult.isSuccess()) {
	    result.setResultCode(checkRepeatResult.getResultCode());
	    result.setResultMsg(checkRepeatResult.getResultMsg());
	    return result;
	}

	Result<ItemSupply> filerResult = filterItemSupplyList(queryItemSuppyByItemSettingResult.getModule(), bizOrder);
	if (!filerResult.isSuccess()) {
	    result.setResultCode(filerResult.getResultCode());
	    result.setResultMsg(filerResult.getResultMsg());
	    return result;
	}
	ItemSupply itemSupply = filerResult.getModule();
	if (itemSupply == null) {
	    logger.error("itemSupply is null item id : " + item.getId());
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_FAILD);
	    result.setResultMsg("itemSupply is null");
	    return result;
	}

	result.setModule(itemSupply);
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<ItemSupply> filterItemSupply(Item item, SmsOrder smsOrder) {
	Result<ItemSupply> result = new Result<ItemSupply>();
	if (item == null || smsOrder == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	Result<List<ItemSupply>> queryItemSuppyByItemSettingResult = queryItemSuppyByItemSetting(item);
	if (!queryItemSuppyByItemSettingResult.isSuccess() || queryItemSuppyByItemSettingResult.getModule() == null) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_FAILD);
	    result.setResultMsg(queryItemSuppyByItemSettingResult.getResultMsg());
	    return result;
	}

	if (queryItemSuppyByItemSettingResult.getModule().isEmpty()) {
	    logger.error("itemSupply is null item id : " + item.getId());
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_FAILD);
	    result.setResultMsg("供货商品可能已经下架");
	    return result;
	}

	Result<ItemSupply> filerResult = filterItemSupplyList(queryItemSuppyByItemSettingResult.getModule(), smsOrder);
	if (!filerResult.isSuccess()) {
	    result.setResultCode(filerResult.getResultCode());
	    result.setResultMsg(filerResult.getResultMsg());
	    return result;
	}
	ItemSupply itemSupply = filerResult.getModule();
	if (itemSupply == null) {
	    logger.error("itemSupply is null item id : " + item.getId());
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_FAILD);
	    result.setResultMsg("itemSupply is null");
	    return result;
	}

	result.setModule(itemSupply);
	result.setSuccess(true);
	return result;
    }

    private Result<Boolean> checkRepeat(Item item, BizOrder bizOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	if (bizOrder == null || item == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}
	if (!bizOrder.isRepeat() || bizOrder.isManualRepeatType()) {
	    // 是第一次供货，或者手动补充，直接走下一步
	    result.setSuccess(true);
	    result.setModule(true);
	    return result;
	}

	// 次数
	if (item.getRepeatCount() == null || item.getRepeatCount() == 0) {
	    // 设置默认补充数
	    item.setRepeatCount(MAX_REPEAT_COUNT);
	}
	if (bizOrder.getSupplyCount() == null) {
	    logger.error("补充供货异常终止repeatCount是空 bizOrderId :" + bizOrder.getId());
	    result.setResultMsg("补充供货异常终止 bizOrderId :" + bizOrder.getId());
	    return result;
	}
	if (bizOrder.getSupplyCount() - 1 >= item.getRepeatCount()) {
	    logger.warn("补充供货终止count = " + bizOrder.getSupplyCount());
	    result.setResultMsg("补充供货终止 超过最大补充数");
	    return result;
	}

	// 时间
	if (bizOrder.getGmtCreate() == null) {
	    logger.error("补充供货异常终止 订单创建时间是空 bizOrderId :" + bizOrder.getId());
	    result.setResultMsg("补充供货异常终止 bizOrderId :" + bizOrder.getId());
	    return result;
	}
	long now = System.currentTimeMillis();
	if (now - bizOrder.getGmtCreate().getTime() > MAX_REPEAT_TIME) {
	    logger.error("补充供货异常终止 超过默认最大补充时间 bizOrderId :" + bizOrder.getId());
	    result.setResultMsg("补充供货超时，超过默认最大补充时间");
	    return result;
	}

	if (item.getRepeatTime() == null || item.getRepeatTime() == 0) {
	    // 如果未设置时间，则表示，不判断时间。
	    result.setSuccess(true);
	    result.setModule(true);
	    return result;
	}
	if (now - bizOrder.getGmtCreate().getTime() > item.getRepeatTime() * 60 * 1000) {
	    logger.warn("补充供货终止 超过补充时间限制 bizOrderId :" + bizOrder.getId());
	    result.setResultMsg("补充供货终止，超过最大补充时间");
	    return result;
	}

	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    // private final static String startTime = "09:00:00";
    // private final static String endTime = "22:00:00";

    private Result<ItemSupply> filterItemSupplyList(List<ItemSupply> list, BizOrder bizOrder) {
	Result<ItemSupply> result = new Result<ItemSupply>();
	result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_FAILD);

	ItemSupply itemSupply = null;
	boolean isOnlySupply = list != null && list.size() == 0;
	if (bizOrder.isRepeat()) {
	    // 补充情况下的筛选

	    // 手动补充，仅支持同通道补充
	    // if (bizOrder.isManualRepeatType()) {
	    // for (ItemSupply row : list) {
	    // if (row.getId().equals(bizOrder.getItemSupplyId())) {
	    // Result<Boolean> fireItemSupply = fireItemSupply(row, true, bizOrder);
	    // if (!fireItemSupply.isSuccess()) {
	    // result.setResultCode(fireItemSupply.getResultCode());
	    // result.setResultMsg(fireItemSupply.getResultMsg());
	    // return result;
	    // }
	    // if (!fireItemSupply.getModule()) {
	    // continue;
	    // }
	    // itemSupply = row;
	    // }
	    // }
	    //
	    // result.setSuccess(true);
	    // result.setModule(itemSupply);
	    // return result;
	    // }

	    if (bizOrder.getSupplyFilterIndex() == null || bizOrder.getSupplyFilterIndex() + 1 >= list.size()) {
		// 筛选标志位+1 大于供货数量， 就不需要补充了。
		result.setResultMsg("不需要补充了");
		return result;
	    }

	    for (int i = bizOrder.getSupplyFilterIndex() + 1; i < list.size(); i++) {
		ItemSupply row = list.get(i);
		Result<Boolean> fireItemSupply = fireItemSupply(row, isOnlySupply, bizOrder);
		if (!fireItemSupply.isSuccess()) {
		    result.setResultCode(fireItemSupply.getResultCode());
		    result.setResultMsg(fireItemSupply.getResultMsg());
		    return result;
		}

		if (!fireItemSupply.getModule()) {
		    continue;
		}

		itemSupply = row;
		itemSupply.setSupplyFilterIndex(i); // 设置订单筛选标志位。
		break;
	    }
	} else {
	    // 非补充情况下的筛选。
	    for (int i = 0; i < list.size(); i++) {
		ItemSupply row = list.get(i);
		Result<Boolean> fireItemSupply = fireItemSupply(row, isOnlySupply, bizOrder);
		if (!fireItemSupply.isSuccess()) {
		    result.setResultCode(fireItemSupply.getResultCode());
		    result.setResultMsg(fireItemSupply.getResultMsg());
		    return result;
		}
		if (!fireItemSupply.getModule()) {
		    continue;
		}

		itemSupply = row;
		itemSupply.setSupplyFilterIndex(i); // 设置订单筛选标志位。
		break;
	    }
	}

	result.setSuccess(true);
	// 有可能是空
	result.setModule(itemSupply);
	return result;
    }

    private Result<ItemSupply> filterItemSupplyList(List<ItemSupply> list, SmsOrder smsOrder) {
	Result<ItemSupply> result = new Result<ItemSupply>();

	ItemSupply itemSupply = null;
	for (int i = 0; i < list.size(); i++) {
	    ItemSupply row = list.get(i);
	    if (!row.canLoseSupply()) {
		// 不允许亏本跑 则做成本价判断
		if (row.getItemCostPrice() == null || smsOrder.getItemPrice() == null
			|| row.getItemCostPrice() > smsOrder.getItemPrice()) {
		    continue;
		}
	    }

	    itemSupply = row;
	    break;
	}

	if (itemSupply == null) {
	    result.setResultCode(Constants.ErrorCode.CODE_ERROR_SUPPLY_FAILD);
	    result.setResultMsg("不允许亏本出货");
	} else {
	    result.setSuccess(true);
	    result.setModule(itemSupply);
	}
	return result;
    }

    private Result<Boolean> fireItemSupply(ItemSupply row, boolean isOnlySupply, BizOrder bizOrder) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	// if (row.isTypeMan()) {
	// String nowTime = DateTool.getNowTime();
	// if (!(nowTime.compareTo(startTime) >= 0 && nowTime.compareTo(endTime) <= 0)) {
	// if (isOnlySupply) {
	// // 当只有一个供货商品的时候，直接返回这个错误信息。
	// result.setResultMsg("非供货时间段");
	// return result;
	// } else {
	// result.setSuccess(true);
	// result.setModule(false);
	// return result;
	// }
	// }
	// }
	if (row.hasStock()) {
	    // 如果库存不足
	    if (row.getQuantity() <= 0) {
		if (isOnlySupply) {
		    // 当只有一个供货商品的时候，直接返回这个错误信息。
		    result.setResultMsg("库存不足");
		    return result;
		} else {
		    result.setSuccess(true);
		    result.setModule(false);
		    return result;
		}
	    }
	}

	if (!row.canLoseSupply()) {
	    // 不允许亏本跑 则做成本价判断
	    if (row.getItemCostPrice() == null || bizOrder.getItemPrice() == null
		    || row.getItemCostPrice() > bizOrder.getItemPrice()) {
		if (isOnlySupply) {
		    result.setResultMsg("不允许亏本出货");
		    return result;
		} else {
		    result.setSuccess(true);
		    result.setModule(false);
		    return result;
		}
	    }
	}

	Result<Boolean> hasReachLimitResult = chargingLimitService.hasReachLimit(row.getSupplyTraderId(),
		bizOrder.getItemFacePrice());
	if (!hasReachLimitResult.isSuccess()) {
	    if (isOnlySupply) {
		result.setResultCode(Constants.ErrorCode.CODE_ERROR_BUSI);
		result.setResultMsg(Constants.ErrorCode.DESC_ERROR_BUSI);
		return result;
	    } else {
		result.setSuccess(true);
		result.setModule(false);
		return result;
	    }
	}

	if (bizOrder.getCarrierType() != Constants.Item.CARRIER_TYPE_OTHER) {
	    String supplyArea = row.getSupplyArea();
	    if (StringUtils.isNotBlank(supplyArea)) {
		String provinceCode = bizOrder.getProvinceCode();
		// 市域临时
		if (supplyArea.equals("442000") || supplyArea.equals("640300")) {
		    supplyArea = provinceCode;
		}

		if (StringUtils.isBlank(provinceCode) || !supplyArea.contains(provinceCode)) {
		    result.setSuccess(true);
		    result.setModule(false);
		    return result;
		}
	    }
	}

	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Boolean> createItem(Item item) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    ItemExample example = new ItemExample();
	    item.setItemName(item.getItemName());
	    example.createCriteria().andItemNameEqualTo(item.getItemName());
	    List<Item> list = (List<Item>) itemDAO.selectByExample(example);
	    if (list.size() > 0) {
		result.setResultMsg("商品名已存在");
		return result;
	    }

	    if (item.isCombine()) {
		item.setStatus(Constants.Item.STATUS_DOWN);
	    } else {
		item.setStatus(Constants.Item.STATUS_INIT);
	    }
	    item.setSupplyFilterType(Constants.Item.SUPPLY_FILTER_TYPE_PRIORITY);
	    Integer id = itemDAO.insert(item);
	    if (id == null) {
		result.setResultMsg("新增商品失败");
		return result;
	    }
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (Exception e) {
	    result.setResultMsg("新增商品失败 ");
	    logger.error("createNewItem error itemName : " + item.getItemName(), e);
	    return result;
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<Boolean> createItemSupply(ItemSupply itemSupply) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    ItemSupplyExample example = new ItemSupplyExample();
	    example.createCriteria().andSupplyTraderIdEqualTo(itemSupply.getSupplyTraderId())
		    .andItemIdEqualTo(itemSupply.getItemId()).andStatusNotEqualTo(Constants.ItemSupply.STATUS_DEL);
	    List<ItemSupply> list = (List<ItemSupply>) itemSupplyDAO.selectByExample(example);
	    if (list.size() > 0) {
		String curProductCode = itemSupply.getSupplyProductCode();
		for (ItemSupply dbItemSupply : list) {
		    if (curProductCode.equals(dbItemSupply.getSupplyProductCode())) {
			result.setResultMsg("商品与该供货已经关联");
			return result;
		    }
		}
	    }
	    itemSupply.setStatus(Constants.ItemSupply.STATUS_INIT);
	    itemSupply.setQuantity(0);
	    itemSupply.setNumDay(0);
	    itemSupply.setNumMounth(0);
	    itemSupply.setMaxDay(0);
	    itemSupply.setMaxMounth(0);
	    itemSupply.setWarnQuantity(0);
	    Long id = itemSupplyDAO.insert(itemSupply);
	    if (id == null) {
		result.setResultMsg("商品关联供货失败");
		return result;
	    }
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (Exception e) {
	    result.setResultMsg("商品关联供货失败 msg :" + e.getMessage());
	    logger.error("createItemSupply error itemId : " + itemSupply.getItemId(), e);
	    return result;
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<ItemSupply> getItemSupply(Long itemSupplyId) {
	Result<ItemSupply> result = new Result<ItemSupply>();
	ItemSupply itemSupply = null;
	try {
	    itemSupply = itemSupplyDAO.selectByPrimaryKey(itemSupplyId);
	    if (itemSupply != null) {
		TraderInfo traderInfo = null;
		TraderInfoExample example = new TraderInfoExample();
		example.createCriteria().andUserIdEqualTo(itemSupply.getSupplyTraderId());
		List<TraderInfo> list = (List<TraderInfo>) traderInfoDAO.selectByExample(example);
		if (list != null && !list.isEmpty()) {
		    traderInfo = list.get(0);
		    itemSupply.setItemSupplyType(traderInfo.getSupplyType());
		    itemSupply.setIsAsyncSupply(traderInfo.getIsAsyncSupply());
		}

		UserInfo userInfo = localCachedService.getUserInfo(itemSupply.getSupplyTraderId());
		if (userInfo != null) {
		    itemSupply.setSupplyTraderName(userInfo.getUserName());
		}
	    }
	} catch (SQLException e) {
	    result.setResultMsg("获取供货商品失败:" + e.getMessage());
	    logger.error("getItemSupply error", e);
	    return result;
	}
	result.setSuccess(true);
	result.setModule(itemSupply);
	return result;
    }

    @Override
    public Result<Boolean> updateItemSupply(ItemSupply itemSupply) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    int row = itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException e) {
	    result.setResultMsg("更新供货商品失败    msg: " + e.getMessage());
	    logger.error("updateItemSupply error ", e);
	}
	return result;
    }

    @Override
    public Result<List<ItemSupply>> queryItemSupplyPage(ItemSupplyQuery itemSupplyQuery) {
	Result<List<ItemSupply>> result = new Result<List<ItemSupply>>();
	try {
	    List<ItemSupply> queryResult = itemSupplyDAO.queryByPage(itemSupplyQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException e) {
	    result.setResultMsg("供货商品查询失败 ");
	    logger.error("queryItemSupplyPage error ", e);
	}

	return result;
    }

    @Override
    public Result<Boolean> upItemSupply(Long itemSupplyId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    ItemSupply itemSupply = new ItemSupply();
	    itemSupply.setStatus(Constants.ItemSupply.STATUS_UP);
	    itemSupply.setId(itemSupplyId);
	    int row = itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("供货商品上架失败");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("供货商品上架失败");
	    logger.error("upItemSupply error itemSupplyId : " + itemSupplyId, e);
	}
	return result;
    }

    @Override
    public Result<Boolean> downItemSupply(Long itemSupplyId) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	try {
	    ItemSupply itemSupply = new ItemSupply();
	    itemSupply.setStatus(Constants.ItemSupply.STATUS_DOWN);
	    itemSupply.setId(itemSupplyId);
	    int row = itemSupplyDAO.updateByPrimaryKeySelective(itemSupply);
	    if (row > 0) {
		result.setSuccess(true);
		result.setModule(true);
	    } else {
		result.setResultMsg("供货商品下架失败");
	    }
	} catch (SQLException e) {
	    result.setResultMsg("供货商品下架失败");
	    logger.error("downItemSupply error itemSupplyId : " + itemSupplyId, e);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<List<Item>> queryItemList() {
	Result<List<Item>> result = new Result<List<Item>>();
	ItemExample itemExample = new ItemExample();
	itemExample.createCriteria().andStatusNotEqualTo(Constants.Item.STATUS_DEL);
	try {
	    List<Item> list = (List<Item>) itemDAO.selectByExample(itemExample);
	    result.setSuccess(true);
	    result.setModule(list);
	} catch (SQLException e) {
	    result.setResultMsg("查询商品列表失败  msg :" + e.getMessage());
	    logger.error("queryItemList error : " + e);
	}
	return result;
    }

    @Override
    public Result<List<ItemSales>> queryItemPriceList(ItemSalesQuery itemSalesQuery) {
	Result<List<ItemSales>> result = new Result<List<ItemSales>>();
	if (itemSalesQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<ItemSales> queryResult = itemSalesDAO.queryItemList(itemSalesQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException ex) {
	    result.setResultMsg("商品价格列表查询失败  msg : " + ex.getMessage());
	    logger.error("queryItemPriceList error ", ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> createItemPrice(ItemSales itemSales) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    Long id = itemSalesDAO.insert(itemSales);
	    if (id == null) {
		result.setResultMsg("开通商品价格失败");
		return result;
	    }
	    itemSales.setId(id);
	    result.setSuccess(true);
	    result.setModule(true);
	} catch (SQLException ex) {
	    result.setResultMsg("开通商品价格失败 : " + ex.getMessage());
	    logger.error("createItemPrice error ", ex);
	}
	return result;
    }

    @Override
    public Result<Boolean> updateItemPrice(ItemSales itemSales) {
	Result<Boolean> result = new Result<Boolean>();
	try {
	    int row = itemSalesDAO.updateByPrimaryKeySelective(itemSales);
	    result.setSuccess(row > 0);
	    result.setModule(row > 0);
	} catch (SQLException ex) {
	    result.setResultMsg("修改商品价格失败 : " + ex.getMessage());
	    logger.error("updateItemPrice error id : " + itemSales.getId(), ex);
	}
	return result;
    }

    @Override
    public Result<ItemSales> getItemPrice(Long id) {
	Result<ItemSales> result = new Result<ItemSales>();
	try {
	    ItemSales itemSales = itemSalesDAO.selectByPrimaryKey(id);
	    if (itemSales != null) {
		result.setSuccess(true);
		result.setModule(itemSales);
	    } else {
		result.setResultMsg("没有该商品  id : " + id);
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("查询商品价格失败 ： msg :" + ex.getMessage());
	    logger.error("getItemPrice error id : " + id, ex);
	}
	return result;
    }

    @Override
    public Result<ItemSales> getItemPrice(Integer itemId, Long userId) {
	Result<ItemSales> result = new Result<ItemSales>();
	try {
	    ItemSales itemSales = itemSalesDAO.selectByIndex(userId, itemId);
	    if (itemSales != null) {
		result.setSuccess(true);
		result.setModule(itemSales);
	    } else {
		result.setResultMsg("没有该商品  itemId : " + itemId + " userId : " + userId);
	    }
	} catch (SQLException ex) {
	    result.setResultMsg("查询商品价格失败 ： msg :" + ex.getMessage());
	    logger.error("getItemPrice error itemId : " + itemId + " userId : " + userId, ex);
	}
	return result;
    }
}