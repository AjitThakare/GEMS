package scripts;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class FillForm {

	private static WebDriver driver;
	private WebDriverWait wait;
	private WebDriverWait smallWait;
	
	@DataProvider(name = "StudentDetails")
	public Object[][] createData1() {
		Object[][] retObjArr = getTableArray(
				"test\\resources\\data\\StudentDetails.xls", "FYBA-General",
				"POINTER");
		return (retObjArr);
	}
	@Test(dataProvider="StudentDetails")
 public void login(String firstName,String middleName,String lastName, 
		 String fullName,String emailID, String Category, String domicile, 
		 String tenthScore, String twelthScore, String physics, String chemistry, 
		 String mathematics, String mathematicsTotal, String biology, String bioTotal)
	{
		System.out.println("Logged In User : "+ firstName);
		driver.get("http://uat.deccansociety.com/login.htm");
		driver.findElement(By.id("j_username")).sendKeys(emailID);
		driver.findElement(By.id("password-1")).sendKeys("123456");
		driver.findElement(By.id("signin_submit")).click();
		//assertEquals(driver.getTitle(),"Student"); // Student logged in successfully
		
		
//		try{                 // If half of the forms are filled then enable it
//			if(driver.findElement(By.id("btnPDF")).isEnabled())
//		{
//			throw new SkipException("Application is submitted !!--------------------------");
//		}
//			else
//			{
//				System.out.println("Filling form now . . . .");
//			}
//	}
//		catch (Exception e) {
//			System.out.println("Exception thrown - Skipping this candidate");
//		}
		
		fillStudentDetails(firstName, middleName, lastName, fullName, emailID, Category, domicile);
		fillTenthDetails( tenthScore);
		fillTwelthDetails(twelthScore, physics, chemistry, mathematics, mathematicsTotal, biology, bioTotal);
	//	documentDetails();
		payment();
		confirmDetails();	
	}
	
	//@AfterMethod
	public void logout()
	{
		driver.findElement(By.cssSelector("a[class='btn dropdown-toggle']")).click();
		driver.findElement(By.cssSelector("a[href='./logout.json']")).click();
	}
	//@Test(dependsOnMethods ="login" ,dataProvider="StudentDetails")
	public void fillStudentDetails(String firstName,String middleName,String lastName, 
			 String fullName,String emailID, String Category, String domicile)
	{
		System.out.println("Filling STUDENT DETAILS for : "+ firstName);
		driver.findElement(By.cssSelector("li [href='#next']")).click();
		driver.findElement(By.id("appl10thMarksheetname")).clear();
		driver.findElement(By.id("appl10thMarksheetname")).sendKeys(fullName);
		
//			 Robot robot;
//		try {
//			robot = new Robot();
//			robot.keyPress(KeyEvent.VK_TAB);
//		   	 robot.keyRelease(KeyEvent.VK_TAB);
//		   	 robot.keyPress(KeyEvent.VK_SPACE);
//		   	 robot.keyRelease(KeyEvent.VK_SPACE);
//		Thread.sleep(2000);
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			 robot.keyPress(KeyEvent.VK_V);
//			 robot.keyRelease(KeyEvent.VK_V);
//			 robot.keyRelease(KeyEvent.VK_CONTROL);
//
//			 robot.keyPress(KeyEvent.VK_TAB);
//		   	 robot.keyRelease(KeyEvent.VK_TAB);
//		   	Thread.sleep(2000);
//		   	 robot.keyPress(KeyEvent.VK_TAB);
//		   	 robot.keyRelease(KeyEvent.VK_TAB);  
//		   	robot.keyPress(KeyEvent.VK_ENTER);
//		   	robot.keyRelease(KeyEvent.VK_ENTER);
//		   	 
//				
//		} catch (AWTException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
   	 
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.cssSelector("input[name='eMail']")).sendKeys(emailID); // email field should be disabled
		
		assertEquals(driver.findElement(By.cssSelector("input[name='eMail']")).getAttribute("readonly"),"true"); // email field should be disabled

		driver.findElement(By.id("applstudmotherName")).clear();
		driver.findElement(By.id("applstudmotherName")).sendKeys("Mother");
		
		
		

	         Select bloodGroup= new Select(driver.findElement(By.id("studentBloodgrp")));
	         bloodGroup.selectByValue("AB+");         
	         
	        	driver.findElement(By.id("dob")).click();
		Select year= new Select(driver.findElement(By.className("ui-datepicker-year")));
		year.selectByVisibleText("1994");
		Select month= new Select(driver.findElement(By.className("ui-datepicker-month")));
		month.selectByVisibleText("May");
		driver.findElement(By.xpath("//td[contains(number(),'13')]")).click();
		
		wait= new WebDriverWait(driver, 10);
		smallWait= new WebDriverWait(driver, 5);
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ui-datepicker-div"))); // Date overlay closes after some time
		driver.findElement(By.id("female")).click();
		
		Select motherTounge = new Select(driver.findElement(By.id("applstudmothertongue")));
		motherTounge.selectByValue("Marathi");
		driver.findElement(By.id("applstudaddr")).clear();
		driver.findElement(By.id("applstudaddr")).sendKeys("Address");
		driver.findElement(By.id("applPermanent")).clear();
		driver.findElement(By.id("applPermanent")).sendKeys("Address");
		
		Select country= new Select(driver.findElement(By.id("countryCombo")));
		country.selectByVisibleText("India");
		Select state= new Select(driver.findElement(By.id("stateCombo")));
		state.selectByVisibleText("Maharashtra");
		Select studentDomicile= new Select(driver.findElement(By.id("domicileCombo")));
		studentDomicile.selectByVisibleText("Maharashtra");
		Select city= new Select(driver.findElement(By.id("cityNameTxt")));
		city.selectByVisibleText("Pune");
		driver.findElement(By.id("txtdistrict")).clear();
		driver.findElement(By.id("txtdistrict")).sendKeys("Pune");
		driver.findElement(By.id("txtTaluka")).clear();
driver.findElement(By.id("txtTaluka")).sendKeys("Taluka");	
driver.findElement(By.id("applstudpincode")).clear();
driver.findElement(By.id("applstudpincode")).sendKeys("444444");
		
Select natioanality= new Select(driver.findElement(By.id("applstudnationality")));
natioanality.selectByVisibleText("Indian");

driver.findElement(By.id("applstudpincode")).clear();
driver.findElement(By.id("applstudpincode")).sendKeys("444444");

Select religion= new Select(driver.findElement(By.id("religionCombo")));
religion.selectByVisibleText("Hindu");

driver.findElement(By.id("applstudOtherKnownLanguages")).clear();
driver.findElement(By.id("applstudOtherKnownLanguages")).sendKeys("English");

Select quota= new Select(driver.findElement(By.id("admissionQuotaTypeCombo")));
System.out.println("Domicile from file is : "+domicile);
if(domicile.equals("MAHARASHTRA STUDENT")){
	
quota.selectByVisibleText(domicile);
}
else
{System.out.println("Domicile is :"+domicile);
	quota.selectByIndex(1);
}
Select reservedCategory= new Select(driver.findElement(By.id("applStudCategoryCombo")));
reservedCategory.selectByVisibleText(Category);

driver.findElement(By.id("applStudSubCasteCombo")).clear();
driver.findElement(By.id("applStudSubCasteCombo")).sendKeys("SubCaste");
driver.findElement(By.id("applStudCasteCombo")).clear();
driver.findElement(By.id("applStudCasteCombo")).sendKeys("Caste");


driver.findElement(By.cssSelector("li [href='#next']")).click();



	}	
	
	//@Test (dependsOnMethods ="fillStudentDetails")
	public void fillTenthDetails ( String tenthScore)
	{
		try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[14]"))); // Need time to skip Modal 
	}catch (Exception e) {
		System.out.println("Exception occured : "+ e.getClass().toString());
	}
		driver.findElement(By.id("optpercent10")).click(); // Select Percent system
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("txt10marks")).clear();
		driver.findElement(By.id("txt10marks")).sendKeys(tenthScore);// Marks obtained
		driver.findElement(By.id("txt10outof")).clear();
		driver.findElement(By.id("txt10outof")).sendKeys("600"); // Total marks
		
		driver.findElement(By.id("tenthSeatNo")).clear();
		driver.findElement(By.id("tenthSeatNo")).sendKeys("654654"); // Seat number
	
		Select yearOfPassing= new Select(driver.findElement(By.id("applstud10yop")));  // Year of passing
		yearOfPassing.selectByVisibleText("2014");
		Select boardName= new Select(driver.findElement(By.id("applstud10thboardname"))); // Board of 10th 
		boardName.selectByVisibleText("MAHARASHTRA STATE BOARD");
		
		driver.findElement(By.id("applstud10thschoolname")).clear();
		driver.findElement(By.id("applstud10thschoolname")).sendKeys("TenthClassSchool"); // School Name
				
		driver.findElement(By.cssSelector("li [href='#next']")).click(); // Click on Next Button	
		}
	
	//@Test (dependsOnMethods="fillTenthDetails")
	public void fillTwelthDetails (String twelthScore, String physics, String chemistry, 
			 String mathematics, String mathematicsTotal, String biology, String bioTotal) // throws org.openqa.selenium.TimeoutException
	{
		
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[14]"))); // Need time to skip Modal 
		}catch (Exception e) {
			System.out.println("Exception occured : "+ e.getClass().toString());
		}
		/*if(driver.findElement(By.id("optpercent12")).isEnabled())
		{System.out.println("displayed");
		}*/
		driver.findElement(By.id("optpercent12")).click(); // Select Percent system
		driver.findElement(By.id("txt12marks")).clear();
		driver.findElement(By.id("txt12marks")).sendKeys(twelthScore);// Marks obtained
		driver.findElement(By.id("txt12outof")).clear();
		driver.findElement(By.id("txt12outof")).sendKeys("600"); // Total marks
		
		driver.findElement(By.id("twelthSeatNo")).clear();
		driver.findElement(By.id("twelthSeatNo")).sendKeys("654654"); // Seat number
	
		Select yearOfPassing= new Select(driver.findElement(By.id("applstud12yop")));  // Year of passing
		yearOfPassing.selectByVisibleText("2016");
		Select boardName= new Select(driver.findElement(By.id("applstud12thboardname"))); // Board of 12th 
		boardName.selectByVisibleText("MAHARASHTRA STATE BOARD");
		
//		driver.findElement(By.id("txt12Englishmarks")).clear();          // Only for Bsc - comp sci
//		driver.findElement(By.id("txt12Englishmarks")).sendKeys("50"); // Marks obtained in English
//		driver.findElement(By.id("txt12Englishoutof")).clear();
//		driver.findElement(By.id("txt12Englishoutof")).sendKeys("100"); // Out oFF marks
//				
//		driver.findElement(By.id("txt12Physicsmarks")).clear();
//		driver.findElement(By.id("txt12Physicsmarks")).sendKeys(physics);
//		driver.findElement(By.id("txt12Physicsoutof")).clear();
//		driver.findElement(By.id("txt12Physicsoutof")).sendKeys("100");
//		
//		driver.findElement(By.id("txt12Mathematicsmarks")).clear();
//		driver.findElement(By.id("txt12Mathematicsmarks")).sendKeys(mathematics);
//		driver.findElement(By.id("txt12Mathematicsoutof")).clear();
//		driver.findElement(By.id("txt12Mathematicsoutof")).sendKeys(mathematicsTotal);
//		
//		driver.findElement(By.id("txt12Chemistrymarks")).clear();
//		driver.findElement(By.id("txt12Chemistrymarks")).sendKeys(chemistry);
//		driver.findElement(By.id("txt12Chemistryoutof")).clear();
//		driver.findElement(By.id("txt12Chemistryoutof")).sendKeys("100");
//
//		driver.findElement(By.id("txt12Biologymarks")).clear();
//		driver.findElement(By.id("txt12Biologymarks")).sendKeys(biology);
//		driver.findElement(By.id("txt12Biologyoutof")).clear();
//		driver.findElement(By.id("txt12Biologyoutof")).sendKeys(bioTotal);  
//		
		driver.findElement(By.id("applstud12thschoolname")).clear();
		driver.findElement(By.id("applstud12thschoolname")).sendKeys("Twelth Class School"); // School Name
				
		driver.findElement(By.cssSelector("li [href='#next']")).click(); // Click on Next Button	
	}
	
//	@Test (dependsOnMethods="fillTwelthDetails")
	public void documentDetails()
	{
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[14]"))); // Need time to skip Modal 
		}catch (Exception e) {
			System.out.println("Exception occured : "+ e.getClass().toString());
		}
		driver.findElement(By.cssSelector("li [href='#next']")).click(); // Click on Next Button	
	}
//	@Test (dependsOnMethods="documentDetails")
	public void payment()
	{
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[14]"))); // Need time to skip Modal 
		}catch (Exception e) {
			System.out.println("Exception occured : "+ e.getClass().toString());
		}
		driver.findElement(By.id("cashrd")).click();
		driver.findElement(By.cssSelector("li [href='#finish']")).click();
		
	}
	
//	@Test (dependsOnMethods="payment")
	public void confirmDetails()
			{
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("popup_overlay")));
	}catch (Exception e) {
		System.out.println("Exception occured at Confirm details: "+ e.getClass().toString());
	}
		
		
		driver.findElement(By.id("popup_ok")).click();
		
		driver.findElement(By.id("declarationChk")).click();
		driver.findElement(By.id("btnSubmit")).click();
		try {
			smallWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("popup_overlay")));
	}catch (Exception e) {
		System.out.println("Exception occured at Confirm details: "+ e.getClass().toString());
	}
		driver.findElement(By.id("popup_ok")).click();
		
		try {
			smallWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("popup_overlay")));
	}catch (Exception e) {
		System.out.println("Exception occured at Confirm details: "+ e.getClass().toString());
	}		
	}
	
	@BeforeMethod
	public void beforeClass() {
		org.openqa.selenium.firefox.internal.ProfilesIni profile = new org.openqa.selenium.firefox.internal.ProfilesIni();
		org.openqa.selenium.firefox.FirefoxProfile firefoxProfile = profile
				.getProfile("SeleniumUser");
		driver = new FirefoxDriver(firefoxProfile);
		System.out.println("User Profile Loaded successfully !");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass() {
		//driver.close();
	}
	public String[][] getTableArray(String xlFilePath, String sheetName,
			String tableName) {
		String[][] tabArray = null;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
			Sheet sheet = workbook.getSheet(sheetName);
			Cell tableStart = sheet.findCell(tableName);

			int startRow, startCol, endRow, endCol, ci, cj;

			startRow = tableStart.getRow();
			startCol = tableStart.getColumn();

			Cell tableEnd = sheet.findCell(tableName, startCol + 1,
					startRow + 1, 100, 64000, false);

			endRow = tableEnd.getRow();
			endCol = tableEnd.getColumn();

			System.out.println("startRow=" + startRow + ", endRow=" + endRow
					+ ", " + "startCol=" + startCol + ", endCol=" + endCol);
			tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
			ci = 0;

			for (int i = startRow + 1; i < endRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < endCol; j++, cj++) {
					tabArray[ci][cj] = sheet.getCell(j, i).getContents();
				}
			}
		} catch (Exception e) {
			System.out.println("error in getTableArray()");

		}

		return (tabArray);
	}
}
