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

import com.longan.biz.dataobject.SupplyOrder;

public class SaleOrderExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 5000;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");

	List<SupplyOrder> result = new ArrayList<SupplyOrder>();
	if (model.get("saleOrderList") instanceof List) {
	    result = (List<SupplyOrder>) model.get("saleOrderList");
	    if (result.size() > MAX_ROW) {
		int sheetCount = result.size() / MAX_ROW + 1;
		for (int i = 1; i <= sheetCount; i++) {
		    buildSheet(workbook, result, i);
		}
	    } else {
		buildSheet(workbook, result, 1);
	    }
	}
    }

    private void buildSheet(HSSFWorkbook workbook, List<SupplyOrder> result, int sheetIndex) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
	header.createCell(0).setCellValue("手机号");
	header.createCell(1).setCellValue("供货单号");
	header.createCell(2).setCellValue("状态");
	header.createCell(3).setCellValue("商品");
	header.createCell(4).setCellValue("面额");
	header.createCell(5).setCellValue("交易时间 ");

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
	    SupplyOrder supplyOrder = result.get(set + i);
	    row.createCell(0).setCellValue(supplyOrder.getItemUid());
	    row.createCell(1).setCellValue(supplyOrder.getId());
	    row.createCell(2).setCellValue(supplyOrder.getSaleStatusDesc());
	    row.createCell(3).setCellValue(supplyOrder.getItemName());
	    row.createCell(4).setCellValue(supplyOrder.getItemFacePriceDouble());
	    row.createCell(5).setCellValue(timeFormat.format(supplyOrder.getGmtCreate()));
	}
    }
}
