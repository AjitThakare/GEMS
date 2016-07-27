package pom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utility.DataProviderClass;
import utility.DateFunctions;
import utility.Log;
import utility.MyActions;

public class OrgAdminJobs {
	private WebDriver driver;
	@FindBy(partialLinkText = "Library Liaison")
	WebElement libraryLiason;
	List<WebElement> liblvl = new ArrayList<WebElement>();
	WebElement requiredLevel;
	@FindBy(css = ".ui-datepicker-month")
	WebElement uiMonth;
	@FindBy(css = ".ui-datepicker-year")
	WebElement uiYear;
	WebElement uiDate;
	@FindBy(xpath = "//span[contains(text(),'Prev')]/parent::a")
	WebElement prev;
	@FindBy(xpath = "//span[contains(text(),'Next')]/parent::a")
	WebElement next;
	
	public void createNewLibrary() {
		DataProviderClass dp = new DataProviderClass();
		MyActions.click(driver, libraryLiason);
		
		if(driver.findElements(By.xpath("// a //td[contains(text(),'New')]/ancestor::a ")).size()>0)
		{
			System.out.println("New Library Div already present . . .");
			Log.info("New Library Div already present . . .");
		}
		else
		{System.out.println("New Library Div not present, creating . . . ");
		Log.info("New Library Div not present, creating . . . ");
			MyActions.click(driver, By.id("btnCreateNewLibrary")); // new library DIv will be generated
		}
		WebElement newTR= driver.findElement(By.xpath("// a //td[contains(text(),'New')]/ancestor::a "));
		String latestLibId= newTR.getAttribute("onclick");
		latestLibId=latestLibId.replace("fetchExistingInformationOfLibrary(","");
		latestLibId=latestLibId.replace(")",""); // to get lib id, removed some part of onclick attribute value
		
		
		
		MyActions.click(driver, By.xpath("// a //td[contains(text(),'New')]"));
		MyActions.click(driver, By.id("edit"+latestLibId)); // get latest Library ID
		
		String[][] singleInput = dp.getTableArray(
				"test\\resources\\data\\registrationData.xls", "Library",
				"NewLib");
		String libraryLevel = singleInput[0][0].toString();
		String libraryName = singleInput[0][1].toString();
		String abbr = singleInput[0][2].toString();
		String libIncharge = singleInput[0][3].toString();
		String contact1 = singleInput[0][4].toString();
		String contact2 = singleInput[0][5].toString();
		String address = singleInput[0][6].toString();
		String pincode = singleInput[0][7].toString();
		String trustName=singleInput[0][8].toString();
		String campus= singleInput[0][9].toString();
		String instituteName=singleInput[0][10].toString();
		String deptName=singleInput[0][11].toString();
		String isCentral=singleInput[0][12].toString();
		liblvl = driver.findElements(By.cssSelector("input[type='radio']"));
		if (libraryLevel.equals("Public")) {
			requiredLevel = liblvl.get(0);
		} else if (libraryLevel.equals("Trust")) {
			requiredLevel = liblvl.get(1);
		} else if (libraryLevel.equals("Campus")) {
			requiredLevel = liblvl.get(2);

		} else if (libraryLevel.equals("Institute")) {
			requiredLevel = liblvl.get(3);

		} else if (libraryLevel.equals("Department")) {
			requiredLevel = liblvl.get(4);
		}
		
		MyActions.click(driver, requiredLevel);
		MyActions.click(driver, By.cssSelector("input[value='Set Level']"));
		MyActions.click(driver, By.id("popup_ok"));
		Select select= new Select(driver.findElement(By.cssSelector("[id*='comboTrustLevel']")));
		select.selectByVisibleText(trustName);
		select= new Select(driver.findElement(By.cssSelector("[id*='comboCampusLevel']")));
		select.selectByVisibleText(campus);
		select= new Select(driver.findElement(By.cssSelector("[id*='comboInstituteLevel']")));
		select.selectByVisibleText(instituteName);
		
		select= new Select(driver.findElement(By.cssSelector("[id*='comboDepartmentLevel']")));
		select.selectByVisibleText(deptName);
		
		MyActions.click(driver, By.cssSelector("[id*='btnSaveDetailSpecification']"));

		String msg= driver.findElement(By.id("popup_message")).getText();
		if(msg.contains("is already configured"))
		{
			System.out.println("This department is already configured.");
			Log.info("This department is already configured.");
		}
		else
		{	
		MyActions.click(driver, By.id("popup_ok"));
		driver.findElement(By.id("txtLibraryName")).sendKeys(libraryName);
		driver.findElement(By.id("txtAbbreviation")).sendKeys(abbr);
		driver.findElement(By.id("txtLibraryInchargeName")).sendKeys(libIncharge);
		driver.findElement(By.id("txtContactNo1")).sendKeys(contact1);
		driver.findElement(By.id("txtContactNo2")).sendKeys(contact2);
		driver.findElement(By.id("libAddress")).sendKeys(address);
		driver.findElement(By.id("txtlibPincode")).sendKeys(pincode);
		if(isCentral.equals("Yes"))
		{
			MyActions.click(driver, By.id("chkCentral"));
		}
		
		MyActions.click(driver, By.id("btnSavelibConfiguration"));
		
		}
		
		
	}
	public void addNewQualification( String university, String stream,String degreeName, String degreeType,
								String duration,String NumberOfSemOrYear)
	{
		MyActions.click(driver, By.linkText("Qualification"));
		Select select= new Select(driver.findElement(By.id("universityComboStreams")));
		select.selectByVisibleText(university);		// Select university and Stream
		select= new Select(driver.findElement(By.id("cmbUniStream")));
		select.selectByVisibleText(stream);
		// Now check if qualification already exists or not.
		List<WebElement> all_qualificationsTR= driver.findElements(By.cssSelector(" #displayQualificationDetails tbody tr"));
		WebElement degree;
		WebElement dType;
		WebElement dDuration;
		WebElement NoOfSem;
		boolean duplicateQualification= false;
		for (WebElement webElement : all_qualificationsTR) {
			degree=webElement.findElement(By.className("degreeName"));
			dType=webElement.findElement(By.className("degreeType"));
			dDuration=webElement.findElement(By.className("degreeDuration"));
			NoOfSem=webElement.findElement(By.className("noOfSem"));
			if(degree.getText().equalsIgnoreCase(degreeName))
			{
				System.out.println("Degree Name matched, Need to check Degree Type.");
				if(dType.getText().equalsIgnoreCase(degreeType))
				{
					System.out.println("Degree Type also matched ....");
					if(dDuration.getText().equalsIgnoreCase(duration))
					{
						System.out.println("Duration also matched . . .");
						if(NoOfSem.getText().equalsIgnoreCase(NumberOfSemOrYear))
						{
							System.out.println("Number of semester/Year matched...");
							duplicateQualification= true;
							break;
						}
						else
							System.out.println("No. of semester/Year is different.");
					}
					else
					System.out.println("Duration is different.");
				}
				else 
				System.out.println("Degree Type is different.");
			}
			
		}
		
		if(duplicateQualification)
		{
			System.out.println("===========================DUPLICATE QUALIFICATION FOUND=========================");
			Log.info("===========================DUPLICATE QUALIFICATION FOUND=========================");
		}
		else //Add new Qualification
		{
			MyActions.click(driver, By.id("addQualificationDetail"));
			driver.findElement(By.id("degreeName")).sendKeys(degreeName);
			driver.findElement(By.id("degreeType")).sendKeys(degreeType);
			driver.findElement(By.id("degreeDuration")).sendKeys(duration);
			driver.findElement(By.id("noOfSem")).sendKeys(NumberOfSemOrYear);
			MyActions.click(driver, By.cssSelector("#bodyAddQualificationDetails + div input[value='Save']"));
			MyActions.click(driver, By.id("popup_ok"));
			System.out.println("Qualification added successfully!");
			Log.info("Qualification added successfully!");			
		}
		
		MyActions.click(driver, By.linkText("Go to Configuration"));
	}

public void deptProgMapping(String society, String campus,String institute,String departmentName, String universityName,
		String streamName, String programmeName,String abbreviation, String	establishmentDate,String programmeType,
		String universityAssociation,String duration,String noOfClass, String departmentalPromotion)
{
	MyActions.click(driver, By.linkText("Dept-Programme-Mapping")); // Dept Prog mapping link 	
	Select select= new Select(driver.findElement(By.id("trustCombo")));
	select.selectByVisibleText(society);	
	select= new Select(driver.findElement(By.id("campusCombo")));
	select.selectByVisibleText(campus);
	select= new Select(driver.findElement(By.id("instituteCombo")));
	select.selectByVisibleText(institute);
	
	MyActions.click(driver, By.linkText("Programme")); //  go to programme tab
	MyActions.click(driver, By.cssSelector("#programmingDepartment table tr td td div")); // Add programme Button
	List <WebElement> programList=driver.findElements(By.cssSelector("#rowed6 tr"));
	int indexForNewProg= programList.size()-1;
	System.out.println("New Prog Index is :"+indexForNewProg);
	Actions act = new Actions(driver);
	act.moveToElement(programList.get(programList.size()-1)).build().perform();
	WebElement LastTR= programList.get(programList.size()-1);
	WebElement item= LastTR.findElement(By.cssSelector("td"));
	act.doubleClick(item).build().perform();
	
	select= new Select(LastTR.findElement(By.cssSelector("td:nth-child(2) select")));
	select.selectByVisibleText(departmentName);
	select= new Select(LastTR.findElement(By.cssSelector("td:nth-child(3) select")));
	select.selectByVisibleText(universityName);
	select= new Select(LastTR.findElement(By.cssSelector("td:nth-child(4) select")));
	select.selectByVisibleText(streamName);
	select= new Select(LastTR.findElement(By.cssSelector("td:nth-child(5) select")));
	select.selectByVisibleText(programmeName);
	LastTR.findElement(By.cssSelector("td:nth-child(6) input")).sendKeys(abbreviation);
	MyActions.click(driver, LastTR.findElement(By.cssSelector("td:nth-child(7)"))); // click on UI Date input
	
	//=========
		
	DateFunctions df = new DateFunctions(establishmentDate); // Datefunctions will give date, month, year for													// that date
		select = new Select(uiYear);
	select.selectByVisibleText(""+df.getYyyy());
	select = new Select(uiMonth);
	select.selectByIndex(df.getMm()-1);
	uiDate = driver.findElement(By.xpath("//td/a[contains(text(),'"+ df.getDd() + "')]"));
	MyActions.click(driver, uiDate);
	
	select= new Select(LastTR.findElement(By.cssSelector("td:nth-child(8) select")));
	select.selectByVisibleText(programmeType);
	select= new Select(LastTR.findElement(By.cssSelector("td:nth-child(9) select")));
	select.selectByVisibleText(universityAssociation);
	
	LastTR.findElement(By.cssSelector("td:nth-child(10) input")).sendKeys(duration);
	LastTR.findElement(By.cssSelector("td:nth-child(11) input")).sendKeys(noOfClass);
	if(departmentalPromotion.equalsIgnoreCase("Yes"))
			{
		MyActions.click(driver, LastTR.findElement(By.cssSelector("td:nth-child(14) input")));
			}
	LastTR.findElement(By.cssSelector("td:nth-child(11) input")).sendKeys(Keys.RETURN);
	
	MyActions.click(driver, By.linkText("Go to Configuration"));
}
		
	//	==== code to handle date, above
	
	
	
	

	public OrgAdminJobs(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver = driver;

	}

}
