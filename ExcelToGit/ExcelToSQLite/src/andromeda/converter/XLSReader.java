package andromeda.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class XLSReader {

	private String filename;
	private Workbook excelBook;
	private Sheet excelSheet;

	private int noOfRows = 0;
	private int noOfCols = 0;
	
	private int rowIndex = 0;

	public XLSReader(String filePathName) {
		try {
			filename = filePathName;
			File inputWorkbook = new File(filename);
			excelBook = Workbook.getWorkbook(inputWorkbook);
			excelSheet = excelBook.getSheet(0);

			noOfCols = excelSheet.getColumns();
			noOfRows = excelSheet.getRows();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public boolean hasNext() {
		if (rowIndex + 1 > noOfRows)
			return false;
		return true;
	}
	

	public ArrayList<String> getNext() {
		ArrayList<String> result = new ArrayList<String>();
		for(int i = 0; i < noOfCols; i++) {
			result.add(excelSheet.getCell(i, rowIndex).getContents());
		}
		rowIndex += 1;
		return result;
	}
	
	
	public ArrayList<String> getRow(int index) {
		
		if(index > noOfRows)
			throw new IllegalStateException("index is " + index + " but size is " + noOfRows);
		
		ArrayList<String> result = new ArrayList<String>();
		for(int i = 0; i < noOfCols; i++) {
			result.add(excelSheet.getCell(index, i).getContents());
		}
		return result;
	}
}

