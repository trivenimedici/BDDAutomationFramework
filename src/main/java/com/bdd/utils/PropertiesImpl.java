package com.bdd.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

public class PropertiesImpl {
	private static Properties defaultproperties ;
	private static final String defaultPropertiesFile = "src//test//resources//DefaultProperties.properties";
	public static void  loadProperties() throws IOException {
		try {
			defaultproperties= new Properties();
			FileInputStream Locator = new FileInputStream(defaultPropertiesFile);
			defaultproperties.load(Locator);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getPropertyValue(String ElementName) throws Exception {
		// Read value using the logical name as Key
		String data = defaultproperties.getProperty(ElementName);
		return data;
	}
	
}
