package com.raonsnc.scim.agent;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimApplication extends ResourceConfig {
	public ScimApplication(){
		register(new ContainerLifecycleListener() {
			@Override
			public void onStartup(Container container) {
				log.debug("onStartup -> config file : {}", container.getConfiguration().getProperty("config.file"));
			}

			@Override
			public void onShutdown(Container container) {
				log.debug("onShutdown -> config file : {}", container.getConfiguration().getProperty("config.file"));
			}	

			@Override
			public void onReload(Container container) {
				log.debug("config file : {}", container.getConfiguration().getProperty("config.file"));
			}
		});	
	}
}
