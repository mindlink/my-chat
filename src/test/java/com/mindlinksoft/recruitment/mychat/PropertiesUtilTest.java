package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class PropertiesUtilTest {

	private Properties properties;
	
	@Before
	public void setUp() {
		properties = PropertiesUtil.loadProperties("src/test/resources/test.properties");
	}
	
	@Test
	public void testLoadProperties() {
		assertEquals("value1", properties.get("test.property.1"));
		assertEquals("value1, value2", properties.get("test.property.2"));
		assertEquals("k1:v1, k2:v2, k3:v3", properties.get("test.property.3"));
	}

	@Test
	public void testListProperty() {
		Collection<String> listProperty = PropertiesUtil.getListProperty("test.property.2", properties);
		String[] values = new String[listProperty.size()];
		listProperty.toArray(values);
		
		assertEquals("value1", values[0]);
		assertEquals("value2", values[1]);
	}
	
	@Test
	public void testMapProperty() {
		Map<String, String> map = PropertiesUtil.getMapProperty("test.property.3", properties);
		
		assertEquals("v1", map.get("k1"));
		assertEquals("v2", map.get("k2"));
		assertEquals("v3", map.get("k3"));
	}
}
