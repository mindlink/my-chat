package com.mindlinksoft.recruitment.mychat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

/**
 * Utility class for {@link Properties}.
 * 
 */
public class PropertiesUtil {

	/**
	 * Loads {@link Properties} from the specified path.
	 * @param propertiesFilePath
	 * @return {@link Properties} loaded from file.
	 * @throws IllegalArgumentException
	 */
	public static Properties loadProperties(String propertiesFilePath) throws IllegalArgumentException {
		Properties properties = new Properties();
		try (InputStream input = new FileInputStream(propertiesFilePath)) {
			properties.load(input);
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to load properties from " + propertiesFilePath, e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Malformed file input.", e);
		}
		return properties;
	}
	
	/**
	 * Gets a comma separated string for the given property name
	 * and converts it to a {@link Collection}. 
	 *  
	 * @param propertyName
	 * @param properties
	 * @return Collection of strings.
	 */
	public static Collection<String> getListProperty(String propertyName, Properties properties) {
		Collection<String> list = new ArrayList<String>(); 
		if (propertyName != null && properties != null && properties.containsKey(propertyName)) {
			String propertyValue = properties.getProperty(propertyName);
			try {
				list = Arrays.asList(propertyValue.replaceAll("\\s+", "").split(","));
			} catch (PatternSyntaxException e) {
				System.out.println("Failed to parse list for property " + propertyName);
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * Gets a comma separated string with key-value pairs, in the
	 * format key:value and converts it to a {@link Map} 
	 * e.g. key1:value1,key2:value2
	 * 
	 * @param propertyName
	 * @param properties
	 * @return Map of string key-value pairs.
	 */
	public static Map<String, String> getMapProperty(String propertyName, Properties properties) {
		Map<String, String> map = new HashMap<String, String>();
		if (propertyName != null && properties != null && properties.containsKey(propertyName)) {
			String propertyValue = properties.getProperty(propertyName);
			try {
				String[] pairs = propertyValue.replaceAll("\\s+", "").split(",");
				for (int i=0;i<pairs.length;i++) {
				    String pair = pairs[i];
				    String[] keyValue = pair.split(":");
				    map.put(keyValue[0], keyValue[1]);
				}
			} catch (PatternSyntaxException e) {
				System.out.println("Failed to parse map for property " + propertyName);
				e.printStackTrace();
			}
		}
		return map;
	}
}
