package com.longan.biz.core.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.longan.biz.core.AcctLogService;
import com.longan.biz.core.BaseService;
import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.ExcelAsyncService;
import com.longan.biz.core.ExportService;
import com.longan.biz.core.RefundOrderService;
import com.longan.biz.core.SmsOrderService;
import com.longan.biz.core.SupplyOrderService;
import com.longan.biz.dataobject.AcctLog;
import com.longan.biz.dataobject.AcctLogQuery;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.BizOrderQuery;
import com.longan.biz.dataobject.Export;
import com.longan.biz.dataobject.RefundOrder;
import com.longan.biz.dataobject.RefundOrderQuery;
import com.longan.biz.dataobject.SmsOrder;
import com.longan.biz.dataobject.SmsOrderQuery;
import com.longan.biz.dataobject.SmsSupply;
import com.longan.biz.dataobject.SmsSupplyQuery;
import com.longan.biz.dataobject.SupplyOrder;
import com.longan.biz.dataobject.SupplyOrderQuery;
import com.longan.biz.domain.Result;

public class ExcelAsyncServiceImpl extends BaseService implements ExcelAsyncService {
    private static final int MAX_ROW = 65535;
    private final static int MAX_CACHE = 10000;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private BizOrderService bizOrderService;

    @Resource
    private SupplyOrderService supplyOrderService;

    @Resource
    private RefundOrderService refundOrderService;

    @Resource
    private SmsOrderService smsOrderService;

    @Resource
    private AcctLogService acctLogService;

    @Resource
    private ExportService exportService;

    @Override
    public void bizOrderExport(final Export export, final BizOrderQuery bizOrderQuery) {
	logger.warn("导出bizOrder文件产生：" + export.getFileName());
	executor.execute(new Runnable() {
	    @Override
	    public void run() {
		String fileName = export.getFileName();
		try {
		    int count = 0;
		    int totalCount = 0;
		    int sheetCount = 1;
		    Workbook workbook = new SXSSFWorkbook(MAX_CACHE);
		    Sheet sheet = workbook.createSheet(sheetCount + ""); // 创建表格
		    bizOrderHeader(sheet, export.getIsDownStream());
		    for (int page = 1; page <= export.getPageCount(); page++) {
			bizOrderQuery.setCurrentPage(page);
			Result<List<BizOrder>> result = bizOrderService.getBizOrderExport(bizOrderQuery);
			if (result.isSuccess()) {
			    totalCount += result.getModule().size();
			    if (totalCount > MAX_ROW) {
				count = 0;
				totalCount = result.getModule().size();
				sheetCount += 1;
				sheet = workbook.createSheet(sheetCount + ""); // 创建表格
				bizOrderHeader(sheet, export.getIsDownStream());
			    }

			    for (BizOrder bizOrder : result.getModule()) {
				count++;
				Row row = sheet.createRow(count);
				row.createCell(0).setCellValue(bizOrder.getId());
				row.createCell(1).setCellValue(bizOrder.getDownstreamSerialno());
				row.createCell(2).setCellValue(bizOrder.getItemUid());
				row.createCell(3).setCellValue(bizOrder.getUidAreaInfo());
				if (bizOrder.getUserId() != null) {
				    row.createCell(4).setCellValue(bizOrder.getUserId());
				}
				row.createCell(5).setCellValue(bizOrder.getItemName());
				row.createCell(6).setCellValue(bizOrder.getItemFacePriceDouble());
				if (export.getIsPartner()) {
				    row.createCell(7).setCellValue(bizOrder.getAmountDummyDouble());
				} else {
				    row.createCell(7).setCellValue(bizOrder.getAmountDouble()); // 导出金额是数字，方便excel计算
				}
				row.createCell(8).setCellValue(timeFormat.format(bizOrder.getGmtCreate()));
				row.createCell(9).setCellValue(bizOrder.getStatusDesc());
				row.createCell(10).setCellValue(bizOrder.getMemo());
				if (!export.getIsDownStream()) {
				    row.createCell(11).setCellValue(bizOrder.getUpstreamSerialno());
				    row.createCell(12).setCellValue(bizOrder.getItemPosId());
				    if (bizOrder.getItemCostPrice() != null) {
					row.createCell(13).setCellValue(bizOrder.getItemCostPriceDesc());
				    }
				    if (bizOrder.getActualCost() != null) {
					row.createCell(14).setCellValue(bizOrder.getActualCostDesc());
				    }
				    if (bizOrder.getUpstreamId() != null) {
					row.createCell(15).setCellValue(bizOrder.getUpstreamId());
				    }
				}
			    }
			}
		    }

		    OutputStream os = new FileOutputStream(fileName, false);
		    workbook.write(os);
		    os.flush();
		    os.close();

		    logger.warn("导出bizOrder文件结束：" + fileName);
		    exportService.updateExport(export, true);
		} catch (Exception ex) {
		    logger.error("导出bizOrder文件出错：" + fileName, ex);
		    exportService.updateExport(export, false);
		}
	    }
	});
    }

    @Override
    public void supplyOrderExport(final Export export, final SupplyOrderQuery supplyOrderQuery) {
	logger.warn("导出supplyOrder文件产生：" + export.getFileName());
	executor.execute(new Runnable() {
	    @Override
	    public void run() {
		String fileName = export.getFileName();
		try {
		    int count = 0;
		    int totalCount = 0;
		    int sheetCount = 1;
		    Workbook workbook = new SXSSFWorkbook(MAX_CACHE);
		    Sheet sheet = workbook.createSheet("1"); // 创建表格
		    supplyOrderHeader(sheet);
		    for (int page = 1; page <= export.getPageCount(); page++) {
			supplyOrderQuery.setCurrentPage(page);
			Result<List<SupplyOrder>> result = supplyOrderService.getSupplyOrderExport(supplyOrderQuery);
			if (result.isSuccess()) {
			    totalCount += result.getModule().size();
			    if (totalCount > MAX_ROW) {
				count = 0;
				totalCount = result.getModule().size();
				sheetCount += 1;
				sheet = workbook.createSheet(sheetCount + ""); // 创建表格
				supplyOrderHeader(sheet);
			    }

			    for (SupplyOrder supplyOrder : result.getModule()) {
				count++;
				Row row = sheet.createRow(count);
				row.createCell(0).setCellValue(supplyOrder.getId());
				row.createCell(1).setCellValue(supplyOrder.getBizOrderId());
				row.createCell(2).setCellValue(supplyOrder.getUpstreamSerialno());
				row.createCell(3).setCellValue(supplyOrder.getItemUid());
				row.createCell(4).setCellValue(supplyOrder.getUserId());
				row.createCell(5).setCellValue(supplyOrder.getItemName());
				row.createCell(6).setCellValue(supplyOrder.getItemFacePriceDouble());
				if (export.getIsPartner()) {
				    row.createCell(7).setCellValue(supplyOrder.getAmountDummyDouble());
				} else {
				    row.createCell(7).setCellValue(supplyOrder.getAmountDouble()); // 导出金额是数字，方便excel计算
				}
				if (supplyOrder.getSupplyCostPrice() != null) {
				    row.createCell(8).setCellValue(supplyOrder.getSupplyCostPriceDesc());
				}
				if (supplyOrder.getSupplyActualCost() != null) {
				    row.createCell(9).setCellValue(supplyOrder.getSupplyActualCostDesc());
				}
				row.createCell(10).setCellValue(supplyOrder.getSupplyTraderId());
				row.createCell(11).setCellValue(timeFormat.format(supplyOrder.getGmtCreate()));
				row.createCell(12).setCellValue(supplyOrder.getSupplyStatusDesc());
				row.createCell(13).setCellValue(supplyOrder.getUpstreamMemo());
				row.createCell(14).setCellValue(supplyOrder.getFinalTypeDesc());
				row.createCell(15).setCellValue(supplyOrder.getRepeatTypeDesc());
				row.createCell(16).setCellValue(supplyOrder.getManualRepeatTypeDesc());
			    }
			}
		    }

		    OutputStream os = new FileOutputStream(fileName, false);
		    workbook.write(os);
		    os.flush();
		    os.close();

		    logger.warn("导出supplyOrder文件结束：" + fileName);
		    exportService.updateExport(export, true);
		} catch (Exception ex) {
		    logger.error("导出supplyOrder文件出错：" + fileName, ex);
		    exportService.updateExport(export, false);
		}
	    }
	});
    }

    @Override
    public void refundOrderExport(final Export export, final RefundOrderQuery refundOrderQuery) {
	logger.warn("导出refundOrder文件产生：" + export.getFileName());
	executor.execute(new Runnable() {
	    @Override
	    public void run() {
		String fileName = export.getFileName();
		try {
		    int count = 0;
		    int totalCount = 0;
		    int sheetCount = 1;
		    Workbook workbook = new SXSSFWorkbook(MAX_CACHE);
		    Sheet sheet = workbook.createSheet("1"); // 创建表格
		    refundOrderHeader(sheet, export.getIsDownStream());
		    for (int page = 1; page <= export.getPageCount(); page++) {
			refundOrderQuery.setCurrentPage(page);
			Result<List<RefundOrder>> result = refundOrderService.getRefundOrderExport(refundOrderQuery);
			if (result.isSuccess()) {
			    totalCount += result.getModule().size();
			    if (totalCount > MAX_ROW) {
				count = 0;
				totalCount = result.getModule().size();
				sheetCount += 1;
				sheet = workbook.createSheet(sheetCount + ""); // 创建表格
				refundOrderHeader(sheet, export.getIsDownStream());
			    }

			    for (RefundOrder refundOrder : result.getModule()) {
				count++;
				Row row = sheet.createRow(count);
				row.createCell(0).setCellValue(refundOrder.getId());
				row.createCell(1).setCellValue(refundOrder.getBizOrderId());
				row.createCell(2).setCellValue(refundOrder.getPayOrderId());
				if (refundOrder.getUserId() != null) {
				    row.createCell(3).setCellValue(refundOrder.getUserId());
				}
				row.createCell(4).setCellValue(refundOrder.getItemId());
				row.createCell(5).setCellValue(refundOrder.getAmountDouble()); // 导出金额是数字，方便excel计算
				row.createCell(6).setCellValue(timeFormat.format(refundOrder.getGmtCreate()));

				row.createCell(7).setCellValue(refundOrder.getPayTypeDesc());
				row.createCell(8).setCellValue(refundOrder.getStatusDesc());
				row.createCell(9).setCellValue(refundOrder.getMemo());
				if (!export.getIsDownStream()) {
				    row.createCell(10).setCellValue(refundOrder.getOperName());
				}
			    }
			}
		    }

		    OutputStream os = new FileOutputStream(fileName, false);
		    workbook.write(os);
		    os.flush();
		    os.close();

		    logger.warn("导出refundOrder文件结束：" + fileName);
		    exportService.updateExport(export, true);
		} catch (Exception ex) {
		    logger.error("导出refundOrder文件出错：" + fileName, ex);
		    exportService.updateExport(export, false);
		}
	    }
	});
    }

    @Override
    public void acctLogExport(final Export export, final AcctLogQuery acctLogQuery) {
	logger.warn("导出acctLog文件产生：" + export.getFileName());
	executor.execute(new Runnable() {
	    @Override
	    public void run() {
		String fileName = export.getFileName();
		try {
		    int count = 0;
		    int totalCount = 0;
		    int sheetCount = 1;
		    Workbook workbook = new SXSSFWorkbook(MAX_CACHE);
		    Sheet sheet = workbook.createSheet("1"); // 创建表格
		    acctLogHeader(sheet, export.getIsDownStream(), export.getIsPartner());
		    for (int page = 1; page <= export.getPageCount(); page++) {
			acctLogQuery.setCurrentPage(page);
			Result<List<AcctLog>> result = acctLogService.getAcctLogExport(acctLogQuery);
			if (result.isSuccess()) {
			    totalCount += result.getModule().size();
			    if (totalCount > MAX_ROW) {
				count = 0;
				totalCount = result.getModule().size();
				sheetCount += 1;
				sheet = workbook.createSheet(sheetCount + ""); // 创建表格
				acctLogHeader(sheet, export.getIsDownStream(), export.getIsPartner());
			    }

			    for (AcctLog acctLog : result.getModule()) {
				count++;
				Row row = sheet.createRow(count);
				row.createCell(0).setCellValue(acctLog.getId());
				row.createCell(1).setCellValue(acctLog.getItemId());
				row.createCell(2).setCellValue(acctLog.getItemName());
				row.createCell(3).setCellValue(acctLog.getUserId());
				row.createCell(4).setCellValue(acctLog.getAcctId());
				if (export.getIsPartner()) {
				    if (acctLog.getAmtInEx() != null || acctLog.getAmtIn() != null) {
					row.createCell(5).setCellValue(acctLog.getAmtInExDouble());// 导出金额是数字，方便excel计算
				    }
				    if (acctLog.getAmtOutEx() != null || acctLog.getAmtOut() != null) {
					row.createCell(6).setCellValue(acctLog.getAmtOutExDouble());
				    }
				    row.createCell(7).setCellValue(acctLog.getTradeTypeDesc());
				    row.createCell(8).setCellValue(acctLog.getBillId());
				    row.createCell(9).setCellValue(acctLog.getBillTypeDesc());
				    row.createCell(10).setCellValue(timeFormat.format(acctLog.getGmtCreate()));
				    if (acctLog.getBizOrderId() != null) {
					row.createCell(11).setCellValue(acctLog.getBizOrderId());
				    }
				    row.createCell(12).setCellValue(acctLog.getStatusDesc());
				} else {
				    if (acctLog.getAmtIn() != null) {
					row.createCell(5).setCellValue(acctLog.getAmtInDouble());// 导出金额是数字，方便excel计算
				    }
				    if (acctLog.getAmtOut() != null) {
					row.createCell(6).setCellValue(acctLog.getAmtOutDouble());
				    }
				    if (acctLog.getAmtBalance() != null) {
					row.createCell(7).setCellValue(acctLog.getAmtBalanceDouble());
				    }
				    row.createCell(8).setCellValue(acctLog.getTradeTypeDesc());
				    row.createCell(9).setCellValue(acctLog.getBillId());
				    row.createCell(10).setCellValue(acctLog.getBillTypeDesc());
				    row.createCell(11).setCellValue(timeFormat.format(acctLog.getGmtCreate()));
				    if (acctLog.getBizOrderId() != null) {
					row.createCell(12).setCellValue(acctLog.getBizOrderId());
				    }
				    if (acctLog.getBizId() != null) {
					row.createCell(13).setCellValue(acctLog.getBizId());
				    }
				    row.createCell(14).setCellValue(acctLog.getStatusDesc());
				    if (!export.getIsDownStream()) {
					row.createCell(15).setCellValue(acctLog.getUpStreamId());
				    }
				}
			    }
			}
		    }

		    OutputStream os = new FileOutputStream(fileName, false);
		    workbook.write(os);
		    os.flush();
		    os.close();

		    logger.warn("导出acctLog文件结束：" + fileName);
		    exportService.updateExport(export, true);
		} catch (Exception ex) {
		    logger.error("导出acctLog文件出错：" + fileName, ex);
		    exportService.updateExport(export, false);
		}
	    }
	});
    }

    @Override
    public void smsOrderExport(final Export export, final SmsOrderQuery smsOrderQuery) {
	logger.warn("导出smsOrder文件产生：" + export.getFileName());
	executor.execute(new Runnable() {
	    @Override
	    public void run() {
		String fileName = export.getFileName();
		try {
		    int count = 0;
		    int totalCount = 0;
		    int sheetCount = 1;
		    Workbook workbook = new SXSSFWorkbook(MAX_CACHE);
		    Sheet sheet = workbook.createSheet(sheetCount + ""); // 创建表格
		    smsOrderHeader(sheet, export.getIsDownStream());
		    for (int page = 1; page <= export.getPageCount(); page++) {
			smsOrderQuery.setCurrentPage(page);
			Result<List<SmsOrder>> result = smsOrderService.getSmsOrderExport(smsOrderQuery);
			if (result.isSuccess()) {
			    totalCount += result.getModule().size();
			    if (totalCount > MAX_ROW) {
				count = 0;
				totalCount = result.getModule().size();
				sheetCount += 1;
				sheet = workbook.createSheet(sheetCount + ""); // 创建表格
				smsOrderHeader(sheet, export.getIsDownStream());
			    }

			    for (SmsOrder smsOrder : result.getModule()) {
				count++;
				Row row = sheet.createRow(count);
				row.createCell(0).setCellValue(smsOrder.getId());
				row.createCell(1).setCellValue(smsOrder.getDownstreamSerialno());
				row.createCell(2).setCellValue(smsOrder.getUidCount());
				row.createCell(3).setCellValue(smsOrder.getSuccCount());
				row.createCell(4).setCellValue(smsOrder.getFailCount());
				if (smsOrder.getUserId() != null) {
				    row.createCell(5).setCellValue(smsOrder.getUserId());
				}
				row.createCell(6).setCellValue(smsOrder.getItemName());
				if (smsOrder.getItemPrice() != null) {
				    row.createCell(7).setCellValue(smsOrder.getItemPriceDouble());
				}
				if (smsOrder.getAmount() != null) {
				    row.createCell(8).setCellValue(smsOrder.getAmountDouble()); // 导出金额是数字，方便excel计算
				}
				row.createCell(9).setCellValue(timeFormat.format(smsOrder.getGmtCreate()));
				row.createCell(10).setCellValue(smsOrder.getStatusDesc());
				if (!export.getIsDownStream()) {
				    row.createCell(11).setCellValue(smsOrder.getUpstreamSerialno());
				    if (smsOrder.getItemCostPrice() != null) {
					row.createCell(12).setCellValue(smsOrder.getItemCostPriceDouble());
				    }
				    if (smsOrder.getCostPrice() != null) {
					row.createCell(13).setCellValue(smsOrder.getCostPriceDouble());
				    }
				    if (smsOrder.getUpstreamId() != null) {
					row.createCell(14).setCellValue(smsOrder.getUpstreamId());
				    }
				}
			    }
			}
		    }

		    OutputStream os = new FileOutputStream(fileName, false);
		    workbook.write(os);
		    os.flush();
		    os.close();

		    logger.warn("导出smsOrder文件结束：" + fileName);
		    exportService.updateExport(export, true);
		} catch (Exception ex) {
		    logger.error("导出smsOrder文件出错：" + fileName, ex);
		    exportService.updateExport(export, false);
		}
	    }
	});
    }

    @Override
    public void smsSupplyExport(final Export export, final SmsSupplyQuery smsSupplyQuery) {
	logger.warn("导出smsSupply文件产生：" + export.getFileName());
	executor.execute(new Runnable() {
	    @Override
	    public void run() {
		String fileName = export.getFileName();
		try {
		    int count = 0;
		    int totalCount = 0;
		    int sheetCount = 1;
		    Workbook workbook = new SXSSFWorkbook(MAX_CACHE);
		    Sheet sheet = workbook.createSheet("1"); // 创建表格
		    smsSupplyHeader(sheet);
		    for (int page = 1; page <= export.getPageCount(); page++) {
			smsSupplyQuery.setCurrentPage(page);
			Result<List<SmsSupply>> result = smsOrderService.getSmsSupplyExport(smsSupplyQuery);
			if (result.isSuccess()) {
			    totalCount += result.getModule().size();
			    if (totalCount > MAX_ROW) {
				count = 0;
				totalCount = result.getModule().size();
				sheetCount += 1;
				sheet = workbook.createSheet(sheetCount + ""); // 创建表格
				supplyOrderHeader(sheet);
			    }

			    for (SmsSupply smsSupply : result.getModule()) {
				count++;
				Row row = sheet.createRow(count);
				row.createCell(0).setCellValue(smsSupply.getId());
				row.createCell(1).setCellValue(smsSupply.getBizOrderId());
				row.createCell(2).setCellValue(smsSupply.getUpstreamSerialno());
				row.createCell(3).setCellValue(smsSupply.getItemUid());
				row.createCell(4).setCellValue(smsSupply.getCount());
				if (smsSupply.getItemPrice() != null) {
				    row.createCell(5).setCellValue(smsSupply.getItemPriceDouble());
				}
				if (smsSupply.getAmount() != null) {
				    row.createCell(6).setCellValue(smsSupply.getAmountDouble()); // 导出金额是数字，方便excel计算
				}
				if (smsSupply.getCostPrice() != null) {
				    row.createCell(7).setCellValue(smsSupply.getItemCostPriceDouble());
				}
				if (smsSupply.getCostPrice() != null) {
				    row.createCell(8).setCellValue(smsSupply.getCostPriceDouble());
				}
				row.createCell(9).setCellValue(smsSupply.getSupplyTraderId());
				row.createCell(10).setCellValue(timeFormat.format(smsSupply.getGmtCreate()));
				row.createCell(11).setCellValue(smsSupply.getSupplyStatusDesc());
				row.createCell(12).setCellValue(smsSupply.getUpstreamMemo());
			    }
			}
		    }

		    OutputStream os = new FileOutputStream(fileName, false);
		    workbook.write(os);
		    os.flush();
		    os.close();

		    logger.warn("导出smsSupply文件结束：" + fileName);
		    exportService.updateExport(export, true);
		} catch (Exception ex) {
		    logger.error("导出smsSupply文件出错：" + fileName, ex);
		    exportService.updateExport(export, false);
		}
	    }
	});
    }

    private void bizOrderHeader(Sheet sheet, boolean isDownStream) {
	Row header = sheet.createRow(0);
	header.createCell(0).setCellValue("订单号");
	header.createCell(1).setCellValue("下游流水号");
	header.createCell(2).setCellValue("客户手机号");
	header.createCell(3).setCellValue("手机区域");
	header.createCell(4).setCellValue("代理商号");
	header.createCell(5).setCellValue("商品");
	header.createCell(6).setCellValue("面额(元)");
	header.createCell(7).setCellValue("交易金额 (元)");
	header.createCell(8).setCellValue("交易时间");
	header.createCell(9).setCellValue("订单状态");
	header.createCell(10).setCellValue("摘要");
	if (!isDownStream) {
	    header.createCell(11).setCellValue("上游流水号");
	    header.createCell(12).setCellValue("扩展信息");
	    header.createCell(13).setCellValue("平台成本价(元)");
	    header.createCell(14).setCellValue("实际成本价(元)");
	    header.createCell(15).setCellValue("上游供货商编号");
	}
    }

    private void supplyOrderHeader(Sheet sheet) {
	Row header = sheet.createRow(0);
	header.createCell(0).setCellValue("供货单号");
	header.createCell(1).setCellValue("订单号");
	header.createCell(2).setCellValue("上游流水号");
	header.createCell(3).setCellValue("客户手机号");
	header.createCell(4).setCellValue("代理商号");
	header.createCell(5).setCellValue("商品");
	header.createCell(6).setCellValue("面额(元)");
	header.createCell(7).setCellValue("交易金额 (元)");
	header.createCell(8).setCellValue("平台成本价(元)");
	header.createCell(9).setCellValue("实际成本价(元)");
	header.createCell(10).setCellValue("供货商编号");
	header.createCell(11).setCellValue("交易时间");
	header.createCell(12).setCellValue("供货单状态");
	header.createCell(13).setCellValue("摘要");
	header.createCell(14).setCellValue("终结");
	header.createCell(15).setCellValue("补充");
	header.createCell(16).setCellValue("人工补充");
    }

    private void refundOrderHeader(Sheet sheet, boolean isDownStream) {
	Row header = sheet.createRow(0);
	header.createCell(0).setCellValue("退款单号");
	header.createCell(1).setCellValue("订单号");
	header.createCell(2).setCellValue("支付单号");
	header.createCell(3).setCellValue("代理商号");
	header.createCell(4).setCellValue("商品编号");
	header.createCell(5).setCellValue("退款金额 (元)");
	header.createCell(6).setCellValue("退款时间");
	header.createCell(7).setCellValue("退款类型");
	header.createCell(8).setCellValue("退款状态");
	header.createCell(9).setCellValue("摘要");
	if (!isDownStream) {
	    header.createCell(10).setCellValue("操作员");
	}
    }

    private void acctLogHeader(Sheet sheet, boolean isDownStream, boolean isPartner) {
	Row header = sheet.createRow(0);
	header.createCell(0).setCellValue("资金流水号");
	header.createCell(1).setCellValue("商品编号");
	header.createCell(2).setCellValue("商品名");
	header.createCell(3).setCellValue("代理商号");
	header.createCell(4).setCellValue("账户编号");
	if (isPartner) {
	    header.createCell(5).setCellValue("收入金额(元)");
	    header.createCell(6).setCellValue("支出金额(元)");
	    header.createCell(7).setCellValue("收支类型");
	    header.createCell(8).setCellValue("交易号");
	    header.createCell(9).setCellValue("交易类型");
	    header.createCell(10).setCellValue("交易时间");
	    header.createCell(11).setCellValue("订单号");
	    header.createCell(12).setCellValue("状态");
	} else {
	    header.createCell(5).setCellValue("收入金额(元)");
	    header.createCell(6).setCellValue("支出金额(元)");
	    header.createCell(7).setCellValue("瞬间余额(元)");
	    header.createCell(8).setCellValue("收支类型 ");
	    header.createCell(9).setCellValue("交易号");
	    header.createCell(10).setCellValue("交易类型");
	    header.createCell(11).setCellValue("交易时间");
	    header.createCell(12).setCellValue("订单号");
	    header.createCell(13).setCellValue("业务编号 ");
	    header.createCell(14).setCellValue("状态");
	    if (!isDownStream) {
		header.createCell(15).setCellValue("供货商编号");
	    }
	}
    }

    private void smsOrderHeader(Sheet sheet, boolean isDownStream) {
	Row header = sheet.createRow(0);
	header.createCell(0).setCellValue("订单号");
	header.createCell(1).setCellValue("下游流水号");
	header.createCell(2).setCellValue("手机数");
	header.createCell(3).setCellValue("成功数");
	header.createCell(4).setCellValue("失败数");
	header.createCell(5).setCellValue("代理商号");
	header.createCell(6).setCellValue("商品");
	header.createCell(7).setCellValue("售价(元)");
	header.createCell(8).setCellValue("交易金额 (元)");
	header.createCell(9).setCellValue("交易时间");
	header.createCell(10).setCellValue("订单状态");
	if (!isDownStream) {
	    header.createCell(11).setCellValue("上游流水号");
	    header.createCell(12).setCellValue("成本价(元)");
	    header.createCell(13).setCellValue("成本金额(元)");
	    header.createCell(14).setCellValue("上游供货商编号");
	}
    }

    private void smsSupplyHeader(Sheet sheet) {
	Row header = sheet.createRow(0);
	header.createCell(0).setCellValue("供货单号");
	header.createCell(1).setCellValue("订单号");
	header.createCell(2).setCellValue("上游流水号");
	header.createCell(3).setCellValue("客户手机号");
	header.createCell(4).setCellValue("短信数");
	header.createCell(5).setCellValue("售价(元)");
	header.createCell(6).setCellValue("交易金额 (元)");
	header.createCell(7).setCellValue("成本价(元)");
	header.createCell(8).setCellValue("成本金额(元)");
	header.createCell(9).setCellValue("供货商编号");
	header.createCell(10).setCellValue("交易时间");
	header.createCell(11).setCellValue("供货单状态");
	header.createCell(12).setCellValue("摘要");
    }
}
