package com.raonsnc.scim.service.resource;


import com.raonsnc.scim.ScimException;

public interface ScimResourceDeleteService extends ScimResourceService {
	public boolean delete(String id) 			throws ScimException;
}
