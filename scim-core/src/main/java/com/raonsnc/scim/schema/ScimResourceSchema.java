package com.raonsnc.scim.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.GsonBuilder;

import lombok.Builder;
import lombok.Data;


/**
 *	RDB : TABLE, VIEW, (TABLE)SYNONYM
 */
@Data
@Builder
public class ScimResourceSchema {
	String name;
	String owner;
	String refInfo;
	
	String id;
	String endpoint;
	String schmea;
	String version;
	List<ScimSchemaExention> schemaExentions;
	
	ScimTypeDefinition.StorageType type;
	
	List<String> schemas = new ArrayList<String>();
	Map<String,ScimAttributeSchema> attributes = new HashMap<String,ScimAttributeSchema>();
	
	boolean managed;
	String entityClassName;
	
	ScimIdentitySchema identitySchema;
	String identityClassName;
	
	
	public ScimAttributeSchema getAttribute(String key) {
		return this.attributes.get(key);
	}
	public ScimAttributeSchema getAttributeByColumn(String column) {
		for (Entry<String, ScimAttributeSchema> entry : attributes.entrySet()) {
			if(entry.getValue().getColumnName().equals(column)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	
	public String getStorageName() {
		return new StringBuffer().append(this.owner).append(".").append(this.name).toString();  
	}
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
