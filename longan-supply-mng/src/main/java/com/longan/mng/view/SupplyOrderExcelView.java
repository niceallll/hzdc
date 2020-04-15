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

public class SupplyOrderExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 65535;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");

	List<SupplyOrder> result = new ArrayList<SupplyOrder>();
	if (model.get("supplyOrderList") instanceof List) {
	    result = (List<SupplyOrder>) model.get("supplyOrderList");
	    boolean isPartner = (Boolean) model.get("isPartner");
	    if (result.size() > MAX_ROW) {
		int sheetCount = result.size() / MAX_ROW + 1;
		for (int i = 1; i <= sheetCount; i++) {
		    buildSheet(model, workbook, result, i, isPartner);
		}
	    } else {
		buildSheet(model, workbook, result, 1, isPartner);
	    }
	}
    }

    private void buildSheet(Map<String, Object> model, HSSFWorkbook workbook, List<SupplyOrder> result, int sheetIndex,
	    boolean isPartner) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
	header.createCell(0).setCellValue("供货单号");
	header.createCell(1).setCellValue("订单号");
	header.createCell(2).setCellValue("上游流水号");
	header.createCell(3).setCellValue("客户手机号");
	header.createCell(4).setCellValue("代理商号");
	header.createCell(5).setCellValue("商品");
	header.createCell(6).setCellValue("面额");
	header.createCell(7).setCellValue("交易金额 (元)");
	header.createCell(8).setCellValue("平台成本价(元)");
	header.createCell(9).setCellValue("实际成本价(元)");
	header.createCell(10).setCellValue("供货商编号");
	header.createCell(11).setCellValue("交易时间 ");
	header.createCell(12).setCellValue("供货单状态 ");
	header.createCell(13).setCellValue("摘要 ");
	header.createCell(14).setCellValue("终结 ");
	header.createCell(15).setCellValue("补充 ");
	header.createCell(16).setCellValue("人工补充 ");

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
	    // row.createCell(0).setCellValue(supplyOrder.getId());
	    row.createCell(0).setCellValue(supplyOrder.getIdDesc());
	    row.createCell(1).setCellValue(supplyOrder.getBizOrderId());
	    row.createCell(2).setCellValue(supplyOrder.getUpstreamSerialno());
	    row.createCell(3).setCellValue(supplyOrder.getItemUid());
	    row.createCell(4).setCellValue(supplyOrder.getUserId());
	    row.createCell(5).setCellValue(supplyOrder.getItemName());
	    row.createCell(6).setCellValue(supplyOrder.getItemFacePriceDouble());
	    if (isPartner) {
		row.createCell(7).setCellValue(supplyOrder.getAmountDummyDouble());
	    } else {
		row.createCell(7).setCellValue(supplyOrder.getAmountDouble()); // 导出金额是数字，方便excel计算
	    }
	    if (supplyOrder.getSupplyCostPriceDesc() != null) {
		row.createCell(8).setCellValue(supplyOrder.getSupplyCostPriceDesc());
	    }
	    if (supplyOrder.getSupplyActualCostDesc() != null) {
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
