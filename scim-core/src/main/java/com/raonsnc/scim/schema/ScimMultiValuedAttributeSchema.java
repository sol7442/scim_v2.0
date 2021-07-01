package com.raonsnc.scim.schema;


public class ScimMultiValuedAttributeSchema extends ScimComplexAttribute{

	ScimMultiValuedAttributeSchema(ScimAttributeSchema schema) {
		super(schema);
		this.multiValued=true;
	}
}
