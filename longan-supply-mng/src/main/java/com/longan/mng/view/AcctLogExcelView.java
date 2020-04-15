package com.longan.mng.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.longan.biz.dataobject.AcctLog;

public class AcctLogExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 65535;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");

	List<AcctLog> result = new ArrayList<AcctLog>();
	if (model.get("acctLogList") instanceof List) {
	    result = (List<AcctLog>) model.get("acctLogList");
	    boolean isDownStream = (Boolean) model.get("isDownStream");
	    boolean isPartner = (Boolean) model.get("isPartner");
	    if (result.size() > MAX_ROW) {
		int sheetCount = result.size() / MAX_ROW + 1;
		for (int i = 1; i <= sheetCount; i++) {
		    buildSheet(model, workbook, result, i, isDownStream, isPartner);
		}
	    } else {
		buildSheet(model, workbook, result, 1, isDownStream, isPartner);
	    }
	}
    }

    private void buildSheet(Map<String, Object> model, HSSFWorkbook workbook, List<AcctLog> result, int sheetIndex,
	    boolean isDownStream, boolean isPartner) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
	header.createCell(0).setCellValue("资金流水号 ");
	header.createCell(1).setCellValue("商品编号 ");
	header.createCell(2).setCellValue("商品名");
	header.createCell(3).setCellValue("代理商号");
	header.createCell(4).setCellValue("账户编号");
	if (isPartner) {
	    header.createCell(5).setCellValue("收入金额(元)");
	    header.createCell(6).setCellValue("支出金额(元)");
	    header.createCell(7).setCellValue("收支类型 ");
	    header.createCell(8).setCellValue("交易号 ");
	    header.createCell(9).setCellValue("交易类型 ");
	    header.createCell(10).setCellValue("交易时间 ");
	    header.createCell(11).setCellValue("订单号 ");
	    header.createCell(12).setCellValue("状态 ");
	} else {
	    header.createCell(5).setCellValue("收入金额(元)");
	    header.createCell(6).setCellValue("支出金额(元)");
	    header.createCell(7).setCellValue("瞬间余额(元) ");
	    header.createCell(8).setCellValue("收支类型 ");
	    header.createCell(9).setCellValue("交易号 ");
	    header.createCell(10).setCellValue("交易类型 ");
	    header.createCell(11).setCellValue("交易时间 ");
	    header.createCell(12).setCellValue("订单号 ");
	    header.createCell(13).setCellValue("业务编号 ");
	    header.createCell(14).setCellValue("状态 ");
	    if (!isDownStream) {
		header.createCell(15).setCellValue("供货商编号");
	    }
	}

	int size = 0;
	if (result.size() > MAX_ROW) {
	    size = (result.size() - MAX_ROW * (sheetIndex - 1)) > MAX_ROW ? MAX_ROW
		    : (result.size() - MAX_ROW * (sheetIndex - 1));
	} else {
	    size = result.size();
	}

	int set = MAX_ROW * (sheetIndex - 1);
	for (int i = 0; i < size; i++) {
	    HSSFRow row = sheet.createRow(i + 1);
	    AcctLog acctLog = result.get(set + i);
	    row.createCell(0).setCellValue(acctLog.getId());
	    row.createCell(1).setCellValue(acctLog.getItemId());
	    row.createCell(2).setCellValue(acctLog.getItemName());
	    row.createCell(3).setCellValue(acctLog.getUserId());
	    row.createCell(4).setCellValue(acctLog.getAcctId());
	    if (isPartner) {
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
		if (!isDownStream) {
		    row.createCell(15).setCellValue(acctLog.getUpStreamId());
		}
	    }
	}
    }
}
