package com.raonsnc.scim.config;



import java.util.HashMap;
import java.util.Map;

import com.raonscn.scim.config.ScimConfiguration;
import com.raonsnc.scim.repo.conf.DataSourceConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScimAgentConfig extends ScimConfiguration {
	String name;
	
	int port 	 = 9999;
	int idleTime = 1000*60*10; 
	DataSourceConfig dataSource;
	
	Map<String,String> paths = new HashMap<String, String>();
	public static ScimAgentConfig load(String fileName) throws Exception {
		return load(ScimAgentConfig.class, fileName);
	}
}
