package com.longan.mng.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.longan.biz.dataobject.Stock;

public class StockDownloadExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 65535;

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + model.get("fileName") + ".xls");

	List<Stock> result = new ArrayList<Stock>();
	if (model.get("stockList") instanceof List) {
	    result = (List<Stock>) model.get("stockList");
	    if (result.size() > MAX_ROW) {
		int sheetCount = result.size() / MAX_ROW + 1;
		for (int i = 1; i <= sheetCount; i++) {
		    buildSheet(model, workbook, result, i);
		}
	    } else {
		buildSheet(model, workbook, result, 1);
	    }
	}
    }

    private void buildSheet(Map<String, Object> model, HSSFWorkbook workbook, List<Stock> result, int sheetIndex) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
	header.createCell(0).setCellValue("序列号");
	header.createCell(1).setCellValue("卡号");
	header.createCell(2).setCellValue("卡密");

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
	    Stock stock = result.get(set + i);
	    row.createCell(0).setCellValue(stock.getCardSerialNo());
	    row.createCell(1).setCellValue(stock.getCardId());
	    row.createCell(2).setCellValue(stock.getCardPwd());
	}
    }
}