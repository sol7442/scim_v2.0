package com.raonsnc.scim.represent;


import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimMetaSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScimRepresentResourceSchema extends ScimResourceSchema{
	
	String version;
	String entityClassName;
	String mappingClassName;
	String metaClassName;

	public void addAttribute(ScimResourceAttribute attribute) {
		if(attributes == null) {
			attributes = new HashMap<String, ScimResourceAttribute>();
		}
		this.attributes.put(attribute.getName(), attribute);
	}
	
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
