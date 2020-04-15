package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.ItemPrice;
import com.longan.biz.dataobject.ItemPriceQuery;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;

public interface ExcelExportService {
    public Result<List<BizOrder>> queryBizOrderExport(BizOrderQuery bizOrderQuery, Boolean isDownStream);

    public Result<List<RefundOrder>> queryRefundOrderExport(RefundOrderQuery refundOrderQuery, Boolean isDownStream);

    public Result<List<AcctLog>> queryAcctLogExport(AcctLogQuery acctLogQuery, Boolean isDownStream);

    public Result<List<Stock>> queryStockListByStockLogId(Long stockLogId, UserInfo userInfo);

    public Result<List<ItemPrice>> queryItemPriceExport(ItemPriceQuery itemPriceQuery);

    public Result<List<SupplyOrder>> querySupplyOrderExport(SupplyOrderQuery supplyOrderQuery);

    public Result<List<SupplyOrder>> querySaleOrderExport(SupplyOrderQuery supplyOrderQuery);

    public Result<List<SmsOrder>> querySmsOrderExport(SmsOrderQuery smsOrderQuery, Boolean isDownStream);

    public Result<List<SmsSupply>> querySmsSupplyExport(SmsSupplyQuery smsSupplyQuery);
}
