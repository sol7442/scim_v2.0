package com.raonsnc.scim.entity;

import java.util.HashMap;
import java.util.List;

import com.raonsnc.scim.ScimException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ScimResource extends HashMap<String,Object> implements ScimEntity {
	private static final long serialVersionUID = 3062894719658045535L;

	List<String> schemas;
	ScimMeta meta;

	public abstract ScimEntity getEntity(String urn) throws ScimException;
	public abstract void setEntity(String urn, ScimEntity entity) throws ScimException;
	
	
}
