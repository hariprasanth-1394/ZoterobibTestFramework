package org.zbib.testcases.UI;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.zbib.factory.DriverFactory;
import org.zbib.pages.HomePage;
import org.zbib.utilities.ConfigReader;
import org.zbib.utilities.ExcelReader;

public class ManualCitationTest {
	
	private DriverFactory driv;
	private Properties prop;
	private HomePage homeElement;
	private String filePath= "D:\\Official\\ZoterobibTestFramework\\src\\main\\resources\\Testdata\\ManualEntryTestData.xlsx";
	private String sheetname = "Sheet1";
	
	@BeforeMethod
	public void setup() {
		driv = new DriverFactory();
		prop = new ConfigReader().initialize_prop();
		driv.initialize_driver(prop.getProperty("browser"));
		homeElement =new HomePage(driv.getDriver());
	}
	
	@Test(priority=1)
	public void verifySingleManualCitation() throws InterruptedException {
		homeElement.manualEntry("Book", "Demo Title", "Demo Fname", "Demo Lname");
		Thread.sleep(5000);
		boolean message = homeElement.verifySuccessMessage();
		Assert.assertEquals(message, true);
	}
	
	@DataProvider
	public Object[][] getCitationData() {
		Object data[][] = ExcelReader.getTestData(filePath, sheetname);
		return data;
	}
	
	@Test(priority=2, dataProvider="getCitationData")
	public void validateMultipleManualCitation(String type, String title,String fname, String lname) throws InterruptedException, UnsupportedFlavorException, IOException {
		homeElement.manualEntry(type, title, fname,lname);
		Thread.sleep(5000);
		homeElement.updateBibTitle("Chicago Manual of Style 17th edition (note)");
		ArrayList<String> l1 = homeElement.bibContent();
		for(String li : l1) {
			boolean as= li.contains(title);
			if(as==true) {
				break;
			}
		}
		String content = homeElement.copytoboard();
		boolean flag = content.contains(title);
		Assert.assertEquals(flag, true);
	}
	
	@AfterMethod
	public void tearDown() {
		driv.getDriver().quit();
	}
}
