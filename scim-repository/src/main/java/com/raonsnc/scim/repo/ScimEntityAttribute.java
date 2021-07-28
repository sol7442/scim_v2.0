package com.raonsnc.scim.repo;


import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScimEntityAttribute extends ScimResourceAttribute {

	String columnName;
	String className;
	String defaultValue;
	boolean nullAble;
	int index;
	int length;
	
	String name;
	String description;
	
	String 	type;
	String 	typeName;
	int 	typeCode;;
	
	public ScimEntityAttribute() {}
	public ScimEntityAttribute(
			String column_name, ScimTypeDefinition.DataType scim_type, String type_name,
			int data_type, int length, String description, int index, String column_default, 
			boolean is_null_able) {
		
		setName(column_name.toLowerCase());
		setType(scim_type.getType());
		setTypeName(type_name);
		setTypeCode(data_type);
		
		setColumnName(column_name);
		setClassName(column_default);
		setDefaultValue(description);
		setNullAble(is_null_able);
		setIndex(index);
		setLength(length);
	}
	public ScimEntityAttribute(ScimEntityAttribute attribute) {
		super(attribute);
		
		setName(attribute.getName());
		setType(attribute.getType());
		setTypeName(attribute.getTypeName());
		setTypeCode(attribute.getTypeCode());
		
		
		setColumnName(attribute.getColumnName());
		setClassName(attribute.getClassName());
		setDefaultValue(attribute.getDefaultValue());
		setNullAble(attribute.isNullAble());
		setIndex(attribute.getIndex());
		setLength(attribute.getLength());
	}
}
