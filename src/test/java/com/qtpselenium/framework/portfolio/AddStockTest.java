package com.qtpselenium.framework.portfolio;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qtpselenium.framework.util.Constants;
import com.qtpselenium.framework.util.TestBase;
import com.qtpselenium.framework.util.TestDataProvider;

public class AddStockTest extends TestBase {
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="suiteADataProvider")
	public void addStockTest(Hashtable<String,String> table) throws MalformedURLException, InterruptedException, ParseException{
		
		validatRunModes(Constants.PORTFOLIO_SUITE, "AddStockTest", table.get(Constants.RUNMODE_COL));
		
		defaultLogin(table.get(Constants.BROWSER_COL));
		
		click("addstock_xpath");
		input("stockname_xpath", table.get(Constants.STOCK_NAME_COL));
		
		click("purchasedate_xpath");
		
		Date currentDate = new Date();
		//System.out.println(currentDate);
		
		String date=table.get(Constants.PURCHASEDATE_COL);
		System.out.println("date from excel : " + date);
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date dateToBeSlected = formatter.parse(date); 
		
		System.out.println("dateToBeSlected " +dateToBeSlected);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToBeSlected);
		
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		System.out.println("day of month: " +day );
		
		SimpleDateFormat mon = new SimpleDateFormat("MMMM");
		String month = mon.format(dateToBeSlected);
		
		String monthYearToBeSelected = month + " " + year;
		System.out.println(monthYearToBeSelected);
		
		while(true){
			String monYrDisplayed = getText("monthyear_xpath");
			if(monYrDisplayed.equals(monthYearToBeSelected))
				break;
			if(currentDate.after(dateToBeSlected))
				click("back_xpath");
			else
				click("front_xpath");
			
		}
		
		driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
		input("quantity_xpath", table.get(Constants.QUANTITY_COL));
		input("price_xpath",table.get(Constants.PRICE_COL));
		
		click("stockadded_xpath");
		
		Assert.assertTrue(isElementPresent("//a[text()='"+table.get(Constants.STOCK_NAME_COL)+"']"), "Stock not added");
		//Assert.assertTrue(isElementPresent("//a[text()='"+companyName+"']"),"element not present");
	}
	

}
