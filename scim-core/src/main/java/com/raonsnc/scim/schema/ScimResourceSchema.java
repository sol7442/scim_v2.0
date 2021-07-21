package com.raonsnc.scim.schema;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.raonsnc.scim.ScimException;

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
	
	public void addAttribute(ScimAttributeSchema attribute) {
		if(attributes == null) {
			attributes = new HashMap<String, ScimAttributeSchema>();
		}
		this.attributes.put(attribute.getName(), attribute);
	}
	public ScimMetaSchema getMeta() {
		ScimMetaSchema meta_schema = null;
		ScimAttributeSchema  meta_attribute = this.attributes.get("meta");
		if(meta_attribute != null) {
			meta_schema = new ScimMetaSchema(meta_attribute);
		}
		
		return meta_schema;
	}
	public void setMeta(ScimMetaSchema meta) {
		this.attributes.put(meta.getName(), meta);
	}
	public static ScimResourceSchema load(String fileName) throws ScimException, IOException {
		FileReader reader = null;
		try {
			File file = new File(fileName);
			if(file.exists()) {
				reader = new FileReader(file);
				return new GsonBuilder().create().fromJson(reader, ScimResourceSchema.class);
				
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
