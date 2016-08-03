package scripts;

import java.io.File;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pom.HomePage;
import pom.LibraryExceptional;
import pom.LibraryPage;
import pom.LoginPage;

public class LibraryModule {
	WebDriver driver;
	final String INPUTFILEPATH="E:\\GEMS\\registrationData.xls";
	@DataProvider(name = "LibraryData")
	public Object[][] createData() {
		Object[][] retObjArr = getTableArray(
				INPUTFILEPATH,
				"Library", "POINTER");
		return (retObjArr);
	}
	public static void main(String[] args) {
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { LibraryModule.class });
		testng.addListener(tla);
		testng.run();
		}
	@DataProvider(name = "LibraryException") // for exceptional Library read data from, another sheet
	public Object[][] createLibraryData() {
		Object[][] retObjArr = getTableArray(
				INPUTFILEPATH,
				"LibraryMathsDept", "POINTER");
		return (retObjArr);
	}
	@Test(dataProvider = "LibraryData",enabled=false)
	public void testLibrary(String userName, String password,
			String libraryName, String admissionYear, String entranceRound) {
		LoginPage lp = new LoginPage(driver);
		lp.get();
		lp.setUsername(userName);
		lp.setPassword(password);
		HomePage hm = lp.submit();

		hm.changeRoleToLibrarian();
		LibraryPage lib= new LibraryPage(driver);
		lib.configureLibary(libraryName); //Done!
		 lib.configureRuleBook(); 
	}
	@Test(dataProvider = "LibraryException") 	// Use different pom for this test
	public void testExceptionalLibrary(String userName, String password,
			String libraryName, String admissionYear, String entranceRound) {
		LoginPage lp = new LoginPage(driver);
		lp.get();
		lp.setUsername(userName);
		lp.setPassword(password);
		HomePage hm = lp.submit();

		hm.changeRoleToLibrarian();
		LibraryExceptional lib= new LibraryExceptional(driver);	// New POM used
		lib.configureLibary(libraryName); //Done!
		 lib.configureRuleBook(); 
	}
		
	
	@AfterMethod
	public void cleanUp() {
		// Dimension dim= new Dimension(30, 30); // Makes window small
		// driver.manage().window().setSize(dim); // So we can know test is
		// complete
		JavascriptExecutor jsx = (JavascriptExecutor) driver;
		jsx.executeScript("alert('Test complete ...')");
		// JOptionPane.showMessageDialog(null, "Test complete ...");
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

