package com.qtpselenium.framework.util;

import java.util.Hashtable;

import javax.print.attribute.HashAttributeSet;

public class Utility {
	

	public static boolean isSuiteRunnable(String suiteName,Xls_Reader xls){
		int rows = xls.getRowCount(Constants.SUITENAME_SHEET);
		
		System.out.println("Rows in sheet:" + rows);
		
		for(int rNum=2; rNum<rows;rNum++){
			String xls_suite_name = xls.getCellData(Constants.SUITENAME_SHEET, Constants.SUITENAME_COL, rNum);
			//System.out.println("Suite " + xls_suite_name + "found in " + rNum + "row number.");
			if(xls_suite_name.equals(suiteName)){
				String xls_runmode = xls.getCellData(Constants.SUITENAME_SHEET, Constants.RUNMODE_COL, rNum);
				if(xls_runmode.equals(Constants.RUNMODE_YES))
					return true;
				
				else
					return false;
			}
			
			
		}
		return false;
		
	}
	
	public static boolean isTestRunnable(String testName,Xls_Reader xls){
	
		int rows = xls.getRowCount(Constants.TESTNAME_SHEET);
		System.out.println("Testcases row count : " + rows);
		
		for(int rNum = 2; rNum <= rows; rNum++){
			String xls_testname = xls.getCellData(Constants.TESTNAME_SHEET, Constants.TESTNAME_COL, rNum);
			System.out.println("Testcase in " + rNum + "row number " + xls_testname);
			
			if(xls_testname.equalsIgnoreCase(testName)){
				String xls_runmode = xls.getCellData(Constants.TESTNAME_SHEET, Constants.RUNMODE_COL, rNum);
				if(xls_runmode.equals(Constants.RUNMODE_YES))
					return true;
				else
					return false;
			}
		}
		return false;
	
}
	
public static Object[][] getData(String testName, Xls_Reader xls){
		
		int rows = xls.getRowCount(Constants.DATA_SHEET);
		System.out.println("Rows in Data Sheet " + rows);
		int testRow=1;
		for(testRow = 1; testRow <= rows; testRow++){
			if(xls.getCellData(Constants.DATA_SHEET, 0, testRow).equalsIgnoreCase(testName))
				break;
		}
		System.out.println("Test Case Row No : " + testRow );
		
		int dataStartColNum= testRow+1;
		int dataStartRowNum = testRow+2;
		
		
		int dataRow = 1;
		while(!xls.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum+dataRow).equals("")){
			dataRow++;
		}
		
		System.out.println("No of data Rows : " + dataRow);
				
		int dataCol=1;
		while(!xls.getCellData(Constants.DATA_SHEET, dataCol,dataStartColNum).equals("")){
			dataCol++;
		}
		
		System.out.println("No of data Cols : " + dataCol);
		
		Object [][] data = new Object[dataRow][1];
		
		int r=0;
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+dataRow;rNum++){
			Hashtable<String,String> table = new Hashtable<String,String>();
			for(int cNum=0;cNum<dataCol;cNum++){
				//System.out.print(data[r][cNum]= xls.getCellData(Constants.DATA_SHEET, cNum, rNum) + " " );
				table.put(xls.getCellData(Constants.DATA_SHEET, cNum, dataStartColNum) , xls.getCellData(Constants.DATA_SHEET, cNum, rNum));
			}
			
			data[r][0] = table;
			r++;
			
		}
		return data;
			
}
	
	
	}

