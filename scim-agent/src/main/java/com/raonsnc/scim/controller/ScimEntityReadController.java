package com.raonsnc.scim.controller;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import lombok.extern.slf4j.Slf4j;


@Path("/v2.0/{entity}/{id}")
@Slf4j
public class ScimEntityReadController {
	@GET()
	public Response request(@Context HttpHeaders headers,
			@PathParam("entity") String entity,
			@PathParam("id") String id) {
		try {
			log.info("{} : {}", entity,id);
			for (Entry<String, List<String>> header : headers.getRequestHeaders().entrySet()) {
				for(String value : header.getValue()) {
					log.info(" - {} : {}", header.getKey(), value);					
				}
			}
			;
			
			log.info("{} : {}", entity,id);
			
			
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return Response.status(Status.OK).entity(id).build();
	}
}
