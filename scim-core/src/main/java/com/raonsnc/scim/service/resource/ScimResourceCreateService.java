package com.raonsnc.scim.service.resource;


import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;

public interface ScimResourceCreateService extends ScimResourceService {
	public void create(ScimEntity entity) 		throws ScimException;
}
