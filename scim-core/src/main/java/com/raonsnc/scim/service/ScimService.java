package com.raonsnc.scim.service;

import java.util.List;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entiry.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;

public interface ScimService<E extends ScimEntity> {
	public void create(E entity) 		throws ScimException;
	public ScimEntity read(E entity) 	throws ScimException;
	public void update(E entity) 		throws ScimException;
	public void delete(E entity) 		throws ScimException;
	public List<E> search(ScimFilter filter) throws ScimException;
}
