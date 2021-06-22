package com.raonsnc.scim.repo;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimRepositoryAdapter implements ScimRepository {
	DataSource dataSource;
	ScimStorage storage;
	
	public ScimRepositoryAdapter(DataSource data_source, ScimStorage storage) {
		this.dataSource = data_source;
		this.storage = storage;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void distroy() {
		if (dataSource instanceof Closeable) {
			Closeable closeable_data_source = (Closeable) dataSource;
			try {
				closeable_data_source.close();
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
		log.info("{}",dataSource);
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean testConnect() throws ScimException{
		return this.storage.testConnect();
	}

	@Override
	public List<String> getSchemaList() throws ScimException {
		return this.storage.getSchemaList();
	}

	@Override
	public List<ScimResourceSchema> getResourceSchemaList(String schema) throws ScimException {
		return this.storage.getResourceSchemaList(schema);
	}

	@Override
	public void findAttributeSchema(ScimResourceSchema resource) throws ScimException {
		this.storage.findAttributeSchema(resource);
	}

	@Override
	public List<ScimAttributeSchema> getAttributeSchema(ScimResourceSchema resource) throws ScimException {
		return this.storage.getAttributeSchema(resource);
	}




}
