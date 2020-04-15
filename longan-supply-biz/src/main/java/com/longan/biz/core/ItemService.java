package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.AcctInfo;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.Item;
import com.longan.biz.dataobject.ItemQuery;
import com.longan.biz.dataobject.ItemSales;
import com.longan.biz.dataobject.ItemSalesQuery;
import com.longan.biz.dataobject.ItemSupply;
import com.longan.biz.dataobject.ItemSupplyQuery;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.domain.Result;

public interface ItemService {
    public Result<Item> createNewItem(Item item);

    public Result<Item> getItem(Integer itemId);

    public Result<List<Item>> queryItemList(Integer bizId);

    public Result<List<Item>> queryItemList(Long userId, Integer bizId, String provinceCode);

    public Result<Boolean> upItem(Integer itemId);

    public Result<Boolean> downItem(Integer itemId);

    public Result<Boolean> hasCombinedItem(Integer itemId);

    public Result<Boolean> toCombineItem(Integer itemId);

    public Result<Boolean> toChargeItem(Integer itemId);

    public Result<Boolean> toCardItem(Integer itemId);

    public Result<Boolean> updateItem(Item item);

    public Result<Boolean> upItem(List<Integer> ids);

    public Result<Boolean> downItem(List<Integer> ids);

    public Result<Boolean> upItemSupply(List<Integer> ids);

    public Result<Boolean> downItemSupply(List<Integer> ids);

    public Result<Boolean> checkItem(BizOrder bizOrder);

    public Result<Boolean> checkPrice(BizOrder bizOrder);

    public Result<Boolean> checkItemStatus(Integer itemId);

    public Result<List<Item>> queryItemList(ItemQuery itemQuery);

    public Result<List<ItemSupply>> queryItemSupplyOnSale(Integer itemId);

    public Result<List<ItemSupply>> queryItemSupplyByBizId(Integer bizId);

    public Result<List<ItemSupply>> queryStockItemSupplyByBiz(Integer bizId);

    public Result<List<ItemSupply>> queryItemSuppyByItemSetting(Item item);

    public Result<ItemSupply> filterItemSupply(Item item, BizOrder bizOrder);

    public Result<ItemSupply> filterItemSupply(Item item, SmsOrder smsOrder);

    public Result<Boolean> createItem(Item item);

    public Result<Boolean> createItemSupply(ItemSupply itemSupply);

    public Result<ItemSupply> getItemSupply(Long itemSupplyId);

    public Result<Boolean> updateItemSupply(ItemSupply itemSupply);

    public Result<List<ItemSupply>> queryItemSupplyPage(ItemSupplyQuery itemSupplyQuery);

    public Result<Boolean> upItemSupply(Long itemSupplyId);

    public Result<Boolean> downItemSupply(Long itemSupplyId);

    public Result<List<Item>> queryItemList();

    // new price rule
    public Result<List<ItemSales>> queryItemPriceList(ItemSalesQuery itemSalesQuery);

    public Result<Boolean> createItemPrice(ItemSales itemSales);

    public Result<Boolean> updateItemPrice(ItemSales itemSales);

    public Result<ItemSales> getItemPrice(Long id);

    public Result<ItemSales> getItemPrice(Integer itemId, Long userId);

    public Result<Integer> getSalesPrice(Item item, AcctInfo acctInfo);
}
