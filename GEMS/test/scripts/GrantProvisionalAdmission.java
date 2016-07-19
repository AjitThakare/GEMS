package scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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
import pom.ProvisionalAdmission;

public class GrantProvisionalAdmission {
	WebDriver driver;

	@DataProvider(name = "provisionalAdmssn")
	public Object[][] createData1() {
		Object[][] retObjArr = getTableArray(
				"test\\resources\\data\\registrationData.xls",
				"provisionalAdmission", "POINTER");
		return (retObjArr);
	}

	@Test(dataProvider = "provisionalAdmssn")
	public void grantProvisionalAdmission(String userName, String password,
			String program, String admissionYear, String entranceRound) {
		LoginPage lp = new LoginPage(driver);
		lp.get();
		lp.setUsername(userName);
		lp.setPassword(password);
		HomePage hm = lp.submit();

		hm.changeRoleToAdmissionAdmin();
		ProvisionalAdmission pa = hm.gotoProvisionalAdmissionPage(driver);
		 pa.get();
		 pa.setProgram(program);
		 pa.setAdmissionYear(admissionYear);
		 pa.setEntranceRound(entranceRound);

		String[][] stdData = getTableArray(
				"test\\resources\\data\\registrationData.xls",
				"provisionalAdmission", "LIST");
				int index = 0;
		for (String[] strings : stdData) {
			String name = stdData[index][0];
			String email = stdData[index][1];
			String programName = stdData[index][2];
			String blnSponsor = stdData[index][3];
			String sponsorName = stdData[index][4];

			pa.grantProvisionalAdmissionTo(name, email, programName,
					blnSponsor, sponsorName);
			index++;
			}

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
