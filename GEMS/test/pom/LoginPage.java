package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import utility.Log;
import utility.MyActions;

public class LoginPage extends LoadableComponent<LoginPage> {
	static Logger log= Logger.getLogger(Log.class.getClass());
	private WebDriver driver;
	@FindBy(id = "j_username")
	WebElement username;
	String user;

	@FindBy(id = "password-1")
	WebElement password;

	@FindBy(id = "signin_submit")
	WebElement submitButton;

	@FindBy(css = ".take-exam>a")
	WebElement registrationLink;

	public void setUsername(String string) {
		username.sendKeys(string);
		user=string;
	}

	public String  getUsername() {
		return user;
	}

	public void setPassword(String pass) {
		password.sendKeys(pass);
	}

	public  HomePage submit() {
		MyActions.click(driver, submitButton);
		HomePage homepage= new HomePage(driver);
		Log.info("##########################");
		Log.info("* * * User Logged In * * *");
		Log.info("with Username "+user);
		Log.info("##########################");
		return homepage;
	}
	

	

	@Override
	protected void isLoaded() throws Error {
		try {
			//driver.get("http://uat.deccansociety.com/login.htm");
			Assert.assertEquals("Welcome to GEMS!", driver.getTitle(),"Not Loaded yet ...");
		} catch (Exception e) {
			System.out
					.println("Element not found cuz get is not called yet .........");
		}
	}
	@Override
	protected void load() {
		driver.get("http://uat.deccansociety.com/login.htm");
	}
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

}
