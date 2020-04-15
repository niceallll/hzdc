package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.ExportQuery;
import com.longan.biz.dataobject.PayOrder;
import com.longan.biz.dataobject.PayOrderQuery;
import com.longan.biz.dataobject.SmsNotify;
import com.longan.biz.dataobject.SmsNotifyQuery;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.Stock;
import com.longan.biz.dataobject.StockQuery;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.domain.BizOrderStatisic;
import com.longan.biz.domain.Result;
import com.longan.biz.domain.SupplyOrderStatisic;

public interface QueryService {
    public Result<List<BizOrder>> queryBizOrderPage(BizOrderQuery bizOrderQuery);

    public Result<List<BizOrder>> queryBizOrderExport(BizOrderQuery bizOrderQuery);

    public Result<List<Export>> queryExport(ExportQuery exportQuery);

    public Result<List<AcctLog>> queryAcctLog(AcctLogQuery acctLogQuery);

    public Result<List<AcctLog>> queryAcctLogExport(AcctLogQuery acctLogQuery);

    public Result<List<Stock>> queryStockPage(StockQuery stockQuery);

    public Result<List<PayOrder>> queryPayOrder(PayOrderQuery payOrderQuery);

    public Result<BizOrderStatisic> countBizOrder(BizOrderQuery bizOrderQuery);

    public Result<SupplyOrderStatisic> countSupplyOrder(SupplyOrderQuery supplyOrderQuery);

    public Result<List<SmsOrder>> querySmsOrderPage(SmsOrderQuery smsOrderQuery);

    public Result<List<SmsSupply>> querySmsSupplyPage(SmsSupplyQuery smsSupplyQuery);

    public Result<List<SmsNotify>> querySmsNotifyPage(SmsNotifyQuery smsNotifyQuery);
}
