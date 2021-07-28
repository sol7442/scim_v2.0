package com.raonsnc.scim.schema;


public class ScimSingularAttribute extends ScimComplexAttribute {
	ScimSingularAttribute(){
		this.multiValued=false;
	}
	ScimSingularAttribute(ScimComplexAttribute schema) {
		super(schema);
		this.multiValued=false;
	}

}
