package com.raonsnc.scim.repo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.GsonBuilder;
import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScimEntitySchema extends ScimResourceSchema {
	
	String storageSchema;
	String storageName;
	ScimTypeDefinition.StorageType storageType;
	
	String version;
//	String entityClassName;
//	String identityClassName;
	
	public void addAttribute(ScimEntityAttribute attribute) {
		if(attributes == null) {
			attributes = new HashMap<String, ScimResourceAttribute>();
		}
		this.attributes.put(attribute.getName(), attribute);
	}
	public ScimEntityAttribute getAttribute(String key) {
		return (ScimEntityAttribute) this.attributes.get(key);
	}
	
	public ScimEntityAttribute findAttributeByColumn(String column) {
		for (Entry<String, ScimResourceAttribute> attribute : attributes.entrySet()) {
			if (attribute instanceof ScimEntityAttribute) {
				ScimEntityAttribute rdb_attribute = (ScimEntityAttribute) attribute;
				if(rdb_attribute.getColumnName().equals(column)) {
					return rdb_attribute;
				}
			}
		}
		return null;
	}
	
	public static ScimEntitySchema load(String fileName) throws ScimException, IOException {
		FileReader reader = null;
		try {
			File file = new File(fileName);
			if(file.exists()) {
				reader = new FileReader(file);
				return new GsonBuilder().create().fromJson(reader, ScimEntitySchema.class);
			}else {
				throw new ScimException("file not found : " + fileName);
			}
		}catch (Exception e) {
			throw new ScimException(e.getMessage(),e);
		}finally {
			if(reader != null)
				reader.close();
		}
	}
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
