package com.raonsnc.scim.service;

import java.util.List;
import java.util.Map;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;
import com.raonsnc.scim.repo.ScimRepository;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.service.ScimService;

public class ScimRepositoryService implements ScimService {
	ScimRepository repository;
	public ScimRepositoryService(ScimRepository repository, ScimResourceSchema schema) {
		this.repository = repository;
		this.repository.setResourcerSchema(schema);
	}
	
	@Override
	public void create(ScimEntity entity) throws ScimException {
		repository.create(entity);
	}

	@Override
	public ScimEntity read(String id) throws ScimException {
		return repository.read(id);
	}

	@Override
	public ScimEntity update(ScimEntity entity) throws ScimException {
		return repository.update(entity);
	}

	@Override
	public boolean delete(String id) throws ScimException {
		return repository.delete(id);
	}

	@Override
	public List<ScimEntity> search(ScimFilter filter) throws ScimException {
		// TODO Auto-generated method stub
		return null;
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
	public boolean open() throws ScimException {
		return this.repository.open();
	}

	@Override
	public void close() throws ScimException {
		this.repository.close();
	}

}
