package com.raonsnc.scim.service.resource;

import java.util.List;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.filter.ScimFilter;

public interface ScimResourceSearchService extends ScimResourceService {
	public List<ScimEntity> search(ScimFilter filter) throws ScimException;
}
