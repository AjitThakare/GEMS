package pom;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utility.DataProviderClass;
import utility.Log;
import utility.MyActions;

public class LibraryPage {
	private WebDriver driver;
@FindBy (linkText="Configuration") WebElement configuration;
@FindBy (linkText="Library Configuration") WebElement LibraryConfiguration;
@FindBy (linkText= "Member-Types")WebElement MemberTyes;
@FindBy (linkText= "Register Member") WebElement RegisterMember;
@FindBy (linkText= "Catalogue") WebElement Catalogue;
@FindBy (linkText= "Accession No. Sharing ") WebElement AccessionNumberSharing;
@FindBy (linkText= "Accession No. Transfer ") WebElement AccessionNumberTransfer;
@FindBy (linkText= "On-line Public Access ") WebElement OPAC;
@FindBy (linkText= "Rule Book") WebElement RuleBook;
@FindBy (linkText= "Configure Rule Book") WebElement ConfigureRB;
@FindBy (linkText= "View Rule Book") WebElement viewRB;
DataProviderClass dp;
WebElement libraryName;
List <WebElement> libraries=new ArrayList<WebElement>();


public LibraryPage(WebDriver driver) {
	PageFactory.initElements(driver, this); // to test after commenting 
	this.driver = driver;
	dp= new DataProviderClass();
}
public void configureRuleBook()
{
	MyActions.click(driver, Catalogue);
	MyActions.click(driver, RuleBook);
	MyActions.click(driver, ConfigureRB);
	
	String [] courseTypes = dp.getSimpleArray("test\\resources\\data\\registrationData.xls","Library", "COURSE");
Select select= new Select(driver.findElement(By.id("cmbCourseType-4")));
	for (String course : courseTypes) 
	{
		System.out.println("Setting Rules for:"+course+"--------------------------------------------------");
		Log.info("Setting Rules for:"+course+"--------------------------------------------------");
		select.selectByVisibleText(course);
		setGeneralRule(course);
		System.out.println("Done :"+course+"-------**************************------------");
		Log.info("Done :"+course+"-------------*************************-------------");
	//setFineRule(course);
	//setDepositRule(course);
	
	}
	
}
private void setGeneralRule(String course) {
	String [][] BookRules= dp.getTableArray("test\\resources\\data\\registrationData.xls","Library", "BooksGR");
	String [][] NewsLetterRules= dp.getTableArray("test\\resources\\data\\registrationData.xls","Library", "NewsLetterGR");
	String [][] RHB= dp.getTableArray("test\\resources\\data\\registrationData.xls","Library", "RHB");
	String [][] CDS= dp.getTableArray("test\\resources\\data\\registrationData.xls","Library", "CDS");
	String [][] DVDS= dp.getTableArray("test\\resources\\data\\registrationData.xls","Library", "DVDS");
	String [][] Journals= dp.getTableArray("test\\resources\\data\\registrationData.xls","Library", "JOURNALS");
	
	
	List <WebElement> totalLibraryItems=driver.findElements(By.cssSelector("[id*='myTabContent'] tr td")); // [id*='myTabContent'][class='tab-content'] tr
	int libTypeIndex=0; // 0=books, 1= journals,2=cds as from UI
	for (WebElement webElement : totalLibraryItems) 
	{	
		System.out.println("Webelement:"+webElement.getText());
		Log.info("Entering general rule for "+webElement.getText());
		MyActions.click(driver,webElement);
		int index=0;
		List <WebElement> txtFields=driver.findElements(By.cssSelector("[id*='libProductTypesDiv'][id$='"+libTypeIndex+"'] tr td input[type='text']"));
			if(webElement.getText().equals("BOOKS"))
			{
			for(int i=0;i<BookRules.length;i++) //no. of rows
				{
					for(int j=0;j<BookRules[0].length;j++) // no. of columns
					{	//MyActions.Highlight(driver,txtFields.get(index));
						txtFields.get(index).clear();
						txtFields.get(index).sendKeys(BookRules[i][j]);
						index++;
					}
				}
			}
			else if(webElement.getText().equals("JOURNALS/PERIODICALS/MAGAZINES"))
			{
				for(int i=0;i<Journals.length;i++) //no. of rows
					{
						for(int j=0;j<Journals[0].length;j++) // no. of columns
						{	txtFields.get(index).clear();
							txtFields.get(index).sendKeys(Journals[i][j]);
							index++;
						}
					}
				}
			else if(webElement.getText().equals("CDS"))
			{
				for(int i=0;i<CDS.length;i++) //no. of rows
					{
						for(int j=0;j<CDS[0].length;j++) // no. of columns
						{	txtFields.get(index).clear();
							txtFields.get(index).sendKeys(CDS[i][j]);
							index++;
						}
					}
				}
			else if(webElement.getText().equals("READING HALL BOOKS"))
			{
				for(int i=0;i<RHB.length;i++) //no. of rows
					{
						for(int j=0;j<RHB[0].length;j++) // no. of columns
						{	txtFields.get(index).clear();
							txtFields.get(index).sendKeys(RHB[i][j]);
							index++;
						}
					}
				}
			else if(webElement.getText().equals("DVDS"))
			{
				for(int i=0;i<DVDS.length;i++) //no. of rows
					{
						for(int j=0;j<DVDS[0].length;j++) // no. of columns
						{	txtFields.get(index).clear();
							txtFields.get(index).sendKeys(DVDS[i][j]);
							index++;
						}
					}
				}
			else if(webElement.getText().equals("NEWS LETTERS"))
			{
				//txtFields=driver.findElements(By.cssSelector("#libProductTypesDiv216-"+libTypeIndex+" tr td input[type='text']"));
				
				for(int i=0;i<NewsLetterRules.length;i++) //no. of rows
				{
					for(int j=0;j<NewsLetterRules[0].length;j++) // no. of columns
					{	txtFields.get(index).clear();
						txtFields.get(index).sendKeys(NewsLetterRules[i][j]);
						index++;
					}
				}
			}
		
		MyActions.click(driver,By.cssSelector("[id*='libProductTypesDiv'][id$='"+libTypeIndex+"'] tr td input[type='button']"));
		MyActions.click(driver, By.id("popup_ok"));
		libTypeIndex++;
	}
	
	
	
	
	
}
public void viewRuleBook()
{
	MyActions.click(driver, Catalogue);
	MyActions.click(driver, RuleBook);
	MyActions.click(driver, viewRB);
}

public void configureLibary(String libraryName)
{
	MyActions.click(driver, configuration);
	MyActions.click(driver, LibraryConfiguration);
	libraries=driver.findElements(By.cssSelector("div[id='accordion'] a td"));
	if(libraries.size()==0)
	{
		System.out.println("There are no Libraries added to this account");
		Log.info("There are no Libraries added to this account");
	}
	else{
		//JavascriptExecutor js= (JavascriptExecutor)driver;
		String LibName=libraries.get(0).getText();
		//System.out.println("innerHTML gives :"+js.executeScript("return arguments[0].innerHTML;",libraries.get(0)));
		if(LibName.equals(libraryName))
		{
			MyActions.click(driver, libraries.get(0));
			//configureLibraryMedia();    //uncheck it after finish
			//configureLibraryLevel();   //
			//configureFinancePolicy();
			//configureMemberTypes(); // Configures member types for included departments (Taken from file)
			
		}
	}
}
private void configureMemberTypes() { 
	List <WebElement>totalDeptRows= driver.findElements(By.cssSelector("table[id='ulDepartmentList4'] tr"));
	//MyActions.click(driver, By.cssSelector("input[value*='Finance Policies']"));	
	String[] requiredDepts= dp.getSimpleArray("test\\resources\\data\\registrationData.xls","Library", "Departments");
	
	for(int i=0;i<totalDeptRows.size();i++) // will select desired Media types
	{
		for (String policyName : requiredDepts) 
		{
			//System.out.println(totalDeptRows.get(i).getText()+ "-"+policyName);
			String item=totalDeptRows.get(i).getText().trim(); // to remove Space before these media types
			if(item.equals(policyName))
			{
				MyActions.click(driver, totalDeptRows.get(i).findElement(By.cssSelector("input")));
				MyActions.click(driver, By.linkText("Add"));
				String msg = driver.findElement(By.cssSelector("#addMemberTypes>tbody>tr>th")).getText().toString();
				if(msg.equals("All member types are added"))
				{
					Log.debug("Add members has message-"+msg);	
					Log.info("No need to add members");	
				}
				else{
					System.out.println("Message is -"+msg);
					// Code to add members
				}			
				MyActions.click(driver,By.linkText("Configure"));
				String [][] Data = dp.getTableArray("test\\resources\\data\\registrationData.xls","Library", "MemberTypeConfig");
				String [] memberNames=dp.getSimpleArray("test\\resources\\data\\registrationData.xls","Library", "Members"); 
			//	WebElement firstMember= driver.findElement(By.cssSelector("#viewMemberTypes tbody td strong")); first member is student. Flow is not checking what are types of member to add data, simply filling data in order it received it.
				List <WebElement> allMembers=driver.findElements(By.cssSelector("#viewMemberTypes>thead>tr tr:nth-child(2) input"));
				WebElement element;
				String inputToElement;
				boolean last=false;
				for(int rowIndex=0;rowIndex<memberNames.length;rowIndex++)					
				{	
					for(int col=0;col<Data[0].length;col++)
					{		element=allMembers.get((rowIndex*Data[0].length)+col);	
					System.out.println("Element "+((rowIndex*Data[0].length)+col) +" ="+"Array["+rowIndex+"]["+col+"]");
								if(col==5){ // Bad practice 
									col--;
									last=true;
									}
							inputToElement=Data[rowIndex][col+1];  // We have stored data in nxt cell // Exceptional case
							if(last)
							{
								col++;
								last=false;
							}
							System.out.println("input string is-"+inputToElement);
					if(element.getAttribute("type").equals("text"))	
					{
						if(element.isEnabled())
						{
						element.clear();
						element.sendKeys(inputToElement);
						}
						else{
							System.out.println("Disabled element ");
						}
					}
					else if(element.getAttribute("type").equals("checkbox"))
					{
						if(inputToElement.equalsIgnoreCase("Yes"))
						{
							element.click();
							System.out.println("Barcode is emailId");
						}
						else{
							System.out.println("Barcode is not emailId");
							Log.info("Barcode is not emailId");
						}
						}
					else
					{
						MyActions.click(driver, element);
					}
					
					}
					MyActions.click(driver, By.id("popup_ok"));
				}
				MyActions.click(driver, By.cssSelector("#divMemberTypes .close"));
				break;
			}
		}
	}
//	driver.findElement(By.cssSelector("table[id='availablePolicies4'] tr input[value='Save']")).sendKeys(""); // save Button
//	MyActions.click(driver,driver.findElement(By.cssSelector("table[id='availablePolicies4'] tr input[value='Save']"))); // save Button
//	MyActions.click(driver, By.id("popup_ok"));
//	MyActions.click(driver, By.id("popup_ok"));
}
private void configureFinancePolicy() {
	
	MyActions.click(driver, By.cssSelector("input[value*='Finance Policies']"));	
	String[] requiredPolicies= dp.getSimpleArray("test\\resources\\data\\registrationData.xls","Library", "FinancePolicies");
	List <WebElement>totalPolicies= driver.findElements(By.cssSelector("table[id='availablePolicies4'] tr"));
	for(int i=0;i<totalPolicies.size();i++) // will select desired Media types
	{
		for (String policyName : requiredPolicies) 
		{
			System.out.println(totalPolicies.get(i).getText()+ "-"+policyName);
			String item=totalPolicies.get(i).getText().trim(); // to remove Space before these media types
			if(item.equals(policyName))
			{
				MyActions.click(driver, totalPolicies.get(i).findElement(By.cssSelector("input")));
				break;
			}
		}
	}
	driver.findElement(By.cssSelector("table[id='availablePolicies4'] tr input[value='Save']")).sendKeys(""); // save Button
	MyActions.click(driver,driver.findElement(By.cssSelector("table[id='availablePolicies4'] tr input[value='Save']"))); // save Button
	MyActions.click(driver, By.id("popup_ok"));
	MyActions.click(driver, By.id("popup_ok"));
}
private void configureLibraryLevel() {
	MyActions.click(driver, By.cssSelector("input[value*='Library Level']"));
	MyActions.click(driver, By.cssSelector("input[value='inst']"));
	WebElement campusName= driver.findElement(By.id("cmbCampus"));
	Select campName=new Select(campusName);
	campName.selectByVisibleText("DES Pune Institute"); //Campus Name
	String[] InstituteList= dp.getSimpleArray("test\\resources\\data\\registrationData.xls",
			"Library", "InstituteList");
	List <WebElement>labels= driver.findElements(By.cssSelector("[id='instituteTable'] tr td"));
	for(int i=0;i<labels.size();i++) // will select desired Media types
	{
		for (String string : InstituteList) 
		{
			//System.out.println(labels.get(i).getText()+ "-"+string);
			String institute=labels.get(i).getText().trim(); // to remove Space before these media types
			if(institute.equals(string))
			{
			//	System.out.println(item);
				MyActions.click(driver,labels.get(i).findElement(By.cssSelector("input")));
				break;
			}
		}
	}
	MyActions.click(driver, By.cssSelector("#footer input[value='Save']")); // save Button
}
public void configureLibraryMedia()
{	MyActions.click(driver, driver.findElement(By.cssSelector("input[value*='Library Media']")));
	String[] mediaTypes= dp.getSimpleArray("test\\resources\\data\\registrationData.xls",
			"Library", "MediaTypes");
	List <WebElement>labels= driver.findElements(By.cssSelector("table[id='availableProductTypes4']>tbody label"));
	for(int i=0;i<labels.size();i++) // will select desired Media types
	{
		for (String string : mediaTypes) 
		{
			System.out.println(labels.get(i).getText()+ "-"+string);
			String item=labels.get(i).getText().trim(); // to remove Space before these media types
			if(item.equals(string))
			{
				MyActions.click(driver, labels.get(i));
				break;
			}
		}
	}
	driver.findElement(By.cssSelector("#divProductTypes table[id='availableProductTypes4'] input[type='button'][value='Save']")).sendKeys(""); // save Button
	MyActions.click(driver, By.cssSelector("#divProductTypes table[id='availableProductTypes4'] input[type='button'][value='Save']")); // save Button

}

}
