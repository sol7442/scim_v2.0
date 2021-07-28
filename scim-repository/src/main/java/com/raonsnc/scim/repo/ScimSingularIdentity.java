package com.raonsnc.scim.repo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScimSingularIdentity extends ScimEntityAttribute{
	
	public ScimSingularIdentity() {
		this.mutability = ScimTypeDefinition.Mutability.readOnly.name();
		this.returned   = ScimTypeDefinition.Returned.ALWAYS.value();
		this.uniqueness = ScimTypeDefinition.Uniqueness.server.name();
		this.multiValued= false;
		
		this.name		= "id";
	}
	
	public List<ScimEntityAttribute> subAttributes;
	public void addAttribute(ScimEntityAttribute attribute) {
		if(this.subAttributes == null) {
			this.subAttributes = new ArrayList<ScimEntityAttribute>();
		}
		subAttributes.add(attribute);
	}
	
	public String toJson() {
		new GsonBuilder().setPrettyPrinting().create();
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
