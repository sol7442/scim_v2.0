package com.raonsnc.scim.agent;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.raonsnc.scim.config.ScimServiceConfig;
import com.raonsnc.scim.service.ScimService;
import com.raonsnc.scim.service.ScimServiceManager;
import com.raonsnc.scim.service.entity.ScimEntityReadResourceService;
import com.raonsnc.scim.service.entity.ScimServiceFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimServiceBinder extends AbstractBinder{
	
	ScimServiceConfig config;
	public ScimServiceBinder(ScimServiceConfig config) {
		this.config = config;
	}
	
	@Override
	protected void configure() {
		log.debug("bind service .........");
		try {
			bind(config).to(ScimServiceConfig.class);
			
			bind(ScimServiceManager.class).to(ScimServiceManager.class).in(Singleton.class);
			bind(ScimServiceFactory.class).to(ScimServiceFactory.class).in(Singleton.class);
			
			bind(ScimEntityReadResourceService.class).named(ScimEntityReadResourceService.NAME).to(ScimService.class);
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
