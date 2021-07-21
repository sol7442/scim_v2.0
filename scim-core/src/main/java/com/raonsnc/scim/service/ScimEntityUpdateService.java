package com.raonsnc.scim.service;

import java.util.List;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;

public interface ScimEntityUpdateService extends ScimEntityService {
	public void create(ScimEntity entity) 		throws ScimException;
	public ScimEntity read(String id) 			throws ScimException;
	public ScimEntity update(ScimEntity entity) throws ScimException;
	public boolean delete(String id) 			throws ScimException;
	public List<ScimEntity> search(ScimFilter filter) throws ScimException;
}
