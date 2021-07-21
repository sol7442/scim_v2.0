package com.raonsnc.scim.schema;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public class ScimMetaSchema extends ScimAttributeSchema  {
	public ScimMetaSchema() {
		this.mutability = ScimTypeDefinition.Mutability.readOnly.name();
		this.type	    = ScimTypeDefinition.DataType.Complex.getType();
		this.multiValued=false;
		this.returned   = ScimTypeDefinition.Returned.DEFAULT.value();
		this.uniqueness = ScimTypeDefinition.Uniqueness.none.name();
	}
	
	public ScimMetaSchema(ScimAttributeSchema attribute) {
		super(attribute);
		this.mutability = ScimTypeDefinition.Mutability.readOnly.name();
		this.type	    = ScimTypeDefinition.DataType.Complex.getType();
		this.multiValued=false;
		this.returned   = ScimTypeDefinition.Returned.DEFAULT.value();
		this.uniqueness = ScimTypeDefinition.Uniqueness.none.name();
		
		setSubAttributes(attribute.getSubAttributes());
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
