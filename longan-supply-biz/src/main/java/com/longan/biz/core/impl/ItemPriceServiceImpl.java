package com.longan.biz.core.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.longan.biz.core.BaseService;
import com.longan.biz.core.ItemPriceService;
import com.longan.biz.core.ItemService;
import com.longan.biz.dao.ItemPriceDAO;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemPrice;
import com.longan.biz.dataobject.ItemPriceQuery;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.domain.Result;

public class ItemPriceServiceImpl extends BaseService implements ItemPriceService {
    @Resource
    private ItemPriceDAO itemPriceDAO;

    @Resource
    private ItemService itemService;

    @Override
    public Result<List<ItemPrice>> queryItemPricePage(ItemPriceQuery itemPriceQuery) {
	Result<List<ItemPrice>> result = new Result<List<ItemPrice>>();
	try {
	    List<ItemPrice> queryResult = itemPriceDAO.queryByPage(itemPriceQuery);
	    result.setModule(queryResult);
	} catch (SQLException ex) {
	    result.setResultMsg("价格查询失败，数据库异常");
	    logger.error("queryItemPricePage error ", ex);
	    return result;
	}
	result.setSuccess(true);
	return result;
    }

    @Override
    public Result<Boolean> adjustPrice(ItemPrice itemPrice) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	Item updateItem = new Item();
	updateItem.setId(itemPrice.getItemId());
	updateItem.setItemSalesPrice(itemPrice.getItemSalesPrice());
	updateItem.setItemSalesPrice2(itemPrice.getItemSalesPrice2());
	updateItem.setItemSalesPrice3(itemPrice.getItemSalesPrice3());
	updateItem.setItemSalesPrice4(itemPrice.getItemSalesPrice4());
	updateItem.setItemDummyPrice(itemPrice.getItemDummyPrice());
	Result<Boolean> updateItemResult = itemService.updateItem(updateItem);
	if (!updateItemResult.isSuccess()) {
	    result.setResultMsg(updateItemResult.getResultMsg());
	    return result;
	}

	ItemSupply updateItemSupply = new ItemSupply();
	updateItemSupply.setId(itemPrice.getId());
	updateItemSupply.setItemCostPrice(itemPrice.getItemCostPrice());
	Result<Boolean> updateItemSupplyResult = itemService.updateItemSupply(updateItemSupply);
	if (!updateItemSupplyResult.isSuccess()) {
	    result.setResultMsg(updateItemSupplyResult.getResultMsg());
	    return result;
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    public Result<Boolean> batchAdjustPrice(List<ItemPrice> itemPriceList) {
	Result<Boolean> result = new Result<Boolean>();
	result.setModule(false);
	for (ItemPrice itemPrice : itemPriceList) {
	    Result<Boolean> adjustPriceResult = adjustPrice(itemPrice);
	    if (!adjustPriceResult.isSuccess()) {
		// 这里就不做出错处理了。
		logger.error("adjustPrice error itemSupplyId : " + itemPrice.getId() + " msg : "
			+ adjustPriceResult.getResultMsg());
	    }
	}
	result.setSuccess(true);
	result.setModule(true);
	return result;
    }

    @Override
    public Result<List<ItemPrice>> queryItemPriceExport(ItemPriceQuery itemPriceQuery) {
	Result<List<ItemPrice>> result = new Result<List<ItemPrice>>();
	if (itemPriceQuery == null) {
	    result.setResultMsg("入参错误");
	    return result;
	}

	try {
	    List<ItemPrice> queryResult = itemPriceDAO.queryByExport(itemPriceQuery);
	    result.setSuccess(true);
	    result.setModule(queryResult);
	} catch (SQLException ex) {
	    result.setResultMsg("商品价格查询失败  msg : " + ex.getMessage());
	    logger.error("queryItemPricePage error ", ex);
	}
	return result;
    }
}
