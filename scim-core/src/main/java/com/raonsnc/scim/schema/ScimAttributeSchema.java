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
	String methodName;
	String columnName;
	String jsonName;
	ScimTypeDefinition.DataType type;
	String typeName;
	int dataType;
	
	int index;
	int length;
	Boolean multiValued;
	String description;
	Boolean nullAble;
	Boolean required;
	Boolean caseExact;
	
	String defaultValue;
	ScimTypeDefinition.Mutability mutability;
	ScimTypeDefinition.Returned returned;
	ScimTypeDefinition.Uniqueness uniqueness;
	
	List<ScimAttributeSchema> subAttributes;
	List<String> canonicalValues;
	List<ScimTypeDefinition.ReferenceType> referenceTypes;
}
