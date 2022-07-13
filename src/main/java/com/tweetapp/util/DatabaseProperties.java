package com.tweetapp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {
	private final Properties properties;

	DatabaseProperties() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("src/main/resources/db.properties"));
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found: " + ex.getLocalizedMessage());
		} catch (IOException ioex) {
			System.out.println("IOException: " + ioex.getLocalizedMessage());
		}
	}

	public String readProperty(String keyName) {
		return properties.getProperty(keyName, "There is no key in the properties file");
	}
}
