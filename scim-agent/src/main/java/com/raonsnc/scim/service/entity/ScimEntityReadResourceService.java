package com.raonsnc.scim.service.entity;

import javax.inject.Inject;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.service.ScimEntityReadService;
import com.raonsnc.scim.service.ScimEntityService;
import com.raonsnc.scim.service.ScimServiceManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimEntityReadResourceService implements ScimEntityReadService {
	public static final String NAME = "entity.read";
	final String method = "read";
	
	String type;

	@Inject
	ScimServiceManager manager;
	
	public ScimEntityReadResourceService() {
		log.debug("{}",NAME);
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public ScimEntity read(String id) throws ScimException {
		
		ScimEntity entity = null;
		ScimEntityReadService service = (ScimEntityReadService) manager.getEntityService(this.type);
		if(service == null) {
			throw new ScimException("Entity Service Not Found : " + this.type);
		}
		entity = service.read(id);
		if(entity == null) {
			return null;
		}
		
		ScimEntity transfer_entity ;
		
		
		return null;
	}
}
