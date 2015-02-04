package test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestDate {
	public static void main(String args[]) throws ParseException{

	
		
		Date currentDate = new Date();
		System.out.println(currentDate);
		
		String date="05/14/2014";
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date dateToBeSlected = formatter.parse(date); 
		
		System.out.println(dateToBeSlected);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToBeSlected);
		
		int year = cal.get(Calendar.YEAR);
		
		SimpleDateFormat mon = new SimpleDateFormat("MMMM");
		String month = mon.format(dateToBeSlected);
		
		String monthYearToBeSelected = month + " " + year;
		System.out.println(monthYearToBeSelected);
	}
}