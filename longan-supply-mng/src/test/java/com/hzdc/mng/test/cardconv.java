package com.hzdc.mng.test;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class cardconv {
    public static void main(String[] args) throws Exception {
	// conv();
    }

    private static void conv() {
	try {
	    InputStream isIn = new FileInputStream("c:/temp/虚拟产品入库模版.xls");
	    HSSFWorkbook workbook = new HSSFWorkbook(isIn);
	    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
		HSSFSheet sheet = workbook.getSheetAt(i);
		int iRowLen = sheet.getLastRowNum();
		BufferedWriter bwOut = new BufferedWriter(new FileWriter("c:/temp/cardid/" + sheet.getSheetName() + ".txt"));
		for (int iRow = 1; iRow <= iRowLen; iRow++) {
		    Row row = sheet.getRow(iRow);
		    String str1 = row.getCell(0).getStringCellValue();
		    String str2 = row.getCell(1).getStringCellValue();
		    String str3 = row.getCell(2).getStringCellValue();
		    String str4 = row.getCell(3).getStringCellValue();
		    bwOut.write(str1 + "," + str1 + "," + str2 + "," + str3 + "," + str4 + "\r\n");
		}
		bwOut.flush();
		bwOut.close();
	    }
	    isIn.close();
	    System.out.println("finish");
	} catch (Exception ex) {
	    ex.printStackTrace(System.out);
	}
    }
}
