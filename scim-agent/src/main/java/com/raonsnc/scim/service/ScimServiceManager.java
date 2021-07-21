package com.raonsnc.scim.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import com.raonsnc.scim.config.ScimServiceConfig;
import com.raonsnc.scim.object.ScimServiceInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ScimServiceManager {
	
	Map<String,ScimService> services = new HashMap<String, ScimService>();
	
	@Inject
	ScimServiceConfig config;
	
	@Inject
	public void load() {
		try {
			log.info("----");
			for (Entry<String,ScimServiceInfo> entry : config.getServices().entrySet()) {
//				log.info(" - {}:{}",entry.getKey(),entry.getValue().getClassName());
			}
			
		}catch (Exception e) {
			
		}finally {
			log.info("----");
		}
	}
	
	public ScimService getService(String type,String method) {
		return null;
	}

	public ScimEntityService getEntityService(String type) {
		return (ScimEntityService) services.get(type);
	}
}
