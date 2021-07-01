package com.raonsnc.scim.schema;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public class ScimIdentitySchema extends ScimAttributeSchema{
	public ScimIdentitySchema() {
		this.mutability = ScimTypeDefinition.Mutability.readWrite.name();
		this.returned   = ScimTypeDefinition.Returned.ALWAYS.value();
		this.uniqueness = ScimTypeDefinition.Uniqueness.server.name();
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
	
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
