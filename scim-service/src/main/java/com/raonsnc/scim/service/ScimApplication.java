package com.raonsnc.scim.service;

import java.io.File;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import com.raonsnc.scim.repo.DataStorageRegistry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimApplication extends ResourceConfig {
	public ScimApplication(){
		register(new ContainerLifecycleListener() {
			@Override
			public void onStartup(Container container) {
				String path = System.getProperty("java.class.path");
				for (String class_path : path.split(File.pathSeparator)) {
					log.info(" - {}", class_path);
				}
				
				DataStorageRegistry.getInstance().initialize();
			}

			@Override
			public void onShutdown(Container container) {
				log.debug("onShutdown -> {}", container.getConfiguration().getApplicationName());
			}	

			@Override
			public void onReload(Container container) {
				log.debug("onReload   -> {}", container.getConfiguration().getApplicationName());
			}
		});	
	}
}
