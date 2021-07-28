package com.raonsnc.scim.schema;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public class ScimMetaSchema extends ScimSingularAttribute  {
	public ScimMetaSchema() {
		this.mutability = ScimTypeDefinition.Mutability.readOnly.name();
		this.type	    = ScimTypeDefinition.DataType.Complex.getType();
		this.multiValued=false;
		this.returned   = ScimTypeDefinition.Returned.DEFAULT.value();
		this.uniqueness = ScimTypeDefinition.Uniqueness.none.name();
	}
	
	public ScimMetaSchema(ScimComplexAttribute attribute) {
		super(attribute);
		this.mutability = ScimTypeDefinition.Mutability.readOnly.name();
		this.type	    = ScimTypeDefinition.DataType.Complex.getType();
		this.multiValued=false;
		this.returned   = ScimTypeDefinition.Returned.DEFAULT.value();
		this.uniqueness = ScimTypeDefinition.Uniqueness.none.name();
		
		setSubAttributes(attribute.getSubAttributes());
	}

	public void addAttribute(ScimResourceAttribute attribute) {
		if(this.subAttributes == null) {
			this.subAttributes = new ArrayList<ScimResourceAttribute>();
		}
		if (attribute instanceof ScimSimpleAttribute) {
			ScimSimpleAttribute meta_attribute = new ScimSimpleAttribute(attribute);
			
			subAttributes.add(meta_attribute);
		}
	}
	
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
