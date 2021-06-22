package com.raonsnc.scim.repo;

import java.util.List;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;

public interface ScimStorage {
	public boolean testConnect()throws ScimException;
	public List<String> getSchemaList() throws ScimException;
	public List<ScimResourceSchema> getResourceSchemaList(String schema) throws ScimException ;
	public List<ScimAttributeSchema> getAttributeSchema(ScimResourceSchema resource) throws ScimException;
	public void findAttributeSchema(ScimResourceSchema resource) throws ScimException;
	
}
