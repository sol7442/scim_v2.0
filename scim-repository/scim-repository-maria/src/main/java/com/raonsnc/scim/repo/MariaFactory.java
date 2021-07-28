package com.raonsnc.scim.repo;

import javax.sql.DataSource;

import com.raonsnc.scim.repo.conf.StorageConfig;

public class MariaFactory implements DataStorageFactory{

	@Override
	public String getName() {
		return org.mariadb.jdbc.Driver.class.getCanonicalName();
	}

	@Override
	public DataStorage create(DataSource data_source, StorageConfig storage) {
		return new MariaStorage(data_source, storage);
	}

}
