package com.raonsnc.scim.repo;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;
import com.raonsnc.scim.repo.rdb.ScimRdbService;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;


public class ScimRepositoryAdapter extends ScimRdbService implements ScimRepositoryService {
	String name;
	String type;
	public ScimRepositoryAdapter(String name, DataSource data_source, ScimStorage storage) {
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
	public List<ScimResourceSchema> getResourceSchemaList(String schema) throws ScimException {
		return this.storage.getResourceSchemaList(schema);
	}

	@Override
	public void findAttributeSchema(ScimResourceSchema resource) throws ScimException {
		this.storage.findAttributeSchema(resource);
	}

	@Override
	public Map<String, ScimAttributeSchema> getAttributeSchema(ScimResourceSchema resource) throws ScimException {
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
