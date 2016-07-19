package pom;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utility.DateFunctions;
import utility.Log;
import utility.MyActions;

public class HomePage {
	private WebDriver driver;
	@FindBy(id = "header-welcome")
	WebElement options;
	@FindBy(linkText = "Logout")
	WebElement Logout;
	@FindBy(linkText = "Institutional")
	WebElement institutional_MenuItem;
	@FindBy(linkText = "All Application Approval")
	WebElement allApplicationApproveLink;
	@FindBy(linkText = "Applicant Merit List")
	WebElement applicantMeritList;
	@FindBy(linkText = "Call for Admission Round")
	WebElement callForAdmissionRound;
	@FindBy(linkText = "Applicant Admissions")
	WebElement applicantAdmissions;
	@FindBy(id = "smsMessage")
	WebElement messageToMeritListStudents;

	@FindBy(id = "applicantInstCourseCmb")
	WebElement program;
	@FindBy(id = "applicationProcessYearCmb")
	WebElement admissionYear;
	@FindBy(id = "entranceRoundCmb")
	WebElement entranceRound;
	@FindBy(id = "entranceNameCmb")
	WebElement entranceName;
	@FindBy(id = "markAllStatusCheckbox")
	WebElement selectAllRadioButton;
	@FindBy(id = "cmbPolicyX")
	WebElement financePolicy;
	@FindBy(id = "fromDate")
	WebElement fromDate;
	@FindBy(id = "toDate")
	WebElement toDate;

	@FindBy(id = "hiddenCourseId")
	WebElement programForMeritList;
	@FindBy(id = "admissionYear")
	WebElement admissionYearForMeritList;
	@FindBy(id = "entranceRoundCmb")
	WebElement RoundForMeritList;
	@FindBy(css = ".ui-datepicker-month")
	WebElement uiMonth;
	@FindBy(css = ".ui-datepicker-year")
	WebElement uiYear;
	@FindBy(xpath = "//span[contains(text(),'Prev')]/parent::a")
	WebElement prev;
	@FindBy(xpath = "//span[contains(text(),'Next')]/parent::a")
	WebElement next;
	@FindBy(id = "search-input")
	WebElement searchInput;
	WebElement uiDate;
	@FindBy (id="getSuccessTransactions") WebElement SuccessfulTransactions;
	WebElement admission_Admin_Role;
	WebElement finance_Admin_Role;
	@FindBy(id = "approveDisApproveStatusGlobal")
	WebElement GlobalApproveStatus;
	private WebElement librarianRole;
	
	public void setProgramForMeritList(String programForMeritList) {
		Select program = new Select(this.programForMeritList);
		program.selectByVisibleText(programForMeritList);
	}

	public void setMessageToMeritListStudents(String message) {
		messageToMeritListStudents.sendKeys(message);
	}

	public void setAdmissionYearForMeritList(String year) {
		Select admissionYear = new Select(admissionYearForMeritList);
		admissionYear.selectByVisibleText(year);
	}

	public void setRoundForMeritList(String roundForMeritList) {
		Select round = new Select(RoundForMeritList);
		round.selectByVisibleText(roundForMeritList);
	}

	public void openOptionsMenu() {
		MyActions.click(driver, options);
	}

	public void setProgram(String programName) {
		Select selectProgram = new Select(program);
		selectProgram.selectByVisibleText(programName);
	}

	public void setAdmissionYear(String year) {
		Select selectadmissionYear = new Select(admissionYear);
		selectadmissionYear.selectByVisibleText(year);
	}

	public void setEntranceRound(String round) {
		Select selectEntranceRound = new Select(entranceRound);
		selectEntranceRound.selectByVisibleText(round);
	}

	public void setEntranceName(String entrance) {
		Select selectEntranceName = new Select(entranceName);
		selectEntranceName.selectByVisibleText(entrance);
	}

	public void setFinancePolicy(String policyName) {
		Select select = new Select(financePolicy);
		select.selectByVisibleText(policyName);
	}

	public void setFromDate(String fromDate) {
		MyActions.click(driver, By.id("fromDate"));
		DateFunctions df = new DateFunctions(fromDate); // Datefunctions will
														// give date, month,
														// year for that date
		// System.out.println("ui year is : "+Integer.parseInt(uiYear.getText()));

		if (df.getYyyy() < Integer.parseInt(uiYear.getText())
				|| df.getMonthNumber(uiMonth.getText()) > df.getMm()) {
			// Input year + Month is less than Current Year + Month
			while (df.getYyyy() < Integer.parseInt(uiYear.getText())
					|| df.getMonthNumber(uiMonth.getText()) > df.getMm()) {
				MyActions.click(driver, prev);
			}
			;
		} else {
			// Input year is greater than Current year
			while (df.getYyyy() > Integer.parseInt(uiYear.getText())
					|| df.getMonthNumber(uiMonth.getText()) < df.getMm()) {
				MyActions.click(driver, next);
			}
			;
		}
		uiDate = driver.findElement(By.xpath("//td/a[contains(text(),'"
				+ df.getDd() + "')]"));
		MyActions.click(driver, uiDate);
	}

	public void acceptFeesFromStudentsFromFile() {
		// file handling code for excel
		// get list of transaction reference and then accept fees
		String listOfReferences[] = getListOfTransactionReference(
				"test\\resources\\data\\registrationData.xls",
				"acceptFeesForAdmission", "PENDING");
		ArrayList<WebElement> allAcceptButtons = new ArrayList<WebElement>();
		System.out.println("list references= " + listOfReferences.toString());

		for (int i = 0; i < listOfReferences.length; i++) {
			System.out.println("Found array [" + i + "] ="
					+ listOfReferences[i]);
			try {
				allAcceptButtons.add(driver.findElement(By
						.cssSelector("input[onclick*=" + listOfReferences[i]
								+ "][value='Accept']")));
			}

			catch (Exception e) {
Log.warn("Reference number= "+listOfReferences[i]+" not found on page !!");
Log.info("in function acceptFeesFromStudentsFromFile()");
			}
		}
		for (WebElement acceptButton : allAcceptButtons) {
			MyActions.click(driver, acceptButton);
			MyActions.click(driver, By.id("popup_ok"));
			//Log.info("Reference Number "+acceptButton.getAttribute("onclick")+" is accepted successfully ...");
		}

	}

	public void setToDate(String toDate) {
		MyActions.click(driver, By.id("toDate"));
		DateFunctions df = new DateFunctions(toDate); // Datefunctions will give
														// date, month, year for
														// that date
		// System.out.println("ui year is : "+Integer.parseInt(uiYear.getText()));

		if (df.getYyyy() < Integer.parseInt(uiYear.getText())
				|| df.getMonthNumber(uiMonth.getText()) > df.getMm()) {
			// Input year + Month is less than Current Year + Month
			while (df.getYyyy() < Integer.parseInt(uiYear.getText())
					|| df.getMonthNumber(uiMonth.getText()) > df.getMm()) {
				MyActions.click(driver, prev);
			}
			;
		} else {
			// Input year is greater than Current year
			while (df.getYyyy() > Integer.parseInt(uiYear.getText())
					|| df.getMonthNumber(uiMonth.getText()) < df.getMm()) {
				MyActions.click(driver, next);
			}
			;
		}
		uiDate = driver.findElement(By.xpath("//td/a[contains(text(),'"
				+ df.getDd() + "')]"));
		MyActions.click(driver, uiDate);
	}

	public void getURL(String url) {
		driver.get(url);
	}

	public void changeRoleToAdmissionAdmin() {
		openOptionsMenu();
		admission_Admin_Role = driver.findElement(By
				.linkText("Admission Admin"));
		MyActions.click(driver, admission_Admin_Role);
	}

	public void changeRoleToFinanceAdmin() {
		openOptionsMenu();
		finance_Admin_Role = driver.findElement(By.linkText("Finance Admin"));
		MyActions.click(driver, finance_Admin_Role);
	}
	public void changeRoleToLibrarian()
	{
		openOptionsMenu();
		librarianRole= driver.findElement(By.linkText("Library Admin"));
		MyActions.click(driver,librarianRole);
	}

	public void approveAll(String programName, String year, String round,
			String entrance) {
		MyActions.click(driver, institutional_MenuItem);
		MyActions.click(driver, allApplicationApproveLink); // http://uat.deccansociety.com/adm_applicantApplicationProcessPending.htm
		setProgram(programName);
		setAdmissionYear(year);
		setEntranceRound(round);
		setEntranceName(entrance);
		MyActions.click(driver, selectAllRadioButton); // radio button
		Select updateAllApplicantStatus = new Select(GlobalApproveStatus);
		updateAllApplicantStatus.selectByVisibleText("Approve");
	}

	public HomePage(WebDriver driver) {
		super();
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void generateMeritList(String program, String admissionYear,
			String round, String message) {
		MyActions.click(driver, institutional_MenuItem);
		MyActions.click(driver, applicantMeritList);
		setProgramForMeritList(program);
		setAdmissionYearForMeritList(admissionYear);
		setRoundForMeritList(round);
		MyActions.click(driver, By.id("btnGenerateMeritNo")); // Generate Merit
																// list (Approve
																// & Discrepancy
																// button)
		MyActions.click(driver, By.id("provisionalMeritList")); // Provisional
																// merit list
																// Radio button
		MyActions.click(driver, By.id("btnSubmitStatusWiseMeritList"));// Submit
																		// button
																		// in
																		// front
																		// of
																		// this
																		// radio
																		// button
		MyActions.click(driver, By.id("chkStudList")); // Select all Students
		MyActions.click(driver, By.id("btnSubmitStatusWiseMeritListMessage")); // Send
																				// message

		setMessageToMeritListStudents(message);
		MyActions.click(driver, By.id("btnMessageProceed"));

	}

	public void updateStatus() {
		MyActions.click(driver, By.cssSelector(".btn.btn-mini.btn-info"));
	}
public void getSuccessfulTransactions()
{
	setToDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
	MyActions.click(driver, SuccessfulTransactions);
}
	public void getPendingTransactions() {
		WebElement Pending = driver
				.findElement(By
						.cssSelector("[id='divTransactionData'] a[onclick*='Pending']"));
		MyActions.click(driver, Pending);
	}

	public String[] getListOfTransactionReference(String xlFilePath,
			String sheetName, String tableName) {
		String[] tabArray = null;
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
			tabArray = new String[endRow - startRow - 1];
			ci = 0;

			for (int i = startRow + 1; i < endRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < endCol; j++, cj++) {
					tabArray[ci] = sheet.getCell(j, i).getContents();
				}
			}
		} catch (Exception e) {
			System.out.println("error in getting TransactionReference()");

		}

		return (tabArray);
	}

	public void settleSuccessfulTransactions() {
		String listOfReferences[] = getListOfTransactionReference("test\\resources\\data\\registrationData.xls","acceptFeesForAdmission", "PENDING");
		System.out.println("- - - - Need to Settle following references - - - -  ");
		for (int i = 0; i < listOfReferences.length; i++) {
		System.out.println("Reference Number " + i + " = "+ listOfReferences[i]);	
			//td [contains(text(),'V5')] /parent ::tr /td[2]        // checkbox for entries
			//td [contains(text(),'V5')] /parent ::tr //input [1] 	// checkbox
			//td [contains(text(),'V5')] /parent ::tr //td[9]/ input // Settle Button
		}
		for (int indexOfTransactionReference = 0; indexOfTransactionReference<listOfReferences.length;indexOfTransactionReference++)
		{
			//int attempts=0;
			Log.info("******* searching for "+ listOfReferences[indexOfTransactionReference]+ " ******");
			boolean notSettled=false;
		  try {		String script= "return arguments[0].innerHTML";
	            	String isSettled="notSettled";
	            	WebElement settleMessage=driver.findElement(By.xpath("//td [contains(text(),'"+listOfReferences[indexOfTransactionReference]+"')] /parent ::tr //td[9]"));
	            	isSettled=(String) ((JavascriptExecutor) driver).executeScript(script, settleMessage);
	            //	System.out.println("text is : "+isSettled);
	            	
	            	//WebElement so= driver.findElement(By.xpath("//td [contains(text(),'SO')] /parent ::tr //td[9]"));
	            	//WebElement bjy= driver.findElement(By.xpath("//td [contains(text(),'BJ')] /parent ::tr //td[9]"));
	            	//String outcome = (String) ((JavascriptExecutor) driver).executeScript(script, so);
	            	//System.out.println("so = "+outcome);
	            	// outcome = (String) ((JavascriptExecutor) driver).executeScript(script, bjy);
	            	// System.out.println("BJy = "+outcome);
	            	
	            	if(isSettled.equals("Settled"))
	            	{	//notSettled=false;
	            		Log.info("Transaction with reference - "+listOfReferences[indexOfTransactionReference]+" is already settled");
	            	}
	            	else{
	            		MyActions.click(driver, SuccessfulTransactions);
	            	Log.info("Settling the reference entry - "+listOfReferences[indexOfTransactionReference]);
	            	MyActions.click(driver, driver.findElement(By.xpath("//td [contains(text(),'"+listOfReferences[indexOfTransactionReference]+"')] /parent ::tr // input [1]")));
	        		MyActions.click(driver, driver.findElement(By.xpath("//td [contains(text(),'"+listOfReferences[indexOfTransactionReference]+"')] /parent ::tr //td[9]/ input")));
	        		
	        		notSettled=true;
	            	}
	             } catch(StaleElementReferenceException e) {
	            	 Log.error("Stale error exception occurred for - this element"); 	
	            	 MyActions.click(driver, driver.findElement(By.xpath("//td [contains(text(),'"+listOfReferences[indexOfTransactionReference]+"')] /parent ::tr // input [1]")));
		        	MyActions.click(driver, driver.findElement(By.xpath("//td [contains(text(),'"+listOfReferences[indexOfTransactionReference]+"')] /parent ::tr //td[9]/ input")));
		        	 Log.info("Again trying to click the same element . . .");	
		        	 notSettled=true;
	            }
	            catch (NoSuchElementException e) {
					Log.error("No such element found on page -> Invalid Transaction reference = :"+listOfReferences[indexOfTransactionReference]);
					Log.info("In function settleSuccessfulTransactions()");
				}
	            
	     //   }
			if(notSettled)
			{
		DateFunctions df= new DateFunctions(new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString());
		MyActions.click(driver, By.id("settleDate"));
		uiDate = driver.findElement(By.xpath("//td/a[contains(text(),'"
				+ df.getDd() + "')]"));
		MyActions.click(driver, uiDate);
		MyActions.click(driver, By.cssSelector("div [id='onlinePaymentTransferBody'] tr:nth-child(2) td input"));
		Log.info("Clicked to save this transaction...Please check if it is saved");
		
			}
			
		//System.out.println(entry.getKey() + "/" + entry.getValue());
		//Log.info("Reference Number "+acceptButton.getAttribute("onclick")+" is accepted successfully ...");
		}

		
	}
	
	public ProvisionalAdmission gotoProvisionalAdmissionPage(WebDriver driver)
	{
		ProvisionalAdmission proAdmssn= new ProvisionalAdmission(driver);
		return proAdmssn;
		
	}
	
}
