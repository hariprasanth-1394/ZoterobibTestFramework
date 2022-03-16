package org.zbib.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private Properties prop;

	public Properties initialize_prop() {
		prop = new Properties();

		try {
			FileInputStream ips = new FileInputStream(
					"D:\\Official\\ZoterobibTestFramework\\src\\main\\resources\\config\\config.properties");
			prop.load(ips);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
	}
}
