package com.raonsnc.scim.entity.impl;

import java.util.HashMap;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.entity.ScimIdentity;

public class DefaultIdentity extends HashMap<String, Object> implements ScimIdentity {
	private static final long serialVersionUID = 8127594152144552837L;

	@Override
	public void parse(String identity) throws ScimException {
		put("id",identity);
	}
	@Override
	public void parse(ScimEntity entity) throws ScimException {
		put("id",entity.get("id"));
	}
	
	@Override
	public String bind() throws ScimException {
		return (String) get("id");
	}

	
}
