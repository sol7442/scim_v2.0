package com.raonsnc.scim.schema;


import lombok.Data;

/**
 * RDB : COLUMN
 */
@Data
public  class ScimResourceAttribute {
	public String 	name;
	public String 	type;
	public String 	description;
	public boolean 	required;
	public boolean 	caseExact;
	public boolean 	multiValued;
	public String 	mutability;
	public String 	returned;
	public String	uniqueness;
	public String 	referenceType;
	
	public ScimResourceAttribute() {}
	public ScimResourceAttribute(ScimResourceAttribute schema) {
		this.setName(schema.getName());
		this.setType(schema.getType());
		this.setDescription(schema.getDescription());
		this.setRequired(schema.isRequired());
		this.setCaseExact(schema.isCaseExact());
		this.setMutability(schema.getMutability());
		this.setReturned(schema.getReturned());
		this.setUniqueness(schema.getUniqueness());
		this.setReferenceType(schema.getReferenceType());
		//this.setSubAttributes(schema.getSubAttributes());
	}
}
