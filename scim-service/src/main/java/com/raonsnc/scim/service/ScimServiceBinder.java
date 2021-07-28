package com.raonsnc.scim.service;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimServiceBinder extends AbstractBinder{
	ScimServiceConfig config;
	public ScimServiceBinder(ScimServiceConfig config) {
		this.config = config;
	}
	
	@Override
	protected void configure() {
		try {
			bind(config).to(ScimServiceConfig.class);
			
			bind(ScimServiceManager.class).to(ScimServiceManager.class).in(Singleton.class);
			bind(ScimServiceFactory.class).to(ScimServiceFactory.class).in(Singleton.class);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
