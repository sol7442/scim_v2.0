package com.raonsnc.scim.service.entity;

import javax.inject.Inject;

import org.glassfish.hk2.api.IterableProvider;

import com.raonsnc.scim.service.ScimService;

public class ScimServiceFactory {
	@Inject
	IterableProvider<ScimService> services;
	
	public ScimService create(String name) {
		return services.named(name).get();
	}
}
