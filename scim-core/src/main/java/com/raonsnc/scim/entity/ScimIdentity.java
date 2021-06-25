package com.raonsnc.scim.entity;

import java.util.Map;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimIdentitySchema;
public interface ScimIdentity extends Map<String,Object>{
	public void setScimIdentitySchema(ScimIdentitySchema schemna);
	
	public void bind(ScimEntity entity)  throws ScimException;
	public String toIdentify()			 throws ScimException;
	public void parse(ScimEntity entity) throws ScimException;
	public void parse(String identity)   throws ScimException;
}
