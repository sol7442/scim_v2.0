package com.raonsnc.scim.repo;

import javax.sql.DataSource;

import com.raonsnc.scim.repo.conf.ConnectionConfig;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;

public class MariaStorageFactory implements ScimStorageFactory{

	@Override
	public String getName() {
		return org.mariadb.jdbc.Driver.class.getCanonicalName();
	}

	@Override
	public ScimStorage create(DataSource data_source, StorageConfig storage) {
		return new MariaStorage(data_source, storage);
	}

}
