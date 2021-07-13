package com.raonsnc.scim.repo;

import javax.sql.DataSource;

import com.raonsnc.scim.repo.conf.StorageConfig;

public interface ScimStorageFactory {
	public String getName();
	public ScimStorage create(DataSource data_source, StorageConfig storage);
}
