package com.longan.biz.core;

import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.SupplyOrderQuery;

public interface ExcelAsyncService {
    public void bizOrderExport(Export export, BizOrderQuery bizOrderQuery);

    public void supplyOrderExport(Export export, SupplyOrderQuery supplyOrderQuery);

    public void refundOrderExport(Export export, RefundOrderQuery refundOrderQuery);

    public void acctLogExport(Export export, AcctLogQuery acctLogQuery);

    public void smsOrderExport(Export export, SmsOrderQuery smsOrderQuery);

    public void smsSupplyExport(Export export, SmsSupplyQuery smsSupplyQuery);
}
