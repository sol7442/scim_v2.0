package com.raonsnc.scim.agent;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShutdownHook extends Thread {
	ScimAgentMain daemon = null;
	public void attach(ScimAgentMain daemon) {
		this.daemon = daemon;
		Runtime.getRuntime().addShutdownHook(this);
	}
	public void run() {
		try {
			daemon.destroy();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			log.info("{}", new Date());
		}
	}
}
