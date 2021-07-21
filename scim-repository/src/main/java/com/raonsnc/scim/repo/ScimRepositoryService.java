package com.raonsnc.scim.repo;


import java.util.Map;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.service.ScimEntityAllService;

public interface ScimRepositoryService extends ScimEntityAllService, ScimStorageService{
	public boolean	test() 	throws ScimException;
	public boolean 	open() 	throws ScimException;
	public void 	close() throws ScimException;
	
	public Map<String, Object> query(String query) throws ScimException ;
	public Map<String, Object> call(String query) throws ScimException ;
}

