package com.raonsnc.scim.schema;

import java.util.Map;

import lombok.Data;


/**
 * RDB : TABLE, VIEW, (TABLE)SYNONYM
 */
@Data
public class ScimResourceSchema {
	
	public String id;
	public String name;
	public String description;

	public Map<String, ScimAttributeSchema> attributes;
}
