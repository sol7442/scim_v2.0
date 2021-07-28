package com.raonsnc.scim.repo;

import java.util.List;
import java.util.Map;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimResourceSchema;

public interface DataStorageService {
	public List<String> getSchemaList() throws ScimException;
	public List<ScimEntitySchema> getEntitySchemaList(String schema) throws ScimException ;
	public ScimEntitySchema getEntitySchema(String schema, String name) throws ScimException;
	
	public Map<String,ScimResourceAttribute> getAttributeSchema(ScimResourceSchema resource) throws ScimException;
	
}
