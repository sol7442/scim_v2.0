package com.raonsnc.scim.represent;


import java.util.ArrayList;
import java.util.StringTokenizer;

import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimSimpleAttributeSchema;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScimRepresentAttributeSchema extends ScimAttributeSchema {
//	String setMethod;
//	String getMethod;
//	String mappedName;
//	String methodName;
//	String attributeName;
//	String attributeType;
	
	public ScimRepresentAttributeSchema() {}
	public ScimRepresentAttributeSchema(ScimAttributeSchema schema) {
		this.setName(schema.getName());
		this.setType(schema.getType());
		this.setDescription(schema.getDescription());
		this.setRequired(schema.isRequired());
		this.setCaseExact(schema.isCaseExact());
		this.setMutability(schema.getMutability());
		this.setReturned(schema.getReturned());
		this.setUniqueness(schema.getUniqueness());
		this.setReferenceType(schema.getReferenceType());
		this.setSubAttributes(schema.getSubAttributes());
	}
	@Override
	public void addAttribute(ScimAttributeSchema attribute) {
		if(this.subAttributes == null) {
			this.subAttributes = new ArrayList<ScimAttributeSchema>();
		}
		if (attribute instanceof ScimSimpleAttributeSchema) {
			ScimSimpleAttributeSchema meta_attribute = new ScimSimpleAttributeSchema(attribute);
			
			subAttributes.add(meta_attribute);
		}
	}
	
	public void setName(String name) {
		super.setName(name);
//		this.methodName    = makeMethodName(name);
//		this.attributeName = name.toUpperCase();
	}
	public String makeAttributeName(String name) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(name.substring(0,1).toUpperCase());
		buffer.append(name.substring(1));
		return buffer.toString();
	}
	private String makeMethodName(String name) {
		StringBuffer buffer = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(name, "_");
		while(tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken();
			buffer.append(token.substring(0,1).toUpperCase());
			buffer.append(token.substring(1).toLowerCase());
		}
		return buffer.toString();
	}
}