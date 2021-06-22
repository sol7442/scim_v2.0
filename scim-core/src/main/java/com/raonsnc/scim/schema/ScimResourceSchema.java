package com.raonsnc.scim.schema;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;


/**
 *	RDB : TABLE, VIEW, (TABLE)SYNONYM
 */
@Data
@Builder
public class ScimResourceSchema {
	String name;
	String owner;
	String refInfo;
	ScimType.ResouceType type;
	
	List<String> schemas = new ArrayList<String>();
	List<ScimAttributeSchema> attributes = new ArrayList<ScimAttributeSchema>();
}
