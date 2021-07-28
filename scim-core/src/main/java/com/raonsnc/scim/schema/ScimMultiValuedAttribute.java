package com.raonsnc.scim.schema;


public class ScimMultiValuedAttribute extends ScimComplexAttribute{

	ScimMultiValuedAttribute(ScimComplexAttribute schema) {
		super(schema);
		this.multiValued=true;
	}
}
