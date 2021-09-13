package webelements;

import org.openqa.selenium.By;

/**
* @author Author: Alex Podimov
*
*/

public class WebElements {
	
	//the following are public and static, no need to create an object/instantiate of this class
	
	//total number of products available on the front page
	public static final By totalNumberOfProducts = By.xpath("//ul[@id='homefeatured']/*");	
	
	//the following two are dynamic methods, where passing numerical value(as String) I can iterate through children of a parent element
	public static By dynamicProductOnTheFrontPage(String str) {
			
		return By.xpath(String.format("//ul[@id='homefeatured']/*[%s]", str));	
	}
	
	//selected product price
	public static final By selectedProductPrice = By.xpath("//span[@id='our_price_display']");		
	
	//add to cart button
	public static final By addToCartBtn = By.xpath("//span[contains(text(),'Add to cart')] ");	
	
	//the small pop-up that come when you deal with cart
	public static final By popUpCart = By.xpath("//div[@id='layer_cart']");
	
	//total product price from (top-right corner of the cart)
	public static final By totalProductsPrice = By.xpath("//div[3]/div[1]/div[1]/div[4]/div[1]/div[2]/div[1]//span[1]");

	//Search input, top-mid main window
	public static final By searchInputField = By.xpath("//input[@id='search_query_top']");
	             
	//search button for the above search input
	public static final By searchBtn = By.xpath("//div[3]/div[1]/div[1]/div[2]/form[1]/button[1]");
			
	//contact us button, should redirect further
	public static final By contactUsBtn = By.xpath("//header/div[2]/div[1]/div[1]/nav[1]/div[2]/a[1]");
	
	//sign in button (top-right) main page
	public static final By signInBtn = By.xpath("//a[contains(text(),'Sign in')]");
	
	//sign in button (top-right) main page
	public static final By emailAddressInputField = By.xpath("//input[@id='email_create']");
		
	//Create an account button
	public static final By createAccountBtn = By.xpath("//button[@id='SubmitCreate']");

	//Register button
	public static final By registerBtn = By.xpath("//button[@id='submitAccount']");
	
}

