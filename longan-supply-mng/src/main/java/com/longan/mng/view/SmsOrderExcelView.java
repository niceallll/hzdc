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

import com.longan.biz.dataobject.SmsOrder;

public class SmsOrderExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 65535;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");

	List<SmsOrder> list = new ArrayList<SmsOrder>();
	if (model.get("smsOrderList") instanceof List) {
	    list = (List<SmsOrder>) model.get("smsOrderList");
	    boolean isDownStream = (Boolean) model.get("isDownStream");
	    if (list.size() > MAX_ROW) {
		int sheetCount = list.size() / MAX_ROW + 1;
		for (int i = 1; i <= sheetCount; i++) {
		    buildSheet(model, workbook, list, i, isDownStream);
		}
	    } else {
		buildSheet(model, workbook, list, 1, isDownStream);
	    }
	}
    }

    private void buildSheet(Map<String, Object> model, HSSFWorkbook workbook, List<SmsOrder> list, int sheetIndex,
	    boolean isDownStream) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
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

	int size = 0;
	if (list.size() > MAX_ROW) {
	    size = (list.size() - MAX_ROW * (sheetIndex - 1)) > MAX_ROW ? MAX_ROW : (list.size() - MAX_ROW * (sheetIndex - 1));
	} else {
	    size = list.size();
	}

	int pos = MAX_ROW * (sheetIndex - 1);
	for (int i = 0; i < size; i++) {
	    HSSFRow row = sheet.createRow(i + 1);
	    SmsOrder smsOrder = list.get(pos + i);
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
	    if (!isDownStream) {
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
