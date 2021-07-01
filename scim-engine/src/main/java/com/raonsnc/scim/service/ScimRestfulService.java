package com.raonsnc.scim.service;

import java.util.List;
import java.util.Map;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;
import com.raonsnc.scim.service.ScimService;

public class ScimRestfulService implements ScimService{

	@Override
	public void create(ScimEntity entity) throws ScimException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScimEntity read(String id) throws ScimException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScimEntity update(ScimEntity entity) throws ScimException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String id) throws ScimException {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() throws ScimException {
		// TODO Auto-generated method stub
		
	}


}
