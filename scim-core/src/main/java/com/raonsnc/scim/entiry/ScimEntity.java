package com.raonsnc.scim.entiry;

import java.util.Map;

public interface ScimEntity extends Map<String,Object> {
	public String getId();
	public void setId(String id);
}
