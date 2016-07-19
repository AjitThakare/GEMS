package scripts;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utility.Log;


public class GoogleSignIn {
	Logger log1= Logger.getLogger(Log.class.getClass());
	private static WebDriver driver;
		
	@Test
	public void signIn()
	{
		Log.startTestCase("Login to Google");
		driver.get("https://www.gmail.com");
		log1.info("Sign in started");
	}
	
	@BeforeClass
  public void setUp() {
		org.openqa.selenium.firefox.internal.ProfilesIni profile = new org.openqa.selenium.firefox.internal.ProfilesIni();
		org.openqa.selenium.firefox.FirefoxProfile firefoxProfile = profile
				.getProfile("SeleniumUser");
		driver = new FirefoxDriver(firefoxProfile);
		System.out.println("User Profile Loaded successfully !");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.close();
	}
}
