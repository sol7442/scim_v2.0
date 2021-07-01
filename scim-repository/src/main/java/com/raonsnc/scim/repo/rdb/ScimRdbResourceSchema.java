package com.raonsnc.scim.repo.rdb;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.GsonBuilder;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScimRdbResourceSchema extends ScimResourceSchema {
	
	String storageOwner;
	String storageName;
	ScimTypeDefinition.StorageType storageType;
	
	String version;
	String entityClassName;
	String identityClassName;
	
	public void addAttribute(ScimRdbAttributeSchema attribute) {
		if(attributes == null) {
			attributes = new HashMap<String, ScimAttributeSchema>();
		}
		this.attributes.put(attribute.getName(), attribute);
	}
	public ScimRdbAttributeSchema getAttribute(String key) {
		return (ScimRdbAttributeSchema) this.attributes.get(key);
	}
	
	public ScimRdbAttributeSchema findAttributeByColumn(String column) {
		for (Entry<String, ScimAttributeSchema> attribute : attributes.entrySet()) {
			if (attribute instanceof ScimRdbAttributeSchema) {
				ScimRdbAttributeSchema rdb_attribute = (ScimRdbAttributeSchema) attribute;
				if(rdb_attribute.getColumnName().equals(column)) {
					return rdb_attribute;
				}
			}
		}
		return null;
	}
	
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
