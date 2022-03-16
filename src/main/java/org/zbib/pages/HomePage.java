package org.zbib.pages;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	private WebDriver driver;
	//Search and Popup
	private By searchBox = By.xpath("//div[@class='input-group input']//input");
	private By citeButton = By.xpath("//button[contains(text(), 'Cite')]");
	private By popupHeader = By.xpath("//div[@class='modal-header']");
	private By searchlist = By.xpath("//li[@class='result']");
	private By addedcitation = By.xpath("//section[@class='section section-review review']//button[1]");
	
	//success banner
	private By message = By.xpath("//div[@class='message success']//p");
	private By close = By.xpath("//div[@class='message success']//button[@class='btn btn-icon close']");
	
	//Manual Entry
	private By manulbutton = By.xpath("//button[@class='btn btn-sm btn-outline-secondary']");
	private By popup = By.xpath("//div[@class='modal-content']");
	private By itemType = By.id("itemType");
	private By title = By.xpath("//div[@class='input-group input']//input[@id='title']");
	private By fname = By.xpath("//input[@placeholder='first name']");
	private By lname = By.xpath("//input[@placeholder='last name']");
	
	//Biblography Section
	private By section = By.xpath("//section[@class='section section-bibliography']//div[@class='container']");
	private By bibTitle = By.xpath("//div[@class='editable-content placeholder']");
	private By editTitle = By.xpath("//input[@class='editable editing editable-control']");
	
	//copy to Clipboard
	private By board = By.xpath("//button[@class='btn btn btn-secondary btn-xl copy-to-clipboard']");
	
	//Creat biblograhy view
	private By createBtn = By.xpath("//button[contains(text(),'Create')]");
	private By viewBtn = By.xpath("//a[contains(text(), 'View')]");
	private By viewSection = By.xpath("//div[@class='permalink-tools']");
	
	//view in another tab
	private By tabContent = By.xpath("//div[@class='csl-bib-body']//div[@class='csl-entry']");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void searchOnline(String params1,String params2) throws InterruptedException {
		WebElement search = driver.findElement(searchBox);
		search.sendKeys(params1);
		driver.findElement(citeButton).click();;
		Thread.sleep(10000);
		String mainWinHander = driver.getWindowHandle();
		WebDriver popup = driver.switchTo().window(mainWinHander);
		List<WebElement> al = popup.findElements(By.xpath("//li[@class='result']"));
        for(WebElement al1 : al) {
        	String book = al1.getAttribute("data-signature");
        	if(book.contains(params2)) {
        		al1.click();
        		break;
        	}
        }
        driver.switchTo().activeElement();
        Thread.sleep(5000);
	}
	
	public boolean verifySuccessMessage() {
		String messageText = driver.findElement(message).getText();
		boolean mess = messageText.contains("Your first citation has been added. Citations are stored locally in your browser.");
		driver.findElement(close).click();
		return mess;
	}
	
	public void manualEntry(String itemtype, String title1, String finame, String laname) throws InterruptedException {
		driver.findElement(manulbutton).click();
		Thread.sleep(6000);
        String mainWinHander1 = driver.getWindowHandle();
        WebDriver manualpopup = driver.switchTo().window(mainWinHander1);
        manualpopup.findElement(itemType).click();
        manualpopup.findElement(By.xpath("//div[@class='select-menu']//div[contains(text(), '" + itemtype + "')]")).click();
        Thread.sleep(3000);
		manualpopup.findElement(title).sendKeys(title1);
		manualpopup.findElement(fname).sendKeys(finame);
		manualpopup.findElement(lname).sendKeys(laname);
		Thread.sleep(1000);
		manualpopup.findElement(By.xpath("//button[contains(text(), 'Done')]")).click();
		driver.switchTo().activeElement();
		Thread.sleep(3000);
	} 

	public void updateBibTitle(String title) {
	     WebDriverWait wait = new WebDriverWait(driver,5);
 		 wait.until(ExpectedConditions.visibilityOf(driver.findElement(section)));
	     driver.findElement(bibTitle).click();
	     driver.findElement(editTitle).sendKeys(title);
	}
	
	public String getBibTitle() {
		return driver.findElement(bibTitle).getText();
	}
	
	public void selectTemplate(String templateName) {
		driver.findElement(By.xpath("//div[@class='select-menu']//div[contains(text(), '" + templateName + "')]"));
	}
	
	public ArrayList<String> bibContent() {
		List<WebElement> al = driver.findElements(By.xpath("//ul[@class='bibliography']//li//div[@class='csl-entry']"));
		ArrayList<String> texts = new ArrayList<String>();
		for(WebElement content : al) {
			texts.add(content.getText());
		}
		return texts;
	}
	
	public String copytoboard() throws UnsupportedFlavorException, IOException {

		driver.findElement(board).click();;
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		String copiedData = (String) contents.getTransferData(DataFlavor.stringFlavor);
		return copiedData;
	}
	
	public ArrayList<String> validateViewLink() throws InterruptedException {
		driver.switchTo().activeElement();
		driver.findElement(createBtn).click();
		Thread.sleep(6000);
//		 WebDriverWait wait = new WebDriverWait(driver,10);
// 		 wait.until(ExpectedConditions.visibilityOf(driver.findElement(viewSection)));
		String oldTab = driver.getWindowHandle();
		String clicklnk = Keys.chord(Keys.CONTROL,Keys.ENTER);
	    driver.findElement(By.xpath("//a[contains(text(), 'View')]")).sendKeys(clicklnk);;
	    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    Thread.sleep(5000);
	    
	    List<WebElement>tabcon = driver.findElements(tabContent);
	    ArrayList<String> citations = new ArrayList<String>();
	    for(WebElement tab : tabcon) {
	    	citations.add(tab.getText());
	    }
	    driver.switchTo().window(tabs.get(0));
	    return citations;
	}
	
}
