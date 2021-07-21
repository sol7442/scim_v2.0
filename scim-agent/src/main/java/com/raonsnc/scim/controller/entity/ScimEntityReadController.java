package com.raonsnc.scim.controller.entity;

import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.protocal.ScimStatus;
import com.raonsnc.scim.schema.ScimTypeDefinition;
import com.raonsnc.scim.service.ScimEntityReadService;
import com.raonsnc.scim.service.ScimServiceManager;
import com.raonsnc.scim.service.entity.ScimEntityReadResourceService;
import com.raonsnc.scim.service.entity.ScimServiceFactory;

import lombok.extern.slf4j.Slf4j;


@Path("/v2.0/{type}/{id}")
@Slf4j
public class ScimEntityReadController {
	
	@Inject
	ScimServiceFactory factory;
	
	@GET()
	public Response request(@Context HttpHeaders headers,
			@PathParam("type") String type,
			@PathParam("id") String id)  {
		
		ScimEntity entity = null;
		try {
//			for (Entry<String, List<String>> header : headers.getRequestHeaders().entrySet()) {
//				for(String value : header.getValue()) {
//					log.info(" - {} : {}", header.getKey(), value);					
//				}
//			}
			
			ScimEntityReadService service = (ScimEntityReadService) factory.create(ScimEntityReadResourceService.NAME);
			if(service == null) {
				return Response.status(Status.BAD_REQUEST).entity(ScimTypeDefinition.Status.BadRequest_invalidPath).build();		
			}
			service.setType(type);
			
			entity = service.read(id);
			if(entity == null) {
				return Response.status(Status.BAD_REQUEST).entity(ScimTypeDefinition.Status.BadRequest_noTarget).build();

			}
			
			return Response.status(Status.OK).entity(entity).build();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();		
		}finally {
			log.debug("{}:{}->",type,id,entity);
		}
	}
}
