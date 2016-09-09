package scripts;

import java.io.File;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utility.Log;

@RunWith(value = Parameterized.class)
public class Registration {
	Logger log1= Logger.getLogger(Log.class.getClass());
	private static WebDriver driver;

	@DataProvider(name = "RegistrationDetails")
	public Object[][] createData1() {
		Object[][] retObjArr = getTableArray(
				"test\\resources\\data\\registrationData.xls", "Data",
				"POINTER");
		return (retObjArr);
	}

	@Test(dataProvider = "RegistrationDetails")
	public void login(String firstName, String middleName, String lastName,
			String emailId, String mobileNo) {
		Log.startTestCase("Registration for "+firstName);
		driver.get("http://uat.deccansociety.com/login.htm");
		driver.findElement(By.cssSelector(".take-exam>a")).click(); // Open new
																	// applicant
																	// registration
																	// page

		Select courseName = new Select(driver.findElement(By
				.id("selInstituteCourseStrength")));
		courseName.selectByIndex(2);   // It is HARD COded right now, to be taken from file in future

		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("middleName")).sendKeys(middleName);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.id("emailIdName")).sendKeys(emailId);
		driver.findElement(By.id("mobileNo")).sendKeys(mobileNo);
		
		boolean validEmail=true;
		int[] generateCount = { 1 };
		try{
		do {
			// Boolean isPresent = driver.findElements(By.yourLocator).size() > 0; findelements will not throw exception, when popup is not there .. 
			if (driver.findElements(By.id("popup_ok")).size()>0) { // Pop up present ?  For valid email it will not generate popup
				validEmail=false;
				Log.info("Generating new email ... "+emailId +" is already taken !");
				emailId= generateNewEmail(firstName, lastName, generateCount);
			System.out.println("Email already exist in system...");
			driver.findElement(By.id("popup_ok")).click();         // Close the pop up
			driver.findElement(By.id("emailIdName")).clear();
			driver.findElement(By.id("emailIdName")).sendKeys(emailId); // And generate new Email
			driver.findElement(By.cssSelector("div [id=applicantRegistrationTLB]")).click();
			
			}
			else{
			validEmail= true;
			generateCount[0] = 1;
			Log.info("EmailID is unique - "+emailId);
			}
			
		}while(!validEmail);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		 driver.findElement(By.id("btn_addAdmissionApplicant")).click(); // submit button
		
		try{
		 if(driver.findElement(By.cssSelector("div [id=popup_message]")).getText().contains("Successfully")) // will never fail unless network/service unavailable
		{
			System.out.println("Applicant registered successfully.");
			System.out.println("Applicant EmailId is: "+emailId);
			driver.findElement(By.id("popup_ok")).click();
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(4000); // to observe page before closing the browser
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String generateNewEmail(String firstName, String lastName,
			int[] generateCount) { //generate new email if current emailId is already registered!
		generateCount[0] = generateCount[0] + 1;
		return firstName.toLowerCase() + "_" + lastName.toLowerCase()
				+ generateCount[0] + "@fyba.com";
	}

	@BeforeClass
	public static void setUp() throws Exception {

		org.openqa.selenium.firefox.internal.ProfilesIni profile = new org.openqa.selenium.firefox.internal.ProfilesIni();
		org.openqa.selenium.firefox.FirefoxProfile firefoxProfile = profile
				.getProfile("SeleniumUser");
		driver = new FirefoxDriver(firefoxProfile);
		System.out.println("User Profile Loaded successfully !");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDown() throws Exception {

		// Close the browser
		driver.close();
		//driver.quit();
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
