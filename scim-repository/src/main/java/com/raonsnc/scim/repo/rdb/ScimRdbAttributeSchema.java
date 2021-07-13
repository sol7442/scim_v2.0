package com.raonsnc.scim.repo.rdb;


import com.raonsnc.scim.schema.ScimSimpleAttributeSchema;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScimRdbAttributeSchema extends ScimSimpleAttributeSchema {
	int dataType;
	String typeName;
	String columnName;
	String className;
	String defaultValue;
	boolean nullAble;
	int index;
	int length;
	
	public ScimRdbAttributeSchema(String column_name, ScimTypeDefinition.DataType scim_type, String type_name,
			int data_type, int length, String description, int index, String column_default, boolean is_null_able) {
		
		setName(column_name.toLowerCase());
		setColumnName(column_name);
		setDefaultValue(column_default);
		setDescription(description);
		setNullAble(is_null_able);
		setDataType(data_type);
		setType(scim_type.getType());
		setClassName(scim_type.getClassName());
		setTypeName(type_name);
		setIndex(index);
		setLength(length);
	}
}
