package utility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

public class MyActions {
	static Logger log= Logger.getLogger(Log.class.getClass());
public static void click(WebDriver driver,By by)
{
	WebElement element= driver.findElement(by);
	
	JavascriptExecutor js = (JavascriptExecutor) driver;
	// Object style= js.executeScript("arguments[0].getAttribute('style');",element);
	
	js.executeScript("arguments[0].setAttribute('style.color', arguments[1]);",element, "Red");
	js.executeScript("arguments[0].style.border='5px groove green'", element);
	//	System.out.println(style);
//	 js.executeScript("arguments[0].setAttribute('style',"+style.toString()+");",element);
	try {
		try{
			WebElement modal=driver.findElement(By.cssSelector(".modal-backdrop.in"));
		if(modal.isDisplayed())
		{
			//Log.warn("Modal backdrop found while clicking - >"+by.toString());
			//System.out.println("Modal drop is displayed .. . .");
			// Wait for modal to close 
			(new WebDriverWait(driver,5)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".modal-backdrop.in"))); // waits for invisibility of the backdrop
		}
		}
			catch (Exception e) {
				//Log.info("Modal backdrop not found while clicking - >"+by.toString());
				//System.out.println("No modal backdrop ");
			}
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(by));
       
//       String changeCssScript="arguments[0].setAttribute('border-color', '#C76C0C')";
//        String borderSize="arguments[0].setAttribute('border', '2px')";
//        ((JavascriptExecutor) driver).executeScript(borderSize,driver.findElement(by));
//        ((JavascriptExecutor) driver).executeScript(changeCssScript,driver.findElement(by));
//       
        driver.findElement(by).click();
	}
    catch (StaleElementReferenceException sere) {
        // simply retry finding the element in the refreshed DOM
        driver.findElement(by).click();
    }
    catch (TimeoutException toe) {
        log.error("Element identified by " + by.toString() + " was not clickable after 10 seconds");
    }    
    try{
    	js.executeScript("arguments[0].removeAttribute('style');",element);
    }
    catch (Exception e) {
		// If element is not found after click, it is ok !
	}
	
   
}

public static void click(WebDriver driver, WebElement element) {
	// TODO Auto-generated method stub
//	 String changeCssScript="arguments[0].setAttribute('border-color', '#C76C0C')";
//     String borderSize="arguments[0].setAttribute('border', '2px')";
//     ((JavascriptExecutor) driver).executeScript(borderSize,element);
//     ((JavascriptExecutor) driver).executeScript(changeCssScript,element); 
    
//	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("arguments[0].setAttribute('style.color', arguments[1]);",element, "Red");
	js.executeScript("arguments[0].style.border='5px groove green'", element);
	try {
		try{
			WebElement modal=driver.findElement(By.cssSelector(".modal-backdrop.in"));
		if(modal.isDisplayed())
		{
		//	Log.warn("Modal backdrop found while clicking - >"+element.toString());
		//	System.out.println("Modal drop is displayed .. . .");
			// Wait for modal to close 
			(new WebDriverWait(driver,10)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".modal-backdrop.in"))); // waits for invisibility of the backdrop
		}
		}
		catch (Exception e) {
		//	Log.info("Modal backdrop not found while clicking - >"+element.toString());
		//	System.out.println("No modal backdrop ");
		}
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(element));
         element.click();
	}
    catch (StaleElementReferenceException sere) {
        // simply retry finding the element in the refreshed DOM
    	element.click();
    }
    
    catch (TimeoutException toe) {
        log.error("Element : " + element+ " was not clickable after 10 seconds");
    }
    try{
    	js.executeScript("arguments[0].removeAttribute('style');",element);
    }
    catch (Exception e) {
		// If element is not found after click, it is ok !
	} 
}
public static void Highlight(WebDriver driver, WebElement element)
{
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("arguments[0].style.border='5px solid yellow'", element); 
}
}
