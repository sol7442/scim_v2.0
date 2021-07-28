package com.raonsnc.scim.schema;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ScimComplexAttribute extends ScimResourceAttribute{
	ScimComplexAttribute(){
		setType(ScimTypeDefinition.DataType.Complex.getType());
	}
	
	ScimComplexAttribute(ScimComplexAttribute schema){
		super(schema);
		setType(ScimTypeDefinition.DataType.Complex.getType());
		setSubAttributes(schema.getSubAttributes());
	}
	
	public List<String> canonicalValues;
	public List<String> referenceTypes;
	
	public List<ScimResourceAttribute> subAttributes;
	public void addAttribute(ScimResourceAttribute attribute) {
		if(this.subAttributes == null) {
			this.subAttributes = new ArrayList<ScimResourceAttribute>();
		}
		if (attribute instanceof ScimSimpleAttribute) {
			ScimSimpleAttribute meta_attribute = new ScimSimpleAttribute(attribute);
			
			subAttributes.add(meta_attribute);
		}
	}
}

