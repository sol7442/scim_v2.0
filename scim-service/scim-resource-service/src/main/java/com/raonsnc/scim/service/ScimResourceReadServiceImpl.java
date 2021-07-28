package com.raonsnc.scim.service;

import javax.inject.Inject;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.service.ScimServiceManager;
import com.raonsnc.scim.service.resource.ScimResourceReadService;
import com.raonsnc.scim.service.resource.ScimResourceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimResourceReadServiceImpl implements ScimResourceReadService {
	final String method = "read";
	
	String type;

	@Inject
	ScimServiceManager manager;
	
	public ScimResourceReadServiceImpl() {
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
		ScimResourceReadService service = (ScimResourceReadService) manager.getEntityService(this.type);
		if(service == null) {
			throw new ScimException("Entity Service Not Found : " + this.type);
		}
		entity = service.read(id);
		if(entity == null) {
			return null;
		}
		log.debug("{}", entity);
		
		ScimEntity transfer_entity ;
		
		
		return null;
	}
}
