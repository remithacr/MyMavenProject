package com.qtpselenium.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.IdentityScope;
import java.security.Key;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import listeners.CustomListener;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;

public class TestBase {
	
	public static Properties prop;
	public static Logger APPLICATION_LOG;
	//public static WebDriver driver;
	public static RemoteWebDriver driver;
	
	public  static void init(){
		if(prop ==  null){
			prop = new Properties();
			try {
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resource\\Project.properties");
				prop.load(fs);
				System.out.println("property file reading : " + prop.getProperty("test"));
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void initLogs(Class<?> class1){
		
		FileAppender appender = new FileAppender();
		appender.setFile(System.getProperty("user.dir") + "//target//reports//" +CustomListener.reportFolder + "//"+ class1.getName() + ".log");
		appender.setLayout(new PatternLayout());
		appender.setAppend(false);
		appender.activateOptions();
		
		APPLICATION_LOG = Logger.getLogger("devpinoylogger");
		APPLICATION_LOG.setLevel(Level.INFO);
		APPLICATION_LOG.addAppender(appender);
		
		
	}
	
	public static void validatRunModes(String suiteName,String testName,String dataRunMode){
		
		init();
		boolean suiteRunmode = Utility.isSuiteRunnable(suiteName, new Xls_Reader(System.getProperty("user.dir") + "\\Suite.xlsx"));
		System.out.println("suite run mode : " +suiteRunmode);
		boolean testRunmode = Utility.isTestRunnable(testName, new Xls_Reader(System.getProperty("user.dir") + "\\" + Constants.PORTFOLIO_SUITE +".xlsx"));
		System.out.println("Test run mode : " +testRunmode);
		System.out.println("Test data run mode : " +dataRunMode);
		
		boolean dataMode=false;
		if(dataRunMode.equals(Constants.RUNMODE_YES))
			dataMode=true;
		
		if(!(suiteRunmode && testRunmode && dataMode))
		throw new SkipException("skipped");
	}
	
	public static void openBrowser(String browserName) throws MalformedURLException{
		/*if(browserName.equals("Firefox")){
			FirefoxBinary fbinary = new FirefoxBinary(new File(prop.getProperty("firefox_binary_path")));
			driver = new FirefoxDriver(fbinary,null);
			//driver = new FirefoxDriver();
		}
		else if(browserName.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chrome_driver_path"));
			driver = new ChromeDriver();
		}
		else if(browserName.equals("iexplorer")){
			System.setProperty("webdriver.ie.driver", prop.getProperty("ie_driver_path"));
			driver = new InternetExplorerDriver();
		}*/
		
		DesiredCapabilities capability = new DesiredCapabilities();
		
		if(browserName.equals("Firefox")){
			capability.setBrowserName("firefox");
		}else if(browserName.equals("Chrome")){
			capability.setBrowserName("chrome");
		}
		capability.setPlatform(Platform.WINDOWS);
		
		
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}
	
	
	public static void navigate(String identifier_key){
		driver.get(prop.getProperty(identifier_key));
		
	}
	
	public static void click(String identifier_key){
		try{
			if(identifier_key.endsWith("_xpath")){
			driver.findElement(By.xpath(prop.getProperty(identifier_key))).click();
		}
			else if(identifier_key.endsWith("_id")){
				System.out.println(prop.getProperty(identifier_key));
				System.out.println(identifier_key);
				driver.findElement(By.id(prop.getProperty(identifier_key))).click();
				driver.findElement(By.id(prop.getProperty(identifier_key))).click();
			//driver.findElement(By.id(prop.getProperty("continue"))).click();
			//System.out.println(driver.findElement(By.id("emailsubmit")).isDisplayed());
			//driver.findElement(By.id("emailsubmit")).click();
		}
			else if(identifier_key.endsWith("_name")){
				driver.findElement(By.name(prop.getProperty(identifier_key))).click();
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage() + "Element not found" + identifier_key );
			
		}
	}
	
	public static boolean verifyTitle(String identifier_key){
		if(prop.getProperty(identifier_key).equals(driver.getTitle())){
			return true;
		}
		else
			return false;
		}

	
	public static void input(String identifier_key,String data){
		if(identifier_key.endsWith("_xpath")){
			driver.findElement(By.xpath(prop.getProperty(identifier_key))).sendKeys(data);
			
		}
		else if(identifier_key.endsWith("_id")){
			driver.findElement(By.id(prop.getProperty(identifier_key))).sendKeys(data);
		}
		else if(identifier_key.endsWith("_name")){
			driver.findElement(By.name(prop.getProperty(identifier_key))).sendKeys(data);
		}
	}
	
	
	public static boolean isElementPresent(String identifier_key){
		int size=0;
		if(identifier_key.endsWith("_xpath")){
			size=driver.findElements(By.xpath(prop.getProperty(identifier_key))).size();
		}else if(identifier_key.endsWith("_id")){
			size=driver.findElements(By.id(prop.getProperty(identifier_key))).size();
		}
		else if(identifier_key.endsWith("_name")){
			size=driver.findElements(By.name(prop.getProperty(identifier_key))).size();
		}else
			size = driver.findElements(By.xpath(identifier_key)).size();
		
		
		if(size > 0)
			return true;
		else
			return false;
		
		
	}
	
	
	public static String getText(String identifier_key){
		String text=" ";
		
		if(identifier_key.endsWith("_xpath")){
			text = driver.findElement(By.xpath(prop.getProperty(identifier_key))).getText();
		}else if(identifier_key.endsWith("_id")){
			text = driver.findElement(By.id(prop.getProperty(identifier_key))).getText();
		}else if(identifier_key.endsWith("_name")){
			text = driver.findElement(By.name(prop.getProperty(identifier_key))).getText();
		}
		return text;
	}
	
	
	
	public static void doLogin(String browser,String userName,String password) throws InterruptedException, MalformedURLException{
		openBrowser(browser);
		navigate("testURL");
		
		click("money_xpath");
		click("portfolio_xpath");
		
		Assert.assertTrue(verifyTitle("porfolio_title"), "Title dont match");
		Assert.assertTrue(isElementPresent("emailaddress_xpath"),"Element not found - emailaddress_xpath");
		
		
		input("emailaddress_xpath", userName);
		driver.findElement(By.xpath(prop.getProperty("emailaddress_xpath"))).sendKeys(Keys.ENTER);
		
		//System.out.println("before");
		//Assert.assertTrue(isElementPresent("continue_id"),"Element not found - continue_id");
		//System.out.println("after");
		//Thread.sleep(10000);
		//driver.findElement(By.id(prop.getProperty("continue"))).click();
		
		//Assert.assertTrue(isElementPresent("password_xpath"),"Element not found - password_xpath");
		input("password_xpath",password);
		click("login_xpath");
	
	}
	
	public static void defaultLogin(String browser) throws InterruptedException, MalformedURLException{
		doLogin(browser, prop.getProperty("username"), prop.getProperty("password"));
	}
	
	public static void quit(){
		if(driver != null){
		driver.quit();
		driver = null;
		}
	}
	
	
}
