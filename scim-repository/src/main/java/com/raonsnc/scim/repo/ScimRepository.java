package com.raonsnc.scim.repo;

import java.util.List;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;

public interface ScimRepository {
	//public boolean initialize(ScimConfig data_source, ScimConfig apater_config, ScimConfig connection);
	
	public String getName();
	public void distroy();
	public boolean isConnected() ;
	public boolean testConnect() throws ScimException;
	public List<String> getSchemaList() throws ScimException;
	public List<ScimResourceSchema> getResourceSchemaList(String schema) throws ScimException;
	public List<ScimAttributeSchema> getAttributeSchema(ScimResourceSchema resource) throws ScimException;
	public void findAttributeSchema(ScimResourceSchema resource) throws ScimException;
	
//	public void c();
//	public void r();
//	public void u();
//	public void d();
//	public void s();
//	public void q();
}

