package com.longan.biz.core;

import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.domain.Result;

public interface ExportService {
    public Result<Export> createExport(Export export, BizOrderQuery bizOrderQuery);

    public Result<Export> createExport(Export export, SupplyOrderQuery supplyOrderQuery);

    public Result<Export> createExport(Export export, RefundOrderQuery refundOrderQuery);

    public Result<Export> createExport(Export export, AcctLogQuery acctLogQuery);

    public Result<Export> createExport(Export export, SmsOrderQuery smsOrderQuery);

    public Result<Export> createExport(Export export, SmsSupplyQuery smsSupplyQuery);

    public Result<Boolean> updateExport(Export export, Boolean isSucc);

    public Result<Export> getExportById(Long id);

    public Result<Boolean> deleteExportById(Long id);
}
