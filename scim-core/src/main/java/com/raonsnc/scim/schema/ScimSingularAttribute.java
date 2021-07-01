package com.raonsnc.scim.schema;


public class ScimSingularAttribute extends ScimComplexAttribute {

	ScimSingularAttribute(ScimAttributeSchema schema) {
		super(schema);
		this.multiValued=true;
	}

}
