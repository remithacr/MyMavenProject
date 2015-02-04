package listeners;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.apache.commons.io.FileUtils;

import com.qtpselenium.framework.util.Xls_Reader;


public class CustomListener extends TestListenerAdapter implements ISuiteListener{

	public static Hashtable<String,String> table;
	public static String reportFolder;
	public static ArrayList<String> keys;
	
	public void onTestSuccess(ITestResult tr){
		report(tr.getName(),"PASS");
	}

	public void onTestFailure(ITestResult tr){
		report(tr.getName(),tr.getThrowable().getMessage());
	}
	
	public void onTestSkipped(ITestResult tr){
		report(tr.getName(),tr.getThrowable().getMessage());
	}
	
	
	public void report(String testName, String result){
		
		int iteration_number=1;
		while(table.containsKey(testName +" iteration " + iteration_number)){
			iteration_number++;
		}
		keys.add(testName + " iteration " + iteration_number);
		table.put(testName + " iteration " + iteration_number, result);
	}

	
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		System.out.println("Suite " +suite.getName() +" Finished");
		
		if(table!=null){
			System.out.println(table);
			System.out.println(keys);
			
			if(!(suite.getName().equals("My Project"))){
				Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir") +"//target//reports//" +reportFolder+ "//report.xlsx");
				xls.addSheet(suite.getName());
			
				xls.setCellData(suite.getName(), 0, 1, "Testcases");
				xls.setCellData(suite.getName(), 1, 1, "Result");
				
				for(int i=0;i<keys.size();i++){
					String key = keys.get(i);
					String value = table.get(key);
					
					xls.setCellData(suite.getName(), 0, i+2, key);
					xls.setCellData(suite.getName(), 1, i+2, value);
					
				}
			}
			
			table = null;
		}
		

		
	}

	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		System.out.println("Suite " +suite.getName()+ " Starting");
		
		if(table == null){
			table = new Hashtable<String,String>();
			keys  = new ArrayList<String>();
		
	
			if(reportFolder == null){
				Date date = new Date();
				reportFolder = date.toString().replace(":", "_");
				System.out.println(reportFolder);
				System.out.println(System.getProperty("user.dir"));
				
				File folder = new File(System.getProperty("user.dir") + "//target//reports//" + reportFolder);
				folder.mkdir();
			
			
				File src = new File(System.getProperty("user.dir") + "//target//reports//report_template.xlsx");
				File dest = new File(System.getProperty("user.dir") + "//target//reports//" +reportFolder+ "//report.xlsx");
				
				try {
					FileUtils.copyFile(src, dest);
				} catch (IOException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			

	}

		}
	


