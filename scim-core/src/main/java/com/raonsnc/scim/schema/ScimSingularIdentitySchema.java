package com.raonsnc.scim.schema;

public class ScimSingularIdentitySchema extends ScimIdentitySchema{
	public ScimSingularIdentitySchema() {
		this.multiValued=true;
	}
}
