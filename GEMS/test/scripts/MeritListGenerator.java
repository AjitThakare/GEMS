package scripts;

import java.io.File;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import pom.HomePage;
import pom.LoginPage;

public class MeritListGenerator {
	WebDriver driver;
	LoginPage loginpage;
	
	
	@Test(enabled= false)
	public HomePage login(String username, String password)
	{
		loginpage= new LoginPage(driver);
		  loginpage.get();
		  loginpage.setUsername(username);
		  loginpage.setPassword(password);
		  return loginpage.submit();		
	}
	@Test (enabled=false)
	  public HomePage changeRole(String username,String password){
		 
		  HomePage homepage=  login(username, password);	  
		  homepage.changeRoleToAdmissionAdmin();	
		  return homepage;
	  }
	@DataProvider(name="MeritListGen")
	  public Object[][] createData1() {
			Object[][] retObjArr = getTableArray(
					"test\\resources\\data\\registrationData.xls", "MeritListGen",
					"POINTER");
			return (retObjArr);
		}
	  @Test (dataProvider="MeritListGen")
	  public void generateMeritList(String username, String password, String program, String year, String round, String message)
	  {
		 HomePage homepage= changeRole(username, password);
		 homepage.generateMeritList(program, year, round, message);
  }
  @AfterMethod
  public void cleanUp() {
	  //driver.close();
  }

  @BeforeClass
  public void setUp() {
	  ProfilesIni profile = new ProfilesIni();
		FirefoxProfile firefoxProfile = profile.getProfile("SeleniumUser");
		driver = new FirefoxDriver(firefoxProfile);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
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
