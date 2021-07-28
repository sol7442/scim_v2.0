package com.raonsnc.scim.repo;

import javax.sql.DataSource;

import com.raonsnc.scim.repo.conf.StorageConfig;


public class MysqlStorageFactory implements DataStorageFactory {
	@Override
	public DataStorage create(DataSource data_source, StorageConfig storage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName() {
		return com.mysql.jdbc.Driver.class.getCanonicalName();
	}


}
