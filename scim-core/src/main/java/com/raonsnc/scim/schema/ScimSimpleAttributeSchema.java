package com.raonsnc.scim.schema;

public class ScimSimpleAttributeSchema extends ScimAttributeSchema{
	public ScimSimpleAttributeSchema() {}
	public ScimSimpleAttributeSchema(ScimAttributeSchema schema) {
		super(schema);
		this.multiValued=false;
	}
}
