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


public class TopPerformAsset extends TestBase {
	
	@BeforeTest
	public void beforeTest(){
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class, dataProvider="suiteADataProvider")
	public void topPerformAsset(Hashtable<String,String> table) throws InterruptedException, MalformedURLException{
		
		System.out.println("Table : " + table);
		System.out.println("In test wats the runmode :" + table.get(Constants.RUNMODE_COL));
		validatRunModes(Constants.PORTFOLIO_SUITE, "TopPerformAsset", table.get(Constants.RUNMODE_COL));
		
		defaultLogin(table.get(Constants.BROWSER_COL));
		
		String topPerform = getText("text_xpath");
		System.out.println(topPerform);

		
		String t[] = topPerform.split("\\(");
		String companyName =t[0].trim();
		
		System.out.println(companyName);
		
		String percentage = t[1].split("\\)")[0].split("\\%")[0];
		//String t2[] =percentage.split("\\%");
		//String stock_percent = t2[0];
		
		
		System.out.println(percentage);
		
		Assert.assertTrue(isElementPresent("//a[text()='"+companyName+"']"),"element not present");
		Assert.assertTrue(isElementPresent("//td/span[text()='"+percentage+"']"),"element not present");
		
		quit();
	}
	
	

}
