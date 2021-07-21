package com.raonsnc.scim.service;

import java.util.List;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;

public interface ScimEntitySearchService extends ScimEntityService {
	public List<ScimEntity> search(ScimFilter filter) throws ScimException;
}
