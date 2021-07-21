package com.raonscn.scim.config;

import java.util.HashMap;
import java.util.Map;

public class ScimConfiguration {
	Map<String,String> props;
	
	public String getProperty(String key) {
		if(props != null) {
			return props.get(key);
		}
		return null;
	}
	public void setProperty(String key, String value) {
		if(props == null) {
			props = new HashMap<String, String>();
		}
		props.put(key, value);
	}
	public void save(String fileName) throws Exception{
		try {
			ConfigrationHandler.getInstance().save(this, fileName);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	public static <T> T load(Class<T> clazz,String fileName) throws Exception {
		try {
			return ConfigrationHandler.getInstance().load(clazz, fileName );
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
