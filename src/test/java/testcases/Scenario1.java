package testcases;

import utilities.TestcaseBase;
import utilities.WebDriverUtility;

import java.sql.Timestamp;
import java.util.Random;
import utilities.PageUtilities;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//can run from here run as test-ng

import webelements.WebElements;
/**
* @author Author: Alex Podimov
*
*/

public class Scenario1 extends PageUtilities {

	
	private WebDriverUtility driver;
	
	@DataProvider(name = "test-data")
  	public Object[][] dataProviderMethod(){
    	return new Object[][]{
          	{"something"},{"anything"}
    	};
  	}

	@BeforeMethod
	public void testcaseInit() {

		// I have used before class and not before test annotation as it's only one test here and one class as well
		// the following will initialize the Chrome browser
		initBrowser();
		
	}
	
	@Test
	public void test01() { 
		
		driver = new WebDriverUtility(TestcaseBase.getDriver());
		
		Random rndNumber = new Random();
		
		int randLowValue = 1;
		int randHighValue = driver.returnElements(WebElements.totalNumberOfProducts).size();
		int rndValueForElementToClick = rndNumber.nextInt(randHighValue - randLowValue) + randLowValue;
		
		driver.returnElement(WebElements.dynamicProductOnTheFrontPage(String.valueOf(rndValueForElementToClick))).click();

		String productPrice = driver.returnElement(WebElements.selectedProductPrice).getText();
		
		driver.returnElement(WebElements.addToCartBtn).click();   
		driver.waitForElemetVisability(webelements.WebElements.popUpCart);
		
		//check the prices in cart and before it are equal
		Assert.assertEquals(productPrice, driver.returnElement(WebElements.totalProductsPrice).getText());
	}
	
	@Test(dataProvider = "test-data")
	public void test02(String data) {

		driver = new WebDriverUtility(TestcaseBase.getDriver());
		
		//method to securely type-in values into the search field
		driver.typeText(driver.returnElement(WebElements.searchInputField), data);
		
		//let's try to search for the values from data provider
		driver.returnElement(WebElements.searchBtn).click();
		
	} 
	
	@Test
	public void test03() {

		driver = new WebDriverUtility(TestcaseBase.getDriver());
		
		String firstURL = WebDriverUtility.getDriver().getCurrentUrl();
		
		//take a screen shoot
		WebDriverUtility.captureScreenshot();
		
		//click on contact us
		driver.returnElement(WebElements.contactUsBtn).click();
		
		String secondURL = WebDriverUtility.getDriver().getCurrentUrl();
		
		//take a screen shoot
		WebDriverUtility.captureScreenshot();
		
		//let compare URLs, try to see if they are equals
		Assert.assertEquals(firstURL, secondURL);

		//URLs should not be equal, I am forcing it to fail
		
		//failure (java.lang.AssertionError) can be visible in the console
		
		//screen shots should be on the project //test-output folder
	} 
	
	@Test
	public void test04() throws InterruptedException {
		
		//I think the intention of this web site/app is to sell and so the user can buy clothes
		//I have realized that eventually, the purchase flow  requires login and so will try to test it
		//by providing a unique e-mail ID during the test and verifying if that e-mail is accepted and navigation redirects 
		//to the next page
		
		driver = new WebDriverUtility(TestcaseBase.getDriver());
		
		String urlToTest = "http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation"; 

		driver.returnElement(WebElements.signInBtn).click();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		String email =  "Test_" + timestamp.toString().replace(":", "_").replace(" ", "_") + "@gmail.com";
		
		//method to securely type-in values into the email address field
		driver.typeText(driver.returnElement(WebElements.emailAddressInputField), email);

		driver.returnElement(WebElements.createAccountBtn).click();
		
		//let's wait for some object to appear, in this case I am doing this trick to just make sure the page is loaded and 
		//so the URL is properly changed, otherwise the next Assert fails
		//strictly speaking the following doesn't work as expected, I have tried numerous different wait's (unless using Thread.sleep())
		
		//try a JS command to see if the page is loaded
		driver.waitForPageCompleteLoad();

		//try to scroll into view (on the button, just to spend more time on the page for URL to be updated)
		driver.scrollIntoView(WebElements.registerBtn);
		
		//overall, it needs more work to hande this kind of situation, will require waits customization 
		
		Assert.assertEquals(urlToTest, WebDriverUtility.getDriver().getCurrentUrl());

	}
	
	@AfterMethod
	public void tearDown(){
		TestcaseBase.terminateAll();
	}
}
