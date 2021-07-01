package com.raonsnc.scim.schema;

public abstract class ScimComplexAttribute extends ScimAttributeSchema{
	ScimComplexAttribute(ScimAttributeSchema schema){
		super(schema);
		this.type	    = ScimTypeDefinition.DataType.Complex.getType();
	}
}

