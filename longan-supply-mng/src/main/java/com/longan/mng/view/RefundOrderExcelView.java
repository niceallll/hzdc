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

import com.longan.biz.dataobject.RefundOrder;

public class RefundOrderExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 65535;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");

	List<RefundOrder> result = new ArrayList<RefundOrder>();
	if (model.get("refundOrderList") instanceof List) {
	    result = (List<RefundOrder>) model.get("refundOrderList");
	    Boolean isDownStream = (Boolean) model.get("isDownStream");
	    if (result.size() > MAX_ROW) {
		int sheetCount = result.size() / MAX_ROW + 1;
		for (int i = 1; i <= sheetCount; i++) {
		    buildSheet(model, workbook, result, i, isDownStream);
		}
	    } else {
		buildSheet(model, workbook, result, 1, isDownStream);
	    }
	}

    }

    private void buildSheet(Map<String, Object> model, HSSFWorkbook workbook, List<RefundOrder> result, int sheetIndex,
	    Boolean isDownStream) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
	header.createCell(0).setCellValue("退款单号");
	header.createCell(1).setCellValue("订单号");
	header.createCell(2).setCellValue("支付单号");
	header.createCell(3).setCellValue("代理商号");
	header.createCell(4).setCellValue("商品编号");
	header.createCell(5).setCellValue("退款金额 (元)");
	header.createCell(6).setCellValue("退款时间 ");
	header.createCell(7).setCellValue("退款类型 ");
	header.createCell(8).setCellValue("退款状态 ");
	header.createCell(9).setCellValue("摘要 ");
	if (!isDownStream) {
	    header.createCell(10).setCellValue("操作员 ");
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
	    RefundOrder refundOrder = result.get(set + i);
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
	    if (!isDownStream) {
		row.createCell(10).setCellValue(refundOrder.getOperName());
	    }
	}
    }
}