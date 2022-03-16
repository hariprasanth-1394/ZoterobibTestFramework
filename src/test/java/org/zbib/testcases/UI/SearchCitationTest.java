package org.zbib.testcases.UI;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
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

public class SearchCitationTest {
	private DriverFactory driv;
	private Properties prop;
	private HomePage homepage;
	private String filePath= "D:\\Official\\ZoterobibTestFramework\\src\\main\\resources\\Testdata\\SearchCitationTestData.xlsx";
	private String sheetname = "Sheet1";
	
	@BeforeMethod
	public void setup() {
		driv = new DriverFactory();
		prop = new ConfigReader().initialize_prop();
		driv.initialize_driver(prop.getProperty("browser"));
		homepage =new HomePage(driv.getDriver());
	}
	
	@Test(priority=1)
	public void verifySingleSearchCitation() throws InterruptedException, UnsupportedFlavorException, IOException {
		homepage.searchOnline("Jane Eyre", "Brontë, Williams – 2015 – Oberon Books");
		String coppiedContent = homepage.copytoboard();
		boolean flag=coppiedContent.contains(" Jane Eyre. Oberon Books, 2015");
		Assert.assertEquals(flag, true);
	}

	
	@DataProvider
	public Object[][] getSearchCitationData() {
		Object data[][] = ExcelReader.getTestData(filePath, sheetname);
		return data;
	}
	
	@Test(priority=2, dataProvider="getSearchCitationData")
	public void verifyMulipleSearchCitation(String name, String author) throws InterruptedException {
		homepage.searchOnline(name,author);
	}
	
	@AfterMethod
	public void tearDown() {
		driv.getDriver().quit();
	}
}
