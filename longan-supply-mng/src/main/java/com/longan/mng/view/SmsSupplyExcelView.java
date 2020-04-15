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

import com.longan.biz.dataobject.SmsSupply;

public class SmsSupplyExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 65535;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");

	List<SmsSupply> list = new ArrayList<SmsSupply>();
	if (model.get("smsSupplyList") instanceof List) {
	    list = (List<SmsSupply>) model.get("smsSupplyList");
	    if (list.size() > MAX_ROW) {
		int sheetCount = list.size() / MAX_ROW + 1;
		for (int i = 1; i <= sheetCount; i++) {
		    buildSheet(model, workbook, list, i);
		}
	    } else {
		buildSheet(model, workbook, list, 1);
	    }
	}
    }

    private void buildSheet(Map<String, Object> model, HSSFWorkbook workbook, List<SmsSupply> list, int sheetIndex) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
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

	int size = 0;
	if (list.size() > MAX_ROW) {
	    size = (list.size() - MAX_ROW * (sheetIndex - 1)) > MAX_ROW ? MAX_ROW : (list.size() - MAX_ROW * (sheetIndex - 1));
	} else {
	    size = list.size();
	}

	int pos = MAX_ROW * (sheetIndex - 1);
	for (int i = 0; i < size; i++) {
	    HSSFRow row = sheet.createRow(i + 1);
	    SmsSupply smsSupply = list.get(pos + i);
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
