package com.raonsnc.scim.service;

import java.util.Date;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.internal.spi.ServletContainerProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimResourceServiceProvider implements ServletContainerProvider{
	@Override
	public void preInit(ServletContext servletContext, Set<Class<?>> classes) throws ServletException {
		log.debug("preInit");
	}

	@Override
	public void postInit(ServletContext servletContext, Set<Class<?>> classes, Set<String> servletNames) throws ServletException {
		log.debug("preInit");
		
	}

	@Override
	public void onRegister(ServletContext servletContext, Set<String> servletNames) throws ServletException {
		log.debug("onRegister");
	}

	@Override
	public void configure(ResourceConfig resourceConfig) throws ServletException {
		//String servicefigFile = (String) resourceConfig.getProperty("scim.service.config");
		try {
			resourceConfig.register(new ScimResourceServiceBinder());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			log.info("SCIM RESOURCE SERVICE PROVIDER REGISTER : {}", new Date() );
		}
	}

	

}
