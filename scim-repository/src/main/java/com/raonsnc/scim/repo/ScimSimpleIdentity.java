package com.raonsnc.scim.repo;

import com.google.gson.GsonBuilder;
import com.raonsnc.scim.schema.ScimTypeDefinition;

public class ScimSimpleIdentity extends ScimEntityAttribute {
	public ScimSimpleIdentity(ScimEntityAttribute schema) {
		super(schema);
		this.mutability = ScimTypeDefinition.Mutability.readOnly.name();
		this.returned   = ScimTypeDefinition.Returned.ALWAYS.value();
		this.uniqueness = ScimTypeDefinition.Uniqueness.server.name();
		this.name		= "id"; 
	}
	
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
