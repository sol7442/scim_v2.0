package com.raonsnc.scim.repo;


import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Consumer;

import javax.sql.DataSource;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.repo.conf.ConnectionPoolConfig;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ScimStorageRegistry {
	static ScimStorageRegistry instance;
	Map<String,ScimStorageFactory> registry = new HashMap<String, ScimStorageFactory>();

	public static ScimStorageRegistry getInstance() {
		if(instance == null) {
			instance = new ScimStorageRegistry();
		}
		return instance;
	}
	
	public void initialize() {
		ServiceLoader<ScimStorageRegister> loader =  ServiceLoader.load(ScimStorageRegister.class);
		loader.forEach(new Consumer<ScimStorageRegister>() {
			@Override
			public void accept(ScimStorageRegister register) {
 
				ScimStorageFactory factory = register.regist();
				registry.put(factory.getName(), factory);
				
				
				log.debug("factory regist : {}",register.regist());
			}
		});
	}
	
	public ScimStorage create(DataSource data_source, StorageConfig storage) throws ScimException{
		ScimStorageFactory factory = registry.get(storage.getDriver());
		if(factory != null) {
			return factory.create(data_source,storage);
		}
		throw new ScimException("STORAGE NOT FOUND : " + storage.getDriver());
	}
	
	
//	public DataSource create(DataSource config, String driver) throws ScimException{
//		if(config == null)
//			throw new ScimNullPointException("DATA SOURCE");
//		
//		HikariConfig hikari_config = new HikariConfig();
//		try {
//			Class.forName(driver);
//
////			hikari_config.setJdbcUrl( config.getUrl());
////			hikari_config.setUsername(config.getUser());
////			hikari_config.setPassword(config.getPasswd());
//			
//		} catch (ClassNotFoundException e) {
//			throw new ScimException(e);
//		}
//
//		return new HikariDataSource(hikari_config);
//	}
}
