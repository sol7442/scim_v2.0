package com.raonsnc.scim.service.resource;


import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;

public interface ScimResourceReadService extends ScimResourceService {
	public static final String NAME = "resource.read"; 
	public ScimEntity read(String id) 			throws ScimException;
}
