package org.zbib.testcases.UI;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

public class BiblographyTest {

	private DriverFactory driv;
	private Properties prop;
	private HomePage homeElement;
	
	@BeforeMethod
	public void setup() throws InterruptedException {
		driv = new DriverFactory();
		prop = new ConfigReader().initialize_prop();
		driv.initialize_driver(prop.getProperty("browser"));
		homeElement =new HomePage(driv.getDriver());
		homeElement.manualEntry("Bill", "Bill Title", "Bill Fname", "Bill Lname");
		homeElement.searchOnline("Jane Eyre", "Brontë, Williams – 2015 – Oberon Books");
		homeElement.manualEntry("Book", "Demo Title", "Demo Fname", "Demo Lname");
		homeElement.searchOnline("Whereabouts", "Kadane – 1971 – Operations Research");
	}
	
	@Test(priority=1)
	public void verifyBiblograhy(){
		List<String> ele =homeElement.bibContent();
		List<String> titles = new ArrayList<String>(Arrays.asList("Bill Lname, Bill Fname.",
				"Brontë, Charlotte, and John Williams. Jane Eyre. Oberon Books, 2015.",
				"Demo Fname. Demo Title","Kadane, Joseph B. “Optimal Whereabouts Search.” Operations Research,"
						+ " vol. 19, no. 4, Aug. 1971, pp. 894–904. DOI.org (Crossref), https://doi.org/10.1287/opre.19.4.894."));
		int i=0;
		for(String ele1 : ele) {
			boolean status = ele1.contains(titles.get(i));
			Assert.assertEquals(status, true);
			i +=1;
		}
	}
	
	@Test(priority=2)
	public void verifyCopyLink() throws InterruptedException  {
		List<String> homeContent = homeElement.bibContent();
		 ArrayList<String> tabContent = homeElement.validateViewLink();

		 System.out.println(homeContent.containsAll(tabContent));
	}
	
	@AfterMethod
	public void tearDown() {
		driv.getDriver().quit();
	}
}
