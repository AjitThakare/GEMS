package scripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pom.LoginPage;
import pom.OrgAdminJobs;
import utility.DataProviderClass;

public class NewProgramGenerator {
	private WebDriver driver;
	DataProviderClass dp;
	
	
  @Test
  public void createNewProgram() {
	  LoginPage lp= new LoginPage(driver);
	  lp.get();
	  lp.setUsername("orgadmin_des@gems.com");
	  lp.setPassword("123456");
	  lp.submit();
	  OrgAdminJobs jobs= new OrgAdminJobs(driver);
	  dp= new DataProviderClass();
	  String [] inputForNewQualification=dp.getSimpleArray("test\\resources\\data\\registrationData.xls", "NewQualification",
				"QUALIFICATION");
	  String [] inputForDeptProgMapping=dp.getSimpleArray("test\\resources\\data\\registrationData.xls", "NewQualification",
		"DEPTPROGMAPPING");
	String university=inputForNewQualification[0];
	String stream=inputForNewQualification[1];
	String degreeName=inputForNewQualification[2];
	String degreeType=inputForNewQualification[3];
	String duration=inputForNewQualification[4];
	String NumberOfSemOrYear=inputForNewQualification[5];
		//jobs.addNewQualification(university, stream, degreeName, degreeType, duration, NumberOfSemOrYear);
	String society=inputForDeptProgMapping[0];
	String campus=inputForDeptProgMapping[1];
	String institute=inputForDeptProgMapping[2];
	String departmentName=inputForDeptProgMapping[3];
	String universityName=inputForDeptProgMapping[4];
	String streamName=inputForDeptProgMapping[5];
	String programmeName=inputForDeptProgMapping[6];
	String abbreviation=inputForDeptProgMapping[7];
	String establishmentDate=inputForDeptProgMapping[8];
	String programmeType=inputForDeptProgMapping[9];
	String universityAssociation=inputForDeptProgMapping[10];
	String noOfClass=inputForDeptProgMapping[11];
	String departmentalPromotion=inputForDeptProgMapping[12];
	jobs.deptProgMapping(society, campus, institute, departmentName, universityName, streamName, programmeName, abbreviation, establishmentDate, programmeType, universityAssociation, duration, noOfClass, departmentalPromotion);
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
}
