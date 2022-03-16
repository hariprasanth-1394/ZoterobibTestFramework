package org.zbib.factory;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.zbib.utilities.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	public static WebDriver driver;
	public ConfigReader reader = new ConfigReader();
	
	public WebDriver initialize_driver(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}else {
			System.out.println("Entered Browser is not Configured: "+ browser);
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		Properties prop=reader.initialize_prop();
		driver.get(prop.getProperty("url"));
		return driver;
	}
	
	public static synchronized WebDriver getDriver() {
		return driver;
	}
}
