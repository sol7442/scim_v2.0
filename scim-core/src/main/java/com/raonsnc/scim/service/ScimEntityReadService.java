package com.raonsnc.scim.service;


import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;

public interface ScimEntityReadService extends ScimEntityService {
	public ScimEntity read(String id) 			throws ScimException;
}
