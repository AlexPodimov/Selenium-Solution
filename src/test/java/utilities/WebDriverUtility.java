package utilities;

import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
/**
* @author Author: Alex Podimov
*
*/

// the following class is also from my framework, I have removed a lot from it
public class WebDriverUtility extends TestcaseBase {

	private static ThreadLocal<String> screenshotPath = new ThreadLocal<String>();
	private static ThreadLocal<String> screenshotName = new ThreadLocal<String>();

	private WebDriver driver;

	// parameterized constructor
	public WebDriverUtility(WebDriver driver) { 
		
		this.driver =  driver;
		
	}

	//some extra for you to look at
	//this will try to handle StaleElementReferenceException, can help with some tricky and dynamic object
	public boolean checkElementContainsAttribute(By elem, String attributeName, String containsValue) throws InterruptedException {
		
		boolean boolCondition = true ;
		int counter = 0;
		do{
		
			try{
				
				if(driver.findElement(elem) !=null){
					counter++;
					boolCondition = driver.findElement(elem).getAttribute(attributeName).contains(containsValue);					
					break;
					}
				}
			
			catch(StaleElementReferenceException ex){
			}
		
		}while(counter == 0);
	
	return boolCondition;	
	}

	//I like to send "By"s and get back element
	public WebElement returnElement(By elem) {
		
		return driver.findElement(elem);
		
	}
	
	//this will return a List of WebElements
	public List<WebElement> returnElements(By elem) {
		
		return driver.findElements(elem);
		
	}
	
	//some extra
	/** The intension of the following try/catch block is to handle a check when WebElement should NOT be available
	 * @return boolean */
	
	public boolean handleNoSuchElementException (By elem) {
		
			try {driver.findElement(elem).click();	// Lets try to click on WebElement
				
				return true; //"WebElement should NOT be present, test FAILED
			}
			catch(NoSuchElementException ex) {
			
				return false; //"WebElement should NOT be present, test PASSED
			}
	}
	
	//a bit fancy/safe way to type in value in the fields.
	public void typeText(WebElement elem, String valueToType) {
		
		elem.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Let's CNTR+A (select all first, if any value is already typed in the field)
		elem.sendKeys(valueToType);	//type text inside the element's field
	}
	
	//a JS command to scroll page to element , useful just left it here as well
	public void scrollIntoView(By elem) {
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(elem));
	}
	
	
	public static void captureScreenshot() {
		
		try{
			TakesScreenshot scrShot = ((TakesScreenshot) getDriver());
			File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			setScreenshotName(timestamp.toString().replace(":", "_").replace(" ", "_").replace(".", "_") + ".jpg");

			Files.copy(scrFile.toPath(), 
					(new File(System.getProperty("user.dir") + "/test-output/" + getScreenshotName())).toPath()); 
		}		
		
		catch(Exception e) {
			 e.toString();
		}
	}
	
	public void waitForElemetVisability(By elem) {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(elem));
		
	}
	public void waitForPageCompleteLoad() {
		
		new WebDriverWait(driver, 10).until(
				webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
		
		
	}
	
	// ============ ThreadLocal For screenshotPath ========
		public static void setScreenshotPath(String path){		
			screenshotPath.set(path);
		}
		
		public static String getScreenshotPath(){		
			return screenshotPath.get();
		}
		
		// ============ ThreadLocal For screenshotName ========
		public static void setScreenshotName(String name){		
			screenshotName.set(name);
		}
		
		public static String getScreenshotName(){		
			return screenshotName.get();
		}
	

}
