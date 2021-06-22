package com.raonsnc.scim.schema;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * RDB : COLUMN
 */
@Data
@Builder
public class ScimAttributeSchema {
	String uri;
	String name;
	ScimType.DataType type;
	Boolean multiValued;
	String description;
	Boolean nullAble;
	Boolean required;
	Boolean caseExact;
	String defaultValue;
	ScimType.Mutability mutability;
	ScimType.Returned returned;
	ScimType.Uniqueness uniqueness;
	
	List<ScimAttributeSchema> subAttributes;
	List<String> canonicalValues;
	List<ScimType.ReferenceType> referenceTypes;
}
