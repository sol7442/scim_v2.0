package com.raonsnc.scim.agent;

import java.util.Date;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.raonsnc.scim.config.ScimAgentConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimAgentMain {
	
	static ScimAgentMain instance;
	
	Server jettyServer = null;
	public static void main(String[] args) {
		start();
	}
	public static void start() {
		log.info("============================================================================:{}", new Date());
		instance = new ScimAgentMain();
		instance.printHeader();
		instance.initialize(System.getProperty("scim.config"));
		instance.excute();
		log.info("============================================================================:{}", new Date());
	}
	private void printHeader() {
		log.info(" - java.home                  :{}", System.getProperty("java.home"));
		log.info(" - java.version               :{}", System.getProperty("java.version"));
		log.info(" - java.vm.name               :{}", System.getProperty("java.vm.name"));
		log.info(" - java.vm.vendor             :{}", System.getProperty("java.vm.vendor"));
		
		log.info(" - java.library.path          :{}", System.getProperty("java.library.path"));
		log.info(" - java.class.version         :{}", System.getProperty("java.class.version"));
		log.info(" - java.specification.version :{}", System.getProperty("java.specification.version"));
		
		log.info(" - os.name                    :{}", System.getProperty("os.name"));
		log.info(" - os.arch                    :{}", System.getProperty("os.arch"));
		log.info(" - os.version                 :{}", System.getProperty("os.version"));
		
		log.info(" - user.dir                   :{}", System.getProperty("user.dir"));
		log.info(" - user.home                  :{}", System.getProperty("user.home"));
		log.info(" - user.country               :{}", System.getProperty("user.country"));
		log.info(" - user.timezone              :{}", System.getProperty("user.timezone"));
	}
	public static void stop() {
		instance.destroy();
	}
	public void initialize(String configFile) {
		try {
			ScimAgentConfig config = ScimAgentConfig.load(configFile);
			log.debug("{}", config);
			
			jettyServer = new Server();
			ServerConnector connector = new ServerConnector(jettyServer);
			connector.setIdleTimeout(config.getIdleTime());
			connector.setPort(config.getPort());
			
			jettyServer.setStopAtShutdown(true);
			jettyServer.setConnectors(new Connector[] {connector});

			ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
			ctx.setContextPath("/");
			jettyServer.setHandler(ctx);
			
			ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/scim/*");
			serHol.setInitOrder(1);
			serHol.setInitParameter("jersey.config.server.provider.packages","com.raonsnc.scim.controller");
			serHol.setInitParameter("javax.ws.rs.Application", "com.raonsnc.scim.service.ScimApplication");
			
			serHol.setInitParameter("scim.service.config"	,config.getPaths().get("service"));
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void excute() {
		try {
			jettyServer.start();	
		}catch (Exception e) {
			log.error(e.getMessage());
		}finally {
		}
	}
	public void restart() {
		
	}
	public void destroy() {
		try {
			jettyServer.stop();	
		}catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			log.info("======================================:{}", new Date());
		}
	}
	
	
}
