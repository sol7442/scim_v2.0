package com.raonsnc.scim.repo;

import javax.sql.DataSource;

import com.raonsnc.scim.repo.conf.StorageConfig;

public interface DataStorageFactory {
	public String getName();
	public DataStorage create(DataSource data_source, StorageConfig storage);
}
