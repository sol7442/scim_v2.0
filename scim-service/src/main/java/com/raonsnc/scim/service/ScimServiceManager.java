package com.raonsnc.scim.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.sql.DataSource;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.object.ScimServiceInfo;
import com.raonsnc.scim.repo.ScimRepositoryService;
import com.raonsnc.scim.repo.impl.ScimDataSourceBuilder;
import com.raonsnc.scim.repo.impl.ScimRepositoryAdapter;
import com.raonsnc.scim.repo.DataStorage;
import com.raonsnc.scim.repo.DataStorageRegistry;
import com.raonsnc.scim.service.resource.ScimResourceService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ScimServiceManager {
	ScimRepositoryService repository;
	Map<String,ScimService> services = new HashMap<String, ScimService>();
	
	@Inject
	ScimServiceConfig config;
	
	@Inject
	public void load() {
		try {
			loadRepository();
			
			for (Entry<String,ScimServiceInfo> entry : config.getServices().entrySet()) {
				log.info(" - {}:{}",entry.getKey(),entry.getValue());
			}
		}catch (Exception e) {
			log.warn("load failed : {}" ,e.getMessage());
		}
	}

	private void loadRepository(){
		try {
			if(config.getDataSource() != null && config.getDataStorage() != null) {
				DataSource  data_source  = new ScimDataSourceBuilder().build(config.getDataSource());
				DataStorage data_storage = DataStorageRegistry.getInstance().create(data_source,config.getDataStorage());
				repository = new ScimRepositoryAdapter("repository", data_source, data_storage);
				if(repository.test()) {
					log.info("storage : {}:{}",config.getDataStorage().getDriver(), config.getDataSource().getJdbcUrl());	
				}
			}else {
				log.info("repository configuration not found : {} ",config);	
			}
		}catch (ScimException e) {
			log.warn("{}:",e.getMessage());
		}
		
		
	}
	
	public ScimService getService(String type,String method) {
		return null;
	}

	public ScimResourceService getEntityService(String type) {
		return (ScimResourceService) services.get(type);
	}
}
