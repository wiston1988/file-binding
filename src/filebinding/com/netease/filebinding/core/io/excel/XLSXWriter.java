package com.netease.filebinding.core.io.excel;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXWriter extends ExcelWriter {

	public XLSXWriter ( OutputStream output){
		this.workBook = new XSSFWorkbook();		
		this.output = output;
	}
	
	public XLSXWriter ( OutputStream output ,String sheetName){
		this.workBook = new XSSFWorkbook();		
		this.output = output;
		createWorkSheet(sheetName);
	}
	
	
	public void createWorkSheet(String sheetName){
		if(sheetName == null || "".equals(sheetName)){
			//use poi default sheet name
			this.workSheet = this.workBook.createSheet(); 		
		}else{
			this.workSheet = this.workBook.createSheet(sheetName); 			
		}
		this.nextRow=0;
		this.currRow=0;
	}
	
	@Override
	protected void writeLine(Object[] line) {
		super.defaultWriteLine(line);
	}
	
}
