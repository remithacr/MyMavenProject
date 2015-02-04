package com.qtpselenium.framework.portfolio;

import java.net.MalformedURLException;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.framework.util.Constants;
import com.qtpselenium.framework.util.TestBase;
import com.qtpselenium.framework.util.TestDataProvider;
import com.qtpselenium.framework.util.Utility;
import com.qtpselenium.framework.util.Xls_Reader;

public class LoginTest extends TestBase{
	
	@BeforeTest
	public void beforeTest(){
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class, dataProvider="suiteADataProvider")
	public void loginTest(Hashtable<String, String> table) throws InterruptedException, MalformedURLException{
		
		validatRunModes(Constants.PORTFOLIO_SUITE, "LoginTest", table.get(Constants.RUNMODE_COL));
		
		doLogin(table.get(Constants.BROWSER_COL), table.get(Constants.USERNAME_COL), table.get(Constants.PASSWORD_COL));
		
		quit();
		
	}	

	
	
}
