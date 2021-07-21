package com.raonsnc.scim.agent;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.internal.spi.ServletContainerProvider;

import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.config.ScimServiceConfig;
import com.raonsnc.scim.repo.ScimRepositoryAdapter;
import com.raonsnc.scim.repo.ScimRepositoryService;
import com.raonsnc.scim.repo.ScimStorage;
import com.raonsnc.scim.repo.ScimStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.rdb.ScimDataSourceBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimServiceProvider implements ServletContainerProvider{

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
		String configFile = (String) resourceConfig.getProperty("scim.service.config");
		try {
			ScimServiceConfig config = ScimServiceConfig.load(configFile);
			

			
			resourceConfig.register(new ScimServiceBinder(config));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		log.debug("-----");
	}

}
