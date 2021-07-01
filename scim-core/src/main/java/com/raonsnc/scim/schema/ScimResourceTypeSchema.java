package com.raonsnc.scim.schema;

import java.util.HashSet;
import java.util.Set;

import com.raonsnc.scim.entity.ScimMeta;

public class ScimResourceTypeSchema {
	Set<String> schemas = new HashSet<String>();
	
	String id;
	String name;
	String endpoint;
	String schema;
	Set<String> schemaExtensions;
	ScimMeta meta;
	
	public void addSchemaExtension(String schema) {
		if (schemaExtensions == null) {
			schemaExtensions = new HashSet<String>();
		}
		schemaExtensions.add(schema);
	}
}
