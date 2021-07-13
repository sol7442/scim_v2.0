package com.raonsnc.scim.tester.repo;


import java.io.Closeable;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.repo.ScimStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.rdb.ScimDataSourceBuilder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class RepositoryTester {
	static String configFile;
	static String adapterFile;
	@BeforeAll
	static public void initialize() {
		try {
			ScimStorageRegistry.getInstance().initialize();
			configFile 	= "../config/maria_config.yaml";
			adapterFile = "../config/maria_adapter.yaml";
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@AfterAll
	static public void destroy() {
		try {
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}
	
	
	DataSource data_source = null;
	public RepositoryTester() {
		ScimStorageRegistry.getInstance().initialize();
	}
	
	@Test
	public void connection() {
		boolean result = false;
		try {
			data_source = new ScimDataSourceBuilder().build(ConfigrationHandler.getInstance().load(DataSourceConfig.class, configFile));
			result = data_source.getConnection().isValid(1000);
			
			Closeable closeable_data_source = (Closeable)data_source;
			closeable_data_source.close();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			log.debug(":{}",result);
		}
	}
}
