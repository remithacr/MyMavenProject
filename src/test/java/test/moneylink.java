package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class moneylink {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		WebDriver driver=new FirefoxDriver();
		
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream(new File(System.getProperty("user.dir")+"//src//test//java//test//xpath.properties"));
		prop.load(fis);
		//System.out.println(prop.getProperty("email"));
		driver.get("http://portfolio.rediff.com/portfolio-login");
		
		driver.findElement(By.id("useremail")).sendKeys("remitha14@rediffmail.com");
		//driver.findElement(By.id(prop.getProperty("email"))).sendKeys("remitha14@rediffmail.com");
		driver.findElement(By.id(prop.getProperty("continue"))).click();
		//driver.get("http://cobalt.idc.devlab.motive.com/ssc");
		//driver.findElement(By.id(prop.getProperty("username"))).sendKeys("12341234556");
	}

}
