package com.raonsnc.scim.service;


import com.raonsnc.scim.ScimException;

public interface ScimEntityDeleteService extends ScimEntityService {
	public boolean delete(String id) 			throws ScimException;
}
