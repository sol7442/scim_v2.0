package com.raonsnc.scim.agent;

import java.util.Date;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimAgentMain {
	
	static ScimAgentMain instance;
	
	Server jettyServer = null;
	public static void main(String[] args) {
		start();
	}
	public static void start() {
		instance = new ScimAgentMain();
		
		instance.initialize();
		instance.excute();
	}
	public static void stop() {
		instance.destroy();
	}
	public void initialize() {
		try {
			jettyServer = new Server();
			ServerConnector connector = new ServerConnector(jettyServer);
			connector.setIdleTimeout(10000*2);
			connector.setPort(9999);
			
			jettyServer.setStopAtShutdown(true);
			jettyServer.setConnectors(new Connector[] {connector});

			ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
			ctx.setContextPath("/");
			jettyServer.setHandler(ctx);
			
			ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/scim/*");
			serHol.setInitOrder(1);
			serHol.setInitParameter("jersey.config.server.provider.packages","com.raonsnc.scim.controller");
			serHol.setInitParameter("javax.ws.rs.Application", ScimApplication.class.getName());
			
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
			log.info("======================================:{}", new Date());
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
