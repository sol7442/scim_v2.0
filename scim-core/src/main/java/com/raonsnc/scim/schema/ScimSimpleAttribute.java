package com.raonsnc.scim.schema;

public class ScimSimpleAttribute extends ScimResourceAttribute{
	public ScimSimpleAttribute() {}
	public ScimSimpleAttribute(ScimResourceAttribute schema) {
		super(schema);
		this.multiValued=false;
	}
}
