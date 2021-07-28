package com.raonsnc.scim.repo;


import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Consumer;

import javax.sql.DataSource;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.repo.conf.StorageConfig;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DataStorageRegistry {
	static DataStorageRegistry instance;
	Map<String,DataStorageFactory> registry = new HashMap<String, DataStorageFactory>();

	public static DataStorageRegistry getInstance() {
		if(instance == null) {
			instance = new DataStorageRegistry();
		}
		return instance;
	}
	
	public void initialize() {
		ServiceLoader<DataStorageRegister> loader =  ServiceLoader.load(DataStorageRegister.class);
		loader.forEach(new Consumer<DataStorageRegister>() {
			@Override
			public void accept(DataStorageRegister register) {
 
				DataStorageFactory factory = register.regist();
				registry.put(factory.getName(), factory);
				
				log.debug(" - repository regist : {}",factory.getName());
			}
		});
	}
	
	public DataStorage create(DataSource data_source, StorageConfig storage) throws ScimException{
		DataStorageFactory factory = registry.get(storage.getDriver());
		if(factory != null) {
			return factory.create(data_source,storage);
		}
		throw new ScimException("STORAGE NOT FOUND : " + storage.getDriver());
	}
}
