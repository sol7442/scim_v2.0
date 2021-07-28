package com.raonsnc.scim.engine;

import com.raonsnc.scim.repo.ScimEntityAttribute;
import com.raonsnc.scim.repo.ScimEntitySchema;
import com.raonsnc.scim.schema.ScimResourceSchema;

public class ScimResourceInfo {
	String type;
	String entityClassName;
	String transferClassName;
	ScimResourceSchema 	   	scimSchema;
	ScimEntitySchema  	repositorySchema;
	ScimEntityAttribute identitySchema;
}
