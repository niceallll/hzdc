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

import com.longan.biz.dataobject.ItemPrice;

public class ItemPriceExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 65535;

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");

	List<ItemPrice> result = new ArrayList<ItemPrice>();
	if (model.get("itemPriceList") instanceof List) {
	    result = (List<ItemPrice>) model.get("itemPriceList");
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

    private void buildSheet(Map<String, Object> model, HSSFWorkbook workbook, List<ItemPrice> result, int sheetIndex) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
	header.createCell(0).setCellValue("商品编号");
	header.createCell(1).setCellValue("商品名称");
	header.createCell(2).setCellValue("省域");
	header.createCell(3).setCellValue("面值(元)");
	header.createCell(4).setCellValue("成本折扣(%)");
	header.createCell(5).setCellValue("价格1折扣(%)");
	header.createCell(6).setCellValue("价格2折扣(%)");
	header.createCell(7).setCellValue("价格3折扣(%)");
	header.createCell(8).setCellValue("价格4折扣(%)");
	header.createCell(9).setCellValue("拼购折扣(%)");
	header.createCell(10).setCellValue("供货商 ");
	header.createCell(11).setCellValue("状态 ");

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
	    ItemPrice itemPrice = result.get(set + i);
	    row.createCell(0).setCellValue(itemPrice.getItemId());
	    row.createCell(1).setCellValue(itemPrice.getItemName());
	    row.createCell(2).setCellValue(itemPrice.getItemSalesAreaDesc());
	    if (itemPrice.getPriceDesc(itemPrice.getItemFacePrice()) != null) {
		row.createCell(3).setCellValue(Double.parseDouble(itemPrice.getPriceDesc(itemPrice.getItemFacePrice())));// 转为数值型，方便导出后进行计算
	    }
	    if (itemPrice.getItemCostPriceDiscount() != null) {
		row.createCell(4).setCellValue(Double.parseDouble(itemPrice.getItemCostPriceDiscount()));
	    }
	    if (itemPrice.getItemSalesPriceDiscount() != null) {
		row.createCell(5).setCellValue(Double.parseDouble(itemPrice.getItemSalesPriceDiscount()));
	    }
	    if (itemPrice.getItemSalesPrice2Discount() != null) {
		row.createCell(6).setCellValue(Double.parseDouble(itemPrice.getItemSalesPrice2Discount()));
	    }
	    if (itemPrice.getItemSalesPrice3Discount() != null) {
		row.createCell(7).setCellValue(Double.parseDouble(itemPrice.getItemSalesPrice3Discount()));
	    }
	    if (itemPrice.getItemSalesPrice4Discount() != null) {
		row.createCell(8).setCellValue(Double.parseDouble(itemPrice.getItemSalesPrice4Discount()));
	    }
	    if (itemPrice.getItemDummyPriceDiscount() != null) {
		row.createCell(9).setCellValue(Double.parseDouble(itemPrice.getItemDummyPriceDiscount()));
	    }
	    row.createCell(10).setCellValue(itemPrice.getSupplyTraderName());
	    row.createCell(11).setCellValue(itemPrice.getStatusDesc());
	}
    }
}
