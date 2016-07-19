package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utility.Log;
import utility.MyActions;

public class ProvisionalAdmission extends
		LoadableComponent<ProvisionalAdmission> {
	private WebDriver driver;
	@FindBy(id = "applicantInstCourseCmb")
	WebElement program;
	@FindBy(id = "applicationProcessYearCmb")
	WebElement admissionYear;
	@FindBy(id = "entranceRoundCmb")
	WebElement entranceRound;

	@FindBy(id = "parentDepartmentId")
	WebElement admsnForDept;
	WebElement paymentMode;
	WebElement btnSaveProvisionalAdmssn;
	WebElement lnkBacktoAdmsnList;

	WebElement lstDocsNeeded;
	WebElement blnSponsorship;
	WebElement sponsorCategory;
	WebElement btnProceed;

	WebElement dwnldBankChallan; // extra actions after invoice generation
	WebElement dwnldUndertaking;
	WebElement genReceiptOfInvoice;

	public ProvisionalAdmission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProvisionalAdmission(WebDriver driver) {
		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void setAdmissionYear(String admissionYear) {
		Select admssnYear = new Select(this.admissionYear);
		admssnYear.selectByVisibleText(admissionYear);
	}

	public void setEntranceRound(String entranceRound) {
		Select round = new Select(this.entranceRound);
		round.selectByVisibleText(entranceRound);
	}

	public void setProgram(String programName) {
		Select admissnYearForDept = new Select(this.program);
		admissnYearForDept.selectByVisibleText(programName);
	}

	public void setAmsnForDept(String deptName) {
		Select dept = new Select(admsnForDept);
		dept.selectByVisibleText(deptName);
	}

	@Override
	protected void load() {
		driver.get("http://uat.deccansociety.com/adm_applicantAdmissionDetails.htm");

	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		String breadcumb = driver.findElement(By.cssSelector("h2")).getText();
		Assert.assertEquals(breadcumb, "INSTITUTIONAL » APPLICANT ADMISSIONS");
	}

	public void grantProvisionalAdmissionTo(String studentName,
			String emailofStudent, String deptName, String sponsorship,
			String sponsorName) {

		WebDriverWait smallWait = new WebDriverWait(driver, 10);

		WebElement email = driver
				.findElement(By
						.xpath(".//*[starts-with(@id,'studTr')] / td[4][contains(text(),'"
								+ studentName + "')]"));

		smallWait.until(ExpectedConditions.textToBePresentInElement(email,
				emailofStudent));
		String emailData = email.getText();
		String arrayToGetEmail[] = emailData.split(":|\n");// Email is @ index 3
		// System.out.println(arrayToGetEmail[3]);

		WebElement invoiceStatus = driver
				.findElement(By
						.xpath(".//*[starts-with(@id,'studTr')] / td[contains(text(),'"
								+ studentName
								+ "')]/following-sibling::*[4] /span[1]"));
		WebElement paymentStatus = driver
				.findElement(By
						.xpath(".//*[starts-with(@id,'studTr')] / td[contains(text(),'"
								+ studentName
								+ "')]/following-sibling::*[4] /span[2]"));

		WebElement btnprovisionalAdmision = driver.findElement(By
				.xpath(".//*[starts-with(@id,'studTr')] / td[contains(text(),'"
						+ studentName + "')]/following-sibling::*[6]/input"));
		WebElement btnInvoiceGen = driver.findElement(By
				.xpath(".//*[starts-with(@id,'studTr')] / td[contains(text(),'"
						+ studentName + "')]/following-sibling::*[7]/input"));

		// System.out.println("invoiceStatus is :"+invoiceStatus.getText());
		// System.out.println("Payment Status is :"+paymentStatus.getText());
		// if(paymentStatus.getText().equals("Paid"))
		if (true) {
			MyActions.click(driver, btnprovisionalAdmision);
			Select select = new Select(driver.findElement(By
					.id("parentDepartmentId")));
			String DeptNameAlreadyPresent = select.getFirstSelectedOption()
					.getText();

			if (DeptNameAlreadyPresent.equals(deptName)) {
				System.out.println("Alreasy admitted .. . ..  . .");
				Log.info("This applicant is already admitted ! ");
			} else {
				setAmsnForDept(deptName);
				MyActions.click(driver, driver.findElement(By.id("otherchk"))); // Select
																				// check
																				// box
				driver.findElement(
						By.cssSelector("[class='provisinalAdmissionButtonPanel'] input[value*='Save']"))
						.click(); // click on save button
				driver.findElement(By.id("popup_ok")).click();
			}
			MyActions.click(driver,By.cssSelector("a[title='Back']"));
			MyActions.click(driver, btnInvoiceGen); // Generate invoice
			if(driver.findElements(By.cssSelector("#tdFeeInvoiceNo")).size()>0){
				try{
				smallWait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(".//*[@id='divShowAdmissionConfirmationOption']/div[4]/table[1]/tbody/tr[1]/td[2]")), "3370"));
				}
				catch(Exception e)
				{
					System.out.println("waiting for invoice number...");
				}
				WebElement invoiceNo= driver.findElement(By.cssSelector("#tdFeeInvoiceNo"));
				Log.info("Invoice for this user is already generated ! Invoice number is "+invoiceNo.getText());
				System.out.println(invoiceNo.getText());
			}
			else
			{
			if (sponsorship.equals("Yes")) {
				try {
					smallWait.until(ExpectedConditions.elementToBeClickable(By.id("cmbSponsoringBodyListForInvoice")));
					MyActions.click(driver,By.cssSelector("#divShowSponsorshipChoiceOption div input[value='"+ sponsorship + "']"));
				} catch (Exception e) {
					MyActions.click(driver,By.cssSelector("#divShowSponsorshipChoiceOption div input[value='No']"));
					MyActions.click(driver,By.cssSelector("#divShowSponsorshipChoiceOption div input[value='Yes']"));
					}
				Select sponsorBody = new Select(driver.findElement(By
						.id("cmbSponsoringBodyListForInvoice")));
				System.out.println(sponsorBody.getOptions());
				sponsorBody.selectByVisibleText(sponsorName);
			}

			MyActions.click(driver,By.cssSelector("#divShowSponsorshipChoiceOption div input[value='Proceed']")); // First time clicking proceed
			if (driver.findElements(By.id("popup_message")).size()>0) {
				String msgFromPage = driver.findElement(By.id("popup_message"))
						.getText();
				if (msgFromPage.equalsIgnoreCase("Are you sure to proceed?")) {

					Log.info("Sponsorship set for this student.");
					driver.findElement(By.id("popup_ok")).click();
				} else {
					System.out.println("Error in sponsoring ... . . . .");
					System.out.println("msg is " + msgFromPage);
					Log.error(msgFromPage);
					driver.findElement(By.id("popup_ok")).click();
					driver.findElement(By.cssSelector("#divShowSponsorshipChoiceOption div input[value='No']"))
							.click();
					MyActions.click(driver, By.cssSelector("#divShowSponsorshipChoiceOption div input[value='Proceed']"));
					MyActions.click(driver, By.id("popup_ok"));
					
				}
				Log.info("Invoice Generated successfully !");
			}
		}
			MyActions.click(driver,By.cssSelector("a[title='Back']"));
		}
		// String script= "return arguments[0].innerHTML";
		// String out= (String) ((JavascriptExecutor)
		// driver).executeScript(script, email);
		// System.out.println("out is "+out);
	}
}
