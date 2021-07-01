package com.raonsnc.scim.schema;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * RDB : COLUMN
 */
@Data
public  class ScimAttributeSchema {
	public String name;
	public String type;
	public String description;
	public boolean required;
	public boolean caseExact;
	public boolean multiValued;
	public String 	mutability;
	public String 	returned;
	public String	uniqueness;
	public String referenceType;
	
	public List<String> canonicalValues;
	public List<ScimAttributeSchema> subAttributes;
	public List<String> referenceTypes;
	
	public ScimAttributeSchema() {}
	public ScimAttributeSchema(ScimAttributeSchema schema) {
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
	public void addAttribute(ScimAttributeSchema attribute) {
		if(this.subAttributes == null) {
			this.subAttributes = new ArrayList<ScimAttributeSchema>();
		}
		if (attribute instanceof ScimSimpleAttributeSchema) {
			ScimSimpleAttributeSchema meta_attribute = new ScimSimpleAttributeSchema(attribute);
			
			subAttributes.add(meta_attribute);
		}
	}
}
