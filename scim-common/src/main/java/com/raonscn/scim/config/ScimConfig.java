package com.raonscn.scim.config;

import java.util.HashMap;
import java.util.Map;


public class ScimConfig {
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
}
