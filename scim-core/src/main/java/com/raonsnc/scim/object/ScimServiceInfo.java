package com.raonsnc.scim.object;

import lombok.Data;

@Data
public class ScimServiceInfo {
	String name;
	String type;
	
	String entitySchema;
	String entityClass;
	String identityClass;

	String transferSchema;
	String transferClass;
	String metaClass;
}
