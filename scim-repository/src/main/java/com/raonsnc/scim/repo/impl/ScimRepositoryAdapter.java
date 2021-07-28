package com.raonsnc.scim.repo.impl;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;
import com.raonsnc.scim.repo.DataStorage;
import com.raonsnc.scim.repo.ScimEntitySchema;
import com.raonsnc.scim.repo.ScimRepositoryService;
import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimResourceSchema;


public class ScimRepositoryAdapter extends ScimRepositoryServiceImpl implements ScimRepositoryService {
	String name;
	String type;
	public ScimRepositoryAdapter(String name, DataSource data_source, DataStorage storage) {
		super(data_source, storage);
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean test() throws ScimException {
		return this.storage.testConnect();
	}
	@Override
	public boolean open() throws ScimException {
		return this.storage.testConnect();
	}

	@Override
	public void close() throws ScimException {
		if (source instanceof Closeable) {
			Closeable closeable_data_source = (Closeable) source;
			try {
				closeable_data_source.close();
			} catch (IOException e) {
				throw new ScimException(e.getMessage(),e);
			}
		}
	}

	@Override
	public List<String> getSchemaList() throws ScimException {
		return this.storage.getSchemaList();
	}

	@Override
	public List<ScimEntitySchema> getEntitySchemaList(String schema) throws ScimException {
		return this.storage.getEntitySchemaList(schema);
	}

	@Override
	public ScimEntitySchema getEntitySchema(String schema, String name) throws ScimException {
		return this.storage.getEntitySchema(schema, name);
	}

	@Override
	public Map<String, ScimResourceAttribute> getAttributeSchema(ScimResourceSchema resource) throws ScimException {
		return this.storage.getAttributeSchema(resource);
	}

	@Override
	public Map<String, Object> query(String query) throws ScimException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> call(String query) throws ScimException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScimEntity> search(ScimFilter filter) throws ScimException {
		// TODO Auto-generated method stub
		return null;
	}
}
