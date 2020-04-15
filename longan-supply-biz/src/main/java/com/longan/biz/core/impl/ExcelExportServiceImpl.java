package com.longan.biz.core.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import com.longan.biz.core.AcctLogService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.ExcelExportService;
import com.longan.biz.core.ItemPriceService;
import com.longan.biz.core.RefundOrderService;
import com.longan.biz.core.SmsOrderService;
import com.longan.biz.core.StockService;
import com.longan.biz.core.SupplyOrderService;
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
import com.longan.biz.dataobject.StockLog;
import com.longan.biz.dataobject.StockQuery;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.dataobject.UserInfo;
import com.longan.biz.domain.Result;

public class ExcelExportServiceImpl extends BaseService implements ExcelExportService {
    // 最多1个线程同时进行导出处理，最多4个队列，超过队列数则报拒绝异常。
    private ExecutorService downStreamExceutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
	    new LinkedBlockingQueue<Runnable>(4));

    // 最多2个线程同时进行导出处理，最多8个队列，超过队列数则报拒绝异常,内部人员要宽松一点
    private ExecutorService exceutor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(
	    8));

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private RefundOrderService refundOrderService;

    @Resource
    private AcctLogService acctLogService;

    @Resource
    private StockService stockService;

    @Resource
    private ItemPriceService itemPriceService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private SmsOrderService smsOrderService;

    @Override
    public Result<List<BizOrder>> queryBizOrderExport(final BizOrderQuery bizOrderQuery, Boolean isDownStream) {
	Result<List<BizOrder>> result = new Result<List<BizOrder>>();
	Callable<Result<List<BizOrder>>> callable = new Callable<Result<List<BizOrder>>>() {
	    @Override
	    public Result<List<BizOrder>> call() throws Exception {
		return bizOrderService.queryBizOrderExport(bizOrderQuery);
	    }
	};
	Future<Result<List<BizOrder>>> future = null;
	try {
	    if (isDownStream) {
		future = downStreamExceutor.submit(callable);
	    } else {
		future = exceutor.submit(callable);
	    }
	} catch (RejectedExecutionException e) {
	    result.setResultMsg("导出系统繁忙，请稍候再试");
	    logger.error("queryBizOrderExport error,  reach the blockingQueue maxLimit", e);
	    return result;
	}

	try {
	    result = future.get(300, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    logger.error("queryBizOrderExport error", e);
	    result.setResultMsg("queryBizOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (ExecutionException e) {
	    logger.error("queryBizOrderExport error", e);
	    result.setResultMsg("queryBizOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (TimeoutException e) {
	    logger.error("queryBizOrderExport timeout", e);
	    result.setResultMsg("导出超时 : " + e.getMessage());
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<RefundOrder>> queryRefundOrderExport(final RefundOrderQuery refundOrderQuery, Boolean isDownStream) {
	Result<List<RefundOrder>> result = new Result<List<RefundOrder>>();
	Callable<Result<List<RefundOrder>>> callable = new Callable<Result<List<RefundOrder>>>() {
	    @Override
	    public Result<List<RefundOrder>> call() throws Exception {
		return refundOrderService.queryRefundOrderExport(refundOrderQuery);
	    }
	};
	Future<Result<List<RefundOrder>>> future = null;
	try {
	    if (isDownStream) {
		future = downStreamExceutor.submit(callable);
	    } else {
		future = exceutor.submit(callable);
	    }
	} catch (RejectedExecutionException e) {
	    result.setResultMsg("导出系统繁忙，请稍候再试");
	    logger.error("queryRefundOrderExport error,  reach the blockingQueue maxLimit", e);
	    return result;
	}

	try {
	    result = future.get(300, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    logger.error("queryRefundOrderExport error", e);
	    result.setResultMsg("queryRefundOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (ExecutionException e) {
	    logger.error("queryRefundOrderExport error", e);
	    result.setResultMsg("queryRefundOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (TimeoutException e) {
	    logger.error("queryRefundOrderExport timeout", e);
	    result.setResultMsg("导出超时 : " + e.getMessage());
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<AcctLog>> queryAcctLogExport(final AcctLogQuery acctLogQuery, Boolean isDownStream) {
	Result<List<AcctLog>> result = new Result<List<AcctLog>>();
	Callable<Result<List<AcctLog>>> callable = new Callable<Result<List<AcctLog>>>() {
	    @Override
	    public Result<List<AcctLog>> call() throws Exception {
		return acctLogService.queryAcctLogExport(acctLogQuery);
	    }
	};
	Future<Result<List<AcctLog>>> future = null;
	try {
	    if (isDownStream) {
		future = downStreamExceutor.submit(callable);
	    } else {
		future = exceutor.submit(callable);
	    }
	} catch (RejectedExecutionException e) {
	    result.setResultMsg("导出系统繁忙，请稍候再试");
	    logger.error("queryAcctLogExport error,  reach the blockingQueue maxLimit", e);
	    return result;
	}

	try {
	    result = future.get(300, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    logger.error("queryAcctLogExport error", e);
	    result.setResultMsg("queryAcctLogExport error msg : " + e.getMessage());
	    return result;
	} catch (ExecutionException e) {
	    logger.error("queryAcctLogExport error", e);
	    result.setResultMsg("queryAcctLogExport error msg : " + e.getMessage());
	    return result;
	} catch (TimeoutException e) {
	    logger.error("queryAcctLogExport timeout", e);
	    result.setResultMsg("导出超时 : " + e.getMessage());
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<Stock>> queryStockListByStockLogId(Long stockLogId, UserInfo userInfo) {
	Result<List<Stock>> result = new Result<List<Stock>>();
	Result<StockLog> stockLogResult = stockService.getStockLog(stockLogId);
	if (!stockLogResult.isSuccess() || stockLogResult.getModule() == null) {
	    result.setResultMsg("库存日志获取异常");
	    return result;
	}
	if (!stockLogResult.getModule().getOperId().equals(userInfo.getId())) {
	    result.setResultMsg("只有提取人才能下载该文件");
	    return result;
	}

	StockQuery stockQuery = new StockQuery();
	stockQuery.setPageSize(100000);
	stockQuery.setOutSerialno(stockLogId);
	Result<List<Stock>> queryStockPageResult = stockService.queryStockPage(stockQuery);
	if (!queryStockPageResult.isSuccess()) {
	    result.setResultMsg(queryStockPageResult.getResultMsg());
	    return result;
	}

	List<Stock> list = queryStockPageResult.getModule();
	result.setSuccess(true);
	result.setModule(list);
	return result;
    }

    @Override
    public Result<List<ItemPrice>> queryItemPriceExport(final ItemPriceQuery itemPriceQuery) {
	Result<List<ItemPrice>> result = new Result<List<ItemPrice>>();
	Callable<Result<List<ItemPrice>>> callable = new Callable<Result<List<ItemPrice>>>() {
	    @Override
	    public Result<List<ItemPrice>> call() throws Exception {
		return itemPriceService.queryItemPriceExport(itemPriceQuery);
	    }
	};
	Future<Result<List<ItemPrice>>> future = null;
	try {
	    future = exceutor.submit(callable);
	} catch (RejectedExecutionException e) {
	    result.setResultMsg("导出系统繁忙，请稍候再试");
	    logger.error("queryItemPriceExport error,  reach the blockingQueue maxLimit", e);
	    return result;
	}

	try {
	    result = future.get(300, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    logger.error("queryItemPriceExport error", e);
	    result.setResultMsg("queryItemPriceExport error msg : " + e.getMessage());
	    return result;
	} catch (ExecutionException e) {
	    logger.error("queryItemPriceExport error", e);
	    result.setResultMsg("queryItemPriceExport error msg : " + e.getMessage());
	    return result;
	} catch (TimeoutException e) {
	    logger.error("queryItemPriceExport timeout", e);
	    result.setResultMsg("导出超时 : " + e.getMessage());
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<SupplyOrder>> querySupplyOrderExport(final SupplyOrderQuery supplyOrderQuery) {
	Result<List<SupplyOrder>> result = new Result<List<SupplyOrder>>();
	Callable<Result<List<SupplyOrder>>> callable = new Callable<Result<List<SupplyOrder>>>() {
	    @Override
	    public Result<List<SupplyOrder>> call() throws Exception {
		return supplyOrderService.querySupplyOrderExport(supplyOrderQuery);
	    }
	};
	Future<Result<List<SupplyOrder>>> future = null;
	try {
	    future = exceutor.submit(callable);
	} catch (RejectedExecutionException e) {
	    result.setResultMsg("导出系统繁忙，请稍候再试");
	    logger.error("querySupplyOrderExport error,  reach the blockingQueue maxLimit", e);
	    return result;
	}

	try {
	    result = future.get(300, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    logger.error("querySupplyOrderExport error", e);
	    result.setResultMsg("querySupplyOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (ExecutionException e) {
	    logger.error("querySupplyOrderExport error", e);
	    result.setResultMsg("querySupplyOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (TimeoutException e) {
	    logger.error("querySupplyOrderExport timeout", e);
	    result.setResultMsg("导出超时 : " + e.getMessage());
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<SupplyOrder>> querySaleOrderExport(final SupplyOrderQuery supplyOrderQuery) {
	Result<List<SupplyOrder>> result = new Result<List<SupplyOrder>>();
	Callable<Result<List<SupplyOrder>>> callable = new Callable<Result<List<SupplyOrder>>>() {
	    @Override
	    public Result<List<SupplyOrder>> call() throws Exception {
		return supplyOrderService.querySaleOrderExport(supplyOrderQuery);
	    }
	};
	Future<Result<List<SupplyOrder>>> future = null;
	try {
	    future = exceutor.submit(callable);
	} catch (RejectedExecutionException e) {
	    result.setResultMsg("导出系统繁忙，请稍候再试");
	    logger.error("querySaleOrderExport error,  reach the blockingQueue maxLimit", e);
	    return result;
	}

	try {
	    result = future.get(300, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    logger.error("querySaleOrderExport error", e);
	    result.setResultMsg("querySaleOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (ExecutionException e) {
	    logger.error("querySaleOrderExport error", e);
	    result.setResultMsg("querySaleOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (TimeoutException e) {
	    logger.error("querySaleOrderExport timeout", e);
	    result.setResultMsg("导出超时 : " + e.getMessage());
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<SmsOrder>> querySmsOrderExport(final SmsOrderQuery smsOrderQuery, Boolean isDownStream) {
	Result<List<SmsOrder>> result = new Result<List<SmsOrder>>();
	Callable<Result<List<SmsOrder>>> callable = new Callable<Result<List<SmsOrder>>>() {
	    @Override
	    public Result<List<SmsOrder>> call() throws Exception {
		return smsOrderService.querySmsOrderExport(smsOrderQuery);
	    }
	};
	Future<Result<List<SmsOrder>>> future = null;
	try {
	    if (isDownStream) {
		future = downStreamExceutor.submit(callable);
	    } else {
		future = exceutor.submit(callable);
	    }
	} catch (RejectedExecutionException e) {
	    result.setResultMsg("导出系统繁忙，请稍候再试");
	    logger.error("querySmsOrderExport error,  reach the blockingQueue maxLimit", e);
	    return result;
	}

	try {
	    result = future.get(300, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    logger.error("querySmsOrderExport error", e);
	    result.setResultMsg("querySmsOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (ExecutionException e) {
	    logger.error("querySmsOrderExport error", e);
	    result.setResultMsg("querySmsOrderExport error msg : " + e.getMessage());
	    return result;
	} catch (TimeoutException e) {
	    logger.error("querySmsOrderExport timeout", e);
	    result.setResultMsg("导出超时 : " + e.getMessage());
	    return result;
	}
	return result;
    }

    @Override
    public Result<List<SmsSupply>> querySmsSupplyExport(final SmsSupplyQuery smsSupplyQuery) {
	Result<List<SmsSupply>> result = new Result<List<SmsSupply>>();
	Callable<Result<List<SmsSupply>>> callable = new Callable<Result<List<SmsSupply>>>() {
	    @Override
	    public Result<List<SmsSupply>> call() throws Exception {
		return smsOrderService.querySmsSupplyExport(smsSupplyQuery);
	    }
	};
	Future<Result<List<SmsSupply>>> future = null;
	try {
	    future = exceutor.submit(callable);
	} catch (RejectedExecutionException e) {
	    result.setResultMsg("导出系统繁忙，请稍候再试");
	    logger.error("querySmsSupplyExport error,  reach the blockingQueue maxLimit", e);
	    return result;
	}

	try {
	    result = future.get(300, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    logger.error("querySmsSupplyExport error", e);
	    result.setResultMsg("querySmsSupplyExport error msg : " + e.getMessage());
	    return result;
	} catch (ExecutionException e) {
	    logger.error("querySmsSupplyExport error", e);
	    result.setResultMsg("querySmsSupplyExport error msg : " + e.getMessage());
	    return result;
	} catch (TimeoutException e) {
	    logger.error("querySmsSupplyExport timeout", e);
	    result.setResultMsg("导出超时 : " + e.getMessage());
	    return result;
	}
	return result;
    }
}
