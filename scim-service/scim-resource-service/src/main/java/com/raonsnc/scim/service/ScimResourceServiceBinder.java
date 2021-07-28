package com.raonsnc.scim.service;


import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.raonsnc.scim.service.resource.ScimResourceReadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimResourceServiceBinder extends AbstractBinder{
	
	
	@Override
	protected void configure() {
		try {
			bind(ScimResourceReadServiceImpl.class).named(ScimResourceReadService.NAME).to(ScimService.class);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
