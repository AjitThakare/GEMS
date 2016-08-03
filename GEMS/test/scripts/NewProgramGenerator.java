package scripts;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pom.HomePage;
import pom.LoginPage;
import pom.OrgAdminJobs;
import utility.DataProviderClass;
import utility.Log;
import utility.MyActions;

public class NewProgramGenerator {
	private WebDriver driver;
	DataProviderClass dp;
	String [] inputForNewQualification= new String[6];
	  String [] inputForDeptProgMapping= new String[14];
	  String [] inputForAssignRoleForPrg= new String[8];
	  String [] inputForPatternSetting= new String[3];
	  String [] inputForBaseBatch= new String[6];	
  @Test(enabled=false)
  public void createNewProgram() {
	  LoginPage lp= new LoginPage(driver);
	  lp.get();
	  lp.setUsername("orgadmin_des@gems.com");
	  lp.setPassword("123456");
	  HomePage hm= lp.submit();
	  OrgAdminJobs jobs= new OrgAdminJobs(driver);
	  getDataFromFile();
	  
//	  String [] inputForNewQualification=dp.getSimpleArray("test\\resources\\data\\registrationData.xls", "NewQualification",
//				"QUALIFICATION");
//	  String [] inputForDeptProgMapping=dp.getSimpleArray("test\\resources\\data\\registrationData.xls", "NewQualification",
//		"DEPTPROGMAPPING");
	String university=inputForNewQualification[0];
	String stream=inputForNewQualification[1];
	String degreeName=inputForNewQualification[2];
	String degreeType=inputForNewQualification[3];
	String duration=inputForNewQualification[4];
	String NumberOfSemOrYear=inputForNewQualification[5];
 	jobs.addNewQualification(university, stream, degreeName, degreeType, duration, NumberOfSemOrYear); 
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
	hm.logout();
  }
  
  private void getDataFromFile() {
dp= new DataProviderClass();
	  
	  String [][] Data2D=dp.getTableArray("test\\resources\\data\\registrationData.xls", "NewQualificationRow","POINTER");
	  
	  for(int i=0;i<Data2D[0].length;i++)
	  {
		  if(i<=5)
		  {
			  inputForNewQualification[i]=Data2D[0][i]; // 
		  }
		  else if(i<=19){
			  inputForDeptProgMapping[i-6]=Data2D[0][i]; // 6 is no. of inputs for previous array
		  }
		  else if(i<=27)
		  {
			  inputForAssignRoleForPrg[i-20]=Data2D[0][i]; //First 20 inputs belong to other arrays 
		  }
		  else if(i<=30)
		  {
			  inputForPatternSetting[i-28]=Data2D[0][i]; // 3 inputs for pattern setting
		  }
		  else if(i<=36)
		  {
			  inputForBaseBatch[i-31]=Data2D[0][i];
		  }
	  }
	
}

@Test
  public void tasks()
  {
	  
	 // DataProviderClass dp= new DataProviderClass();
	//  String[] data=  dp.getSimpleArray("test\\resources\\data\\registrationData.xls", "NewQualification","AssignRole");
	 getDataFromFile();
	String username=inputForAssignRoleForPrg[0];
	String password=inputForAssignRoleForPrg[1];
	String level=inputForAssignRoleForPrg[2];
	String instituteName=inputForAssignRoleForPrg[3];
	String designation=inputForAssignRoleForPrg[4];
	String role=inputForAssignRoleForPrg[5];
	String programeName=inputForAssignRoleForPrg[6];
	String empID=inputForAssignRoleForPrg[7];
	
//	assignRoleForProg(username, password, level, instituteName, designation, role, programeName,empID);
	
	LoginPage lp= new LoginPage(driver);
	  lp.get();
	  lp.setUsername(username);
	  lp.setPassword(password);
	  HomePage hm= lp.submit();
	  hm.changeRoleToHeadAdmin();
	 ///----------------------------------------------------------------------delete it after this functn 
	
	String progName="";
	if(!instituteName.contains("Grant"))
	{
		progName= progName+"FC Self Finance - ";
	}
	else{
		progName= progName+"FC (Grant in Aid) - ";
	}
	if(inputForNewQualification[1].contains("Arts"))
	{
		progName= progName+"Arts - ";
	}
	else {
		progName= progName+inputForNewQualification[1]+" - ";
	}
	progName= progName+programeName;
	System.out.println("Prog name made is:"+progName);
	Log.info("Prog name made is:"+progName);
	setSyllabusPattern(progName);
	addBaseBatch(inputForBaseBatch[0],inputForBaseBatch[1],inputForBaseBatch[2],inputForBaseBatch[3],inputForBaseBatch[4],inputForBaseBatch[5]);
  }
  private void addBaseBatch(String batchStartYr,String strength, String remark, String duration, String noOfSem, String autoGenRoll ) {
	  getDataFromFile();
	driver.get("http://uat.deccansociety.com/roleWiseCourse.htm");
//	MyActions.click(driver, By.linkText("Organization"));
//MyActions.click(driver, By.linkText("Department Configuration"));
//	MyActions.click(driver, By.linkText("Program Configuration"));
	String programeName= inputForAssignRoleForPrg[6];
MyActions.click(driver, By.xpath(".//* //td[contains(text(),'"+programeName+"')]"));	
MyActions.click(driver, By.cssSelector("#ownCourse tr tr td div	"));	// Program Intake
MyActions.click(driver, By.cssSelector("#courseTable_toppager_left table tr td"));	 // add base batch
List<WebElement> totalBasebathes=driver.findElements(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr")); // gets total TRs from course table
Actions act= new Actions(driver);
act.moveToElement(driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"]")));
act.doubleClick().build().perform();
WebElement currentInput= driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"] //td[2] //select")); // pattern 
Select select= new Select(currentInput);
select.selectByVisibleText(inputForPatternSetting[0]);


currentInput= driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"] //td[3] //select")); 
select= new Select(currentInput);
select.selectByVisibleText(batchStartYr);


currentInput= driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"] //td[5] //input")); 
currentInput.sendKeys(strength);
currentInput= driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"] //td[6] //input")); 
currentInput.sendKeys(remark);

currentInput= driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"] //td[10] //input")); 
currentInput.sendKeys(duration);
currentInput= driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"] //td[11] //input")); 
currentInput.sendKeys(noOfSem);

currentInput= driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"] //td[12] /input"));
if(autoGenRoll.equalsIgnoreCase("yes"))
{
	MyActions.click(driver, currentInput);
}
else{
	System.out.println("Auto gen roll is false...");
	Log.info("Auto generate roll is false...");
}
currentInput= driver.findElement(By.xpath(".//*[@id='gbox_courseTable'] //table[@id='courseTable'] //tr["+totalBasebathes.size()+"] //td[11] //input")); 
currentInput.sendKeys(Keys.RETURN);

}

private void setSyllabusPattern(String programName) {
	//driver.get("http://uat.deccansociety.com/ihe_SyllabusSubject.htm");
	MyActions.click(driver, By.linkText("Organization"));
	MyActions.click(driver, By.linkText("Department Configuration"));
	MyActions.click(driver, By.linkText("Syllabus Details"));
	Select select= new Select(driver.findElement(By.id("CourseComboSyllabus")));
	select.selectByVisibleText(programName); // Selects program for which we are setting the pattern
	
	select= new Select(driver.findElement(By.id("PatternYear")));
	String pattern= inputForPatternSetting[0];
	String fromYear=inputForPatternSetting[1];
		String resultType=inputForPatternSetting[2];
		boolean patternAlreadyPresent=false;	
	List<WebElement> lst=select.getOptions();
	for (WebElement webElement : lst) {
		if(webElement.getText().matches(pattern))
		{
			System.out.println("Pattern already present, so choosing it directly");
			Log.info("Pattern already present, so choosing it directly");
			select.selectByVisibleText(pattern);
			patternAlreadyPresent=true;
			break;
		}
	}
	if(!patternAlreadyPresent) //  pattern is not present
	{
		MyActions.click(driver, By.id("add"));
		select = new Select(driver.findElement(By.id("patternYearAdd")));
		select.selectByVisibleText(pattern);		
		select = new Select(driver.findElement(By.id("fromYearPattern")));
		select.selectByVisibleText(fromYear);		
		select = new Select(driver.findElement(By.id("resultType")));
		select.selectByVisibleText(resultType);	
		MyActions.click(driver, By.id("savePattern"));
		
	}
	
}

public void assignRoleForProg(String username,String password,String level, String instituteName, String designation, String role, String programeName, String empID)
  {
	  // if logged out, then log in first
	  LoginPage lp= new LoginPage(driver);
	  lp.get();
	  lp.setUsername(username);
	  lp.setPassword(password);
	  HomePage hm= lp.submit();
	  hm.changeRoleToHeadAdmin();
	  hm.gotoPostAndROleConfig();
	  WebElement InstituteLevel= driver.findElement(By.id("rdoInstituteLevel"));
	  WebElement courseLevel= driver.findElement(By.id("rdoCourseLevel"));
	  if(level.equalsIgnoreCase("Institute"))
	  {
		  MyActions.click(driver, InstituteLevel);
	  }
	  else if(level.equalsIgnoreCase("Course"))
	  {
		  MyActions.click(driver,courseLevel);
	  }
	 // MyActions.click(driver, By.cssSelector("input[value='Reset'][onclick=\"resetAllTab('postGrade')\"]"));
	  MyActions.click(driver, By.linkText("Role Mapping"));
	  
	  
	  Select select= new Select(driver.findElement(By.id("cmbInstituteLevelForRole")));
	  select.selectByVisibleText(instituteName);
	  WebElement selectAll= driver.findElement(By.xpath("//table[@id='tblPostTypeList1'] //td[contains(text(),'Select All')] /input"));
	  
	  MyActions.click(driver, selectAll);
	  driver.findElement(By.cssSelector("#tableEmpFilter_filter input")).sendKeys(designation);
	  WebElement correctTR= driver.findElement(By.xpath(".//*[@id='tableEmpFilter']//tr //td [contains(text(),'"+empID+"')] / parent :: tr"));
	  MyActions.click(driver, correctTR.findElement(By.cssSelector("input[value='Assign']")));
	  
	  WebElement correctRoleTD= driver.findElement(By.xpath(" .//*[@id='rolesList'] //td [contains(text(),'"+role+"')]"));
	 MyActions.click(driver, correctRoleTD.findElement(By.cssSelector("input")));
	 
	 MyActions.click(driver, By.id("submitRoleBtnId"));
	 MyActions.click(driver, By.id("popup_ok"));
	 if(driver.findElements(By.xpath(".//*[@id='tbodyRole-Setup-Option']/tr//td[contains(text(),'"+programeName+"')] /parent::tr // input")).size()!=0)
	 {
		 MyActions.click(driver, By.xpath(".//*[@id='tbodyRole-Setup-Option']/tr//td[contains(text(),'"+programeName+"')] /parent::tr // input"));
		 MyActions.click(driver, By.id("btnSaveSpecialRole"));
		 System.out.println("Role is assigned successfully...");
		 Log.info("Role is assigned successfully...");
	 }
	 else{
		 MyActions.click(driver, By.xpath(".//*[@id='divEmployee-roleFooter']/input[@value='Close']"));
		 System.out.println("Already assigned this role...");
		 Log.info("Already assigned this role...");
	 }
	
	//  hm.logout();  
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
