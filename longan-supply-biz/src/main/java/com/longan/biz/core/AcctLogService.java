package com.longan.biz.core;

import java.util.List;

import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.domain.Result;
import com.longan.biz.sumobject.AcctLogAmount;

public interface AcctLogService {
    public Result<List<AcctLog>> queryAcctLog(AcctLogQuery acctLogQuery);

    public Result<AcctLogAmount> queryAcctLogSum(AcctLogQuery acctLogQuery);

    public Result<Integer> getCountInExport(AcctLogQuery acctLogQuery);

    public Result<List<AcctLog>> queryAcctLogExport(AcctLogQuery acctLogQuery);

    // 翻页查询
    public Result<List<AcctLog>> getAcctLogExport(AcctLogQuery acctLogQuery);
}
