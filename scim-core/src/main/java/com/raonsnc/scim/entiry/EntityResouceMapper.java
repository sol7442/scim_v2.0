package com.raonsnc.scim.entiry;

import com.raonsnc.scim.ScimException;

public interface EntityResouceMapper<E extends ScimEntity, R extends ScimResource> {
	public E toEntity()	throws ScimException;
	public R toResouce()throws ScimException;
}
