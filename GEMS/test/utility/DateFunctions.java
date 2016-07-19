package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFunctions {

	Date date;
	int dd;
	int mm;
	int yyyy;
	String monthName;
	
	public Date getDate() {
		return date;
	}

	public int getDd() {
		return dd;
	}

	public int getMm() {
		return mm;
	}

	public int getYyyy() {
		return yyyy;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDd(int dd) {
		this.dd = dd;
	}

	public void setMm(int mm) {
		this.mm = mm;
	}

	public void setYyyy(int yyyy) {
		this.yyyy = yyyy;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	
public DateFunctions()
{

	this.dd=0;
	this.mm=0;
	this.yyyy=0;
	this.monthName=null;
	this.date=null;
}
	public DateFunctions(String Inputdate) {
		super();
		
		try {
			this.date = new SimpleDateFormat("dd/MM/yyyy").parse(Inputdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
		mm=cal.get(Calendar.MONTH)+1;
		dd=cal.get(Calendar.DAY_OF_MONTH);
		yyyy=cal.get(Calendar.YEAR);
		monthName=new SimpleDateFormat("MMMM").format(date);
	}
	public int getMonthNumber(String monthName)
	{
		int monthNumber=1;
		String Months[]={"January","February","March","April","May","June","July","August","September","October","November", "December"};
		for (String string : Months) {
			if(string.equals(monthName))
			{break;	
			}
			else
			{
				monthNumber++;
			}		
		}
		return monthNumber;
	}

	@Override
	public String toString() {
		return "DateFunctions [date is = " + date + ", dd=" + dd + ", mm=" + mm
				+ ", yyyy=" + yyyy + ", monthName=" + monthName + "]";
	}

	public static void main(String[] args) {
		
		DateFunctions df= new DateFunctions("30/06/2016");
		System.out.println(df.toString());
		
	}
	

}
