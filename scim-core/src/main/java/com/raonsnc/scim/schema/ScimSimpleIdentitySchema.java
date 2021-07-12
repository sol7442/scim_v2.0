package com.raonsnc.scim.schema;

import com.google.gson.GsonBuilder;

public class ScimSimpleIdentitySchema extends ScimSimpleAttributeSchema {
	public ScimSimpleIdentitySchema(ScimAttributeSchema schema) {
		super(schema);
		this.mutability = ScimTypeDefinition.Mutability.readWrite.name();
		this.returned   = ScimTypeDefinition.Returned.ALWAYS.value();
		this.uniqueness = ScimTypeDefinition.Uniqueness.server.name();
	}
	
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
