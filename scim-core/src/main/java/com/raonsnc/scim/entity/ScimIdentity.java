package com.raonsnc.scim.entity;

import java.util.Map;

import com.raonsnc.scim.ScimException;
public interface ScimIdentity extends Map<String,Object>{
	public void parse(String identity) throws ScimException;
	public void parse(ScimEntity entity) throws ScimException;
	public String bind() throws ScimException;
}
