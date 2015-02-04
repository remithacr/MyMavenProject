package com.qtpselenium.framework.util;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

	@DataProvider(name = "suiteADataProvider")
	public static Object[][] getDataSuiteA(Method m){
		
		System.out.println("Test Name : " + m.getName());
		Xls_Reader xls1 = new Xls_Reader(System.getProperty("user.dir") + "\\"+ Constants.PORTFOLIO_SUITE +".xlsx");
		return Utility.getData(m.getName(), xls1);
		
	}
}
