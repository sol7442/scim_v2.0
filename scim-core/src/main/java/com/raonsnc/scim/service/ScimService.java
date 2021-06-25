package com.raonsnc.scim.service;

import java.util.List;
import java.util.Map;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;

public interface ScimService {
	public void create(ScimEntity entity) 		throws ScimException;
	public ScimEntity read(String id) 	throws ScimException;
	public void update(ScimEntity entity) 		throws ScimException;
	public void delete(String id) 		throws ScimException;
	public List<ScimEntity> search(ScimFilter filter) throws ScimException;
	
	public Map<String,Object> query(String query) throws ScimException;
	public Map<String,Object> call(String query) throws ScimException;
	
	public boolean open() throws ScimException;
	public void close() throws ScimException;
}
