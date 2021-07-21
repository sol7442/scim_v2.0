package com.raonsnc.scim.service;


import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;

public interface ScimEntityCreateService extends ScimEntityService {
	public void create(ScimEntity entity) 		throws ScimException;
}
