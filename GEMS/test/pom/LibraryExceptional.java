package pom;

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

public class LibraryExceptional {
	private WebDriver driver;
	final String INPUTFILEPATH="E:\\GEMS\\registrationData.xls";
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


public LibraryExceptional(WebDriver driver) {
	PageFactory.initElements(driver, this); // to test after commenting 
	this.driver = driver;
	dp= new DataProviderClass();
}
public void configureRuleBook()
{
	MyActions.click(driver, Catalogue); // enable it in the end
	MyActions.click(driver, RuleBook);
	MyActions.click(driver, ConfigureRB);
	
//	driver.get("http://uat.deccansociety.com/lib_libraryProductRuleConfiguration.htm"); //  direct url for rule config
	
	String [] courseTypes = dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "COURSE");
Select select= new Select(driver.findElement(By.id("cmbCourseType-4")));
	for (String course : courseTypes) 
	{
		System.out.println("Setting General Rules for:"+course+"--------------------------------------------------");
		Log.info("Setting General Rules for:"+course+"--------------------------------------------------");
		select.selectByVisibleText(course);
		setGeneralRule(course);
		System.out.println("General rules Done:"+course+"-------**************************------------");
		Log.info("General rules Done:"+course+"-------------*************************-------------");
		setFineRule(course);
		System.out.println("Fine rules Done:"+course+"-------**************************------------");
		Log.info("Fine rules Done:"+course+"-------------*************************-------------");
		
	}
	//driver.navigate().refresh();
}
private void setFineRule(String course) {
	String [] Student= dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "StudentFine");
	String [] Employee= dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "EmplyeeFine");
	String [] TeachingEmp= dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "TEmpFine");
	String [] NTEmpl=dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "NTEmpFine");
	String [] VEmpl= dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "VEmpFine");
	String [] ExecEmpl= dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "ExecEmplFine");
	
//	libraries=driver.findElements(By.cssSelector("div[id='accordion'] a td")); // to get library name
//	if(libraries.size()==0) 
//	{
//		System.out.println("There are no Libraries added to this account");
//		Log.info("There are no Libraries added to this account");
//	}
//	MyActions.click(driver,libraries.get(0)); // BJ wadia 
//	else{
//		int i=0;
//		String LibName=libraries.get(i).getText();
//		while(!LibName.equalsIgnoreCase("BJ WADIA CENTRAL LIBRARY")) //in  case u want another library
//		{
//			i++;
//			LibName=libraries.get(i).getText();
//		}
//		MyActions.click(driver, libraries.get(i));
//		
//	}
	WebElement tr= driver.findElement(By.cssSelector("[id*=myTabProductTypes]"));
	MyActions.click(driver,tr.findElement(By.xpath("//a[contains(text(),'Fine Rules')]"))); // Click on fine rules tab

	List <WebElement> totalMembers=driver.findElements(By.cssSelector("[id*='accordionFineRules'] tr")); // [id*='myTabContent'][class='tab-content'] tr
	int libMembers=1; // 1=Student, 2= Employee
	for (WebElement webElement : totalMembers) 
	{	
		System.out.println("Webelement:"+webElement.getText());
		Log.info("Entering Fine rule for "+webElement.getText());
		MyActions.click(driver,webElement);		
		//List <WebElement> txtFields=driver.findElements(By.cssSelector("[id*='libProductTypesDiv'][id$='"+libMembers+"'] tr td input[type='text']"));
			if(webElement.getText().equals("STUDENT"))
			{
				String msg=driver.findElement(By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='"+libMembers+"'] tbody>tr>td>b")).getText();
				if(msg.equals("Rules has to be cofigured..!!No Records found..!!"))
				{
					System.out.println("Rules not present, need to add rules");
					MyActions.click(driver, By.cssSelector("#libMemberDiv1 a"));
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(Student[0].toString());

					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(Student[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(Student[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(Student[3].toString());
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
					
				}
				else
				{
					System.out.println("Rules already present, Need to edit");
					MyActions.click(driver, By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='1'] tr td a")); // 1 can be replaced by libMembers
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(Student[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(Student[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(Student[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(Student[3].toString());
					
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
			}
			else if(webElement.getText().equals("EMPLOYEE"))
			{
				String msg=driver.findElement(By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='"+libMembers+"'] tbody>tr>td>b")).getText();
				if(msg.equals("Rules has to be cofigured..!!No Records found..!!"))
				{
					System.out.println("Rules not present, need to add rules");
					MyActions.click(driver, By.cssSelector("#libMemberDiv2 a"));
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(Employee[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(Employee[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(Employee[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(Employee[3].toString());
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
				else
				{
					System.out.println("Rules already present, Need to edit");
					MyActions.click(driver, By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='2'] tr td a")); // 2 can be replaced by libMembers
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(Employee[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(Employee[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(Employee[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(Employee[3].toString());
					
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
			}
			else if(webElement.getText().equals("TEACHING EMPLOYEE"))
			{
				String msg=driver.findElement(By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='"+libMembers+"'] tbody>tr>td>b")).getText();
				if(msg.equals("Rules has to be cofigured..!!No Records found..!!"))
				{
					System.out.println("Rules not present, need to add rules");
					MyActions.click(driver, By.cssSelector("#libMemberDiv3 a"));
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(TeachingEmp[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(TeachingEmp[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(TeachingEmp[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(TeachingEmp[3].toString());
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
				else
				{
					System.out.println("Rules already present, Need to edit");
					MyActions.click(driver, By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='3'] tr td a")); // 2 can be replaced by libMembers
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(TeachingEmp[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(TeachingEmp[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(TeachingEmp[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(TeachingEmp[3].toString());
					
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
			}
			else if(webElement.getText().equals("NON-TEACHING EMPLOYEE"))
			{
				String msg=driver.findElement(By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='"+libMembers+"'] tbody>tr>td>b")).getText();
				if(msg.equals("Rules has to be cofigured..!!No Records found..!!"))
				{
					System.out.println("Rules not present, need to add rules");
					MyActions.click(driver, By.cssSelector("#libMemberDiv4 a"));
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(NTEmpl[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(NTEmpl[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(NTEmpl[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(NTEmpl[3].toString());
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
				else
				{
					System.out.println("Rules already present, Need to edit");
					MyActions.click(driver, By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='4'] tr td a")); // 2 can be replaced by libMembers
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(NTEmpl[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(NTEmpl[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(NTEmpl[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(NTEmpl[3].toString());
					
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
			}
			else if(webElement.getText().equals("VISITING EMPLOYEE"))
			{
				String msg=driver.findElement(By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='"+libMembers+"'] tbody>tr>td>b")).getText();
				if(msg.equals("Rules has to be cofigured..!!No Records found..!!"))
				{
					System.out.println("Rules not present, need to add rules");
					MyActions.click(driver, By.cssSelector("#libMemberDiv5 a"));
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(VEmpl[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(VEmpl[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(VEmpl[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(VEmpl[3].toString());
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
				else
				{
					System.out.println("Rules already present, Need to edit");
					MyActions.click(driver, By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='5'] tr td a")); // 2 can be replaced by libMembers
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(VEmpl[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(VEmpl[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(VEmpl[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(VEmpl[3].toString());
					
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
			}
			else if(webElement.getText().equals("EXECUTIVE EMPLOYEE(MEMBERS)"))
			{
				String msg=driver.findElement(By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='"+libMembers+"'] tbody>tr>td>b")).getText();
				if(msg.equals("Rules has to be cofigured..!!No Records found..!!"))
				{
					System.out.println("Rules not present, need to add rules");
					MyActions.click(driver, By.cssSelector("#libMemberDiv6 a"));
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(ExecEmpl[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(ExecEmpl[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(ExecEmpl[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(ExecEmpl[3].toString());
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
				else
				{
					System.out.println("Rules already present, Need to edit");
					MyActions.click(driver, By.cssSelector("[id*='tbnFineRulesConfiguration'][id$='6'] tr td a")); // 2 can be replaced by libMembers
					Select select= new Select(driver.findElement(By.id("cmbRule")));
					select.selectByVisibleText(ExecEmpl[0].toString());
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).clear(); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).clear();
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtFromDays']")).sendKeys(ExecEmpl[1].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtToDays']")).sendKeys(ExecEmpl[2].toString()); 
					driver.findElement(By.cssSelector("#divConfigureFineRules input[id='txtRuleValue']")).sendKeys(ExecEmpl[3].toString());
					
					MyActions.click(driver, By.cssSelector("#divConfigureFineRules input[type='button']"));
				}
			}
		
//		MyActions.click(driver,By.cssSelector("[id*='libProductTypesDiv'][id$='"+libMembers+"'] tr td input[type='button']"));
//		MyActions.click(driver, By.id("popup_ok"));
		libMembers++;
	}
	
}
private void setGeneralRule(String course) {
	String [][] BookRules;
	String[][] NewsLetterRules;
	String[][] RHB;
	String[][] CDS;
	String[][] DVDS;
	String[][] Journals;
	if(course.equalsIgnoreCase("PG"))  //  change input data according to exceptional cases
	{
	BookRules= dp.getTableArray(INPUTFILEPATH,"LibraryMathsDept", "BooksGR");
	NewsLetterRules= dp.getTableArray(INPUTFILEPATH,"LibraryMathsDept", "NewsLetterGR");
	RHB= dp.getTableArray(INPUTFILEPATH,"LibraryMathsDept", "RHB");
	CDS= dp.getTableArray(INPUTFILEPATH,"LibraryMathsDept", "CDS");
	DVDS= dp.getTableArray(INPUTFILEPATH,"LibraryMathsDept", "DVDS");
	Journals= dp.getTableArray(INPUTFILEPATH,"LibraryMathsDept", "JOURNALS");
	}
	else
	{
		BookRules= dp.getTableArray(INPUTFILEPATH,"Library", "BooksGR");
		NewsLetterRules= dp.getTableArray(INPUTFILEPATH,"Library", "NewsLetterGR");
		RHB= dp.getTableArray(INPUTFILEPATH,"Library", "RHB");
		CDS= dp.getTableArray(INPUTFILEPATH,"Library", "CDS");
		DVDS= dp.getTableArray(INPUTFILEPATH,"Library", "DVDS");
		Journals= dp.getTableArray(INPUTFILEPATH,"Library", "JOURNALS");
			}
	
	libraries=driver.findElements(By.cssSelector("div[id='accordion'] a td")); // to get library name
	if(libraries.size()==0) 
	{
		System.out.println("There are no Libraries added to this account");
		Log.info("There are no Libraries added to this account");
	}
	if(driver.findElement(By.cssSelector("[id*='libProductTypes']")).isDisplayed())
	{
		System.out.println("Lib details are OPEN");
	}else
	{
		System.out.println("Need to open LIbrary Div ");
	MyActions.click(driver,libraries.get(0)); // BJ wadia 
	}
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
			configureLibraryMedia();    
			configureLibraryLevel();   
			configureFinancePolicy();
			configureMemberTypes(); // Configures member types for included departments (Taken from file)
			
		}
	}
}
private void configureMemberTypes() { 
	List <WebElement>totalDeptRows= driver.findElements(By.cssSelector("table[id='ulDepartmentList4'] tr"));
	//MyActions.click(driver, By.cssSelector("input[value*='Finance Policies']"));	
	String[] requiredDepts= dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "Departments");
	
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
				String [][] Data = dp.getTableArray(INPUTFILEPATH,"LibraryMathsDept", "MemberTypeConfig");
				String [] memberNames=dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "Members"); 
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
	String[] requiredPolicies= dp.getSimpleArray(INPUTFILEPATH,"LibraryMathsDept", "FinancePolicies");
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
	String[] InstituteList= dp.getSimpleArray(INPUTFILEPATH,
			"LibraryMathsDept", "InstituteList");
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
	String[] mediaTypes= dp.getSimpleArray(INPUTFILEPATH,
			"LibraryMathsDept", "MediaTypes");
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
