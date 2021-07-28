package com.raonsnc.scim.service;

import javax.inject.Inject;

import org.glassfish.hk2.api.IterableProvider;

public class ScimServiceFactory {
	@Inject
	IterableProvider<ScimService> services;
	
	public ScimService create(String name, String type) {
		ScimService service = services.named(name).get();
		
		
		
		
		
		
		
		return service;
	}
}
