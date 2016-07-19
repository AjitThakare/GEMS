package scripts;

import java.io.File;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExistingUserChecker {
	private static WebDriver driver;
	private WebDriverWait wait;

	@DataProvider(name = "ExistingUserChecker")
	public Object[][] createData1() {
		Object[][] retObjArr = getTableArray(
				"test\\resources\\data\\registrationData.xls", "VerifyExistingUser",
				"POINTER");
		return (retObjArr);
	}
	@Test(dataProvider="ExistingUserChecker")
	 public void login(String firstName, String middleName, String lastName,
				String emailId, String mobileNo)
		{
		wait= new WebDriverWait(driver, 10);
			System.out.println("Logged In User : "+ firstName);
			driver.get("http://uat.deccansociety.com/login.htm");
			driver.findElement(By.id("j_username")).sendKeys(emailId);
			driver.findElement(By.id("password-1")).sendKeys("123456");
			driver.findElement(By.id("signin_submit")).click();
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[14]"))); // Need time to skip Modal 
			}catch (Exception e) {
				System.out.println("Exception occured : "+ e.getClass().toString());
			}	
			driver.findElement(By.cssSelector("a[class='btn dropdown-toggle']")).click();
			System.out.println("Clicked Options button");
			driver.findElement(By.cssSelector("a[href='./logout.json']")).click();
			System.out.println("Log out");
			
			
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