package com.raonsnc.scim.entity;


import com.raonsnc.scim.ScimException;

public interface ScimMapper {
	public ScimEntity[] bind(ScimEntity entity) throws ScimException;
	public ScimEntity   bind(ScimEntity... entities)throws ScimException;
}
