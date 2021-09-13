package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
* @author Author: Alex Podimov
*
*/


public class TestcaseBase {

	// I have used this class as well, from my framework and kept threaded variables, 
	// I used to run "Headless" Chrome mode, 10-30 instances to see how our AWS hosted stuff can handle it :)
	// obviously removed quite a lot from here
	
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();	
	public static ThreadLocal<WebDriverWait> wait = new ThreadLocal<WebDriverWait>();
	
	
	//Close the current browser
	
	public static void terminateAll(){		
		if(TestcaseBase.driver!=null){	
			TestcaseBase.getDriver().quit();
			TestcaseBase.setDriver(null);
		}
	}
	
	//getters / setters
	public static void setWait(WebDriverWait wdw){		
		wait.set(wdw);
	}

	public static WebDriverWait getWait(){		
		return wait.get();
	}
	
	public static void setDriver(WebDriver dr){		
		driver.set(dr);
	}

	public static WebDriver getDriver(){		
		return driver.get();
	}
}
