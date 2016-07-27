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

public class OrgAdminJobsTest {
	WebDriver driver;
  @Test
  public void createLibrary() {
	  LoginPage lp= new LoginPage(driver);
	  lp.get();
	  lp.setUsername("orgadmin_des@gems.com");
	  lp.setPassword("123456");
	  lp.submit();
	  OrgAdminJobs jobs= new OrgAdminJobs(driver);
	  jobs.createNewLibrary();	  
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
