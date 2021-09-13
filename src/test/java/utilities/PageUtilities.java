package utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.TestcaseBase;
/**
* @author Author: Alex Podimov
*
*/
public class PageUtilities {
	
public void initBrowser(){
		
		if (TestcaseBase.getDriver() == null) {
				
			//I am including webdriver executable along with the project
	        System.setProperty("webdriver.chrome.driver",
	        		System.getProperty("user.dir") + "/src/test/resources/executables/chromedriver.exe");

	        //some Chrome options to pass to Chrome Driver
	        
	        ChromeOptions options = new ChromeOptions();

			options.addArguments("--log-level=0");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-extensions");
			options.addArguments("--start-maximized");
	
			TestcaseBase.setDriver(new ChromeDriver(options));
						
			//I use .configuration file for the following URL/s
			//and other things so it's not hard coded in my framework, here I will just set URL as a string
			TestcaseBase.getDriver().get("http://automationpractice.com/index.php");
			
			//basic selenium waits
			TestcaseBase.getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			TestcaseBase.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			TestcaseBase.setWait(new WebDriverWait(TestcaseBase.getDriver(), 20));
				
		}
	}
	
}
