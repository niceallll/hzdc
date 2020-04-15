package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.ItemPrice;
import com.longan.biz.dataobject.ItemPriceQuery;
import com.longan.biz.domain.Result;

public interface ItemPriceService {
    // old price rule
    public Result<List<ItemPrice>> queryItemPricePage(ItemPriceQuery itemPriceQuery);

    public Result<Boolean> adjustPrice(ItemPrice itemPrice);

    public Result<Boolean> batchAdjustPrice(List<ItemPrice> itemPriceList);

    public Result<List<ItemPrice>> queryItemPriceExport(ItemPriceQuery itemPriceQuery);
}
