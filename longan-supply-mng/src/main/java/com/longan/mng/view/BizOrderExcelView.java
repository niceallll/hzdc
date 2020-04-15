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

import com.longan.biz.dataobject.BizOrder;

public class BizOrderExcelView extends AbstractExcelView {
    private static final int MAX_ROW = 65535;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");

	List<BizOrder> result = new ArrayList<BizOrder>();
	if (model.get("bizOrderList") instanceof List) {
	    result = (List<BizOrder>) model.get("bizOrderList");
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

    private void buildSheet(Map<String, Object> model, HSSFWorkbook workbook, List<BizOrder> result, int sheetIndex,
	    boolean isDownStream, boolean isPartner) {
	HSSFSheet sheet = workbook.createSheet(sheetIndex + ""); // 创建表格
	HSSFRow header = sheet.createRow(0);// 定义表格的表头
	header.createCell(0).setCellValue("订单号");
	header.createCell(1).setCellValue("下游流水号");
	header.createCell(2).setCellValue("客户手机号");
	header.createCell(3).setCellValue("手机区域");
	header.createCell(4).setCellValue("代理商号");
	header.createCell(5).setCellValue("商品");
	header.createCell(6).setCellValue("面额");
	header.createCell(7).setCellValue("交易金额 (元)");
	header.createCell(8).setCellValue("交易时间 ");
	header.createCell(9).setCellValue("订单状态 ");
	header.createCell(10).setCellValue("摘要 ");
	if (!isDownStream) {
	    header.createCell(11).setCellValue("上游流水号 ");
	    header.createCell(12).setCellValue("扩展信息 ");
	    header.createCell(13).setCellValue("平台成本价(元)");
	    header.createCell(14).setCellValue("实际成本价(元)");
	    header.createCell(15).setCellValue("上游供货商编号");
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
	    BizOrder bizOrder = result.get(set + i);
	    row.createCell(0).setCellValue(bizOrder.getId());
	    row.createCell(1).setCellValue(bizOrder.getDownstreamSerialno());
	    row.createCell(2).setCellValue(bizOrder.getItemUid());
	    row.createCell(3).setCellValue(bizOrder.getUidAreaInfo());
	    if (bizOrder.getUserId() != null) {
		row.createCell(4).setCellValue(bizOrder.getUserId());
	    }
	    row.createCell(5).setCellValue(bizOrder.getItemName());
	    row.createCell(6).setCellValue(bizOrder.getItemFacePriceDouble());
	    if (isPartner) {
		row.createCell(7).setCellValue(bizOrder.getAmountDummyDouble());
	    } else {
		row.createCell(7).setCellValue(bizOrder.getAmountDouble()); // 导出金额是数字，方便excel计算
	    }
	    row.createCell(8).setCellValue(timeFormat.format(bizOrder.getGmtCreate()));
	    row.createCell(9).setCellValue(bizOrder.getStatusDesc());
	    row.createCell(10).setCellValue(bizOrder.getMemo());
	    if (!isDownStream) {
		row.createCell(11).setCellValue(bizOrder.getUpstreamSerialno());
		row.createCell(12).setCellValue(bizOrder.getItemPosId());
		if (bizOrder.getItemCostPriceDesc() != null) {
		    row.createCell(13).setCellValue(bizOrder.getItemCostPriceDesc());
		}
		if (bizOrder.getActualCostDesc() != null) {
		    row.createCell(14).setCellValue(bizOrder.getActualCostDesc());
		}
		if (bizOrder.getUpstreamId() != null) {
		    row.createCell(15).setCellValue(bizOrder.getUpstreamId());
		}
	    }
	}
    }
}
