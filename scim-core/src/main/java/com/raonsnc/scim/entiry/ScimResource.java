package com.raonsnc.scim.entiry;

import java.util.List;

public interface ScimResource extends ScimEntity {
	public void addSchema(String schema);
	public List<String> getSchemas();
	
	public ScimMeta getMeta();
	public void setMeta(ScimMeta meta);
}
