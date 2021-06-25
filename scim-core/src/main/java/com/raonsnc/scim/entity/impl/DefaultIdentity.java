package com.raonsnc.scim.entity.impl;

import java.util.HashMap;
import java.util.Iterator;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.entity.ScimIdentity;
import com.raonsnc.scim.schema.ScimIdentitySchema;

public class DefaultIdentity extends HashMap<String, Object> implements ScimIdentity {
	private static final long serialVersionUID = 8127594152144552837L;

	ScimIdentitySchema schema;
	
	@Override
	public void setScimIdentitySchema(ScimIdentitySchema schemna) {
		this.schema = schemna;
	}

	@Override
	public void parse(ScimEntity entity) throws ScimException {
		
	}

	@Override
	public void parse(String identity) throws ScimException {
		try {
			
		}catch (Exception e) {
			throw new ScimException( e.getMessage(), e);
		} 
	}

	
	
	
//	ScimEntity entity;
//	ScimIdentitySchema schema;
//	
//	public DefaultIdentity(ScimEntity entity,ScimIdentitySchema schema) {
//		this.entity = entity;
//		this.schema = schema;
//	}
//	
//	@Override
//	public void setId(String id) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String getId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
//	@Override
//	public String toIdentity() {
//		StringBuffer buffer = new StringBuffer();
//		int append_count = 0;
//		for (Entry<String,Object> entry : entrySet()) {
//			if(append_count > 0) {
//				buffer.append("&");
//			}
//			buffer.append(entry.getKey()).append("=").append(entry.getValue());
//			
//			append_count++;
//		}
//		
//		return buffer.toString();
//	}

//	@Override
//	public void fromString(String identity) throws ScimException{
//		StringTokenizer entry_token = new StringTokenizer(identity,"&");
//		while(entry_token.hasMoreTokens()) {
//			String entry_string = entry_token.nextToken();
//			
//			String[] entry = entry_string.split("=");
//			if(entry.length != 2) {
//				throw new ScimException("IDENTITY PARSER ERROR : " + entry_string);
//			}
//			
//			if( entry[1].equals("null")) {
//				put(entry[0], null);
//			}else {
//				put(entry[0], entry[1]);
//			}
//		}
//	}
//
//	public boolean equals(ScimIdentity identity) {
//		if(identity == null) return false;
//
//		if(size() != identity.size()) return false;
//		
//		for (Entry<String,Object> entry : entrySet()) {
//			Object entry_obj 	= entry.getValue();
//			Object identity_obj = identity.get(entry.getKey());
//			
//			if( compare_value(entry_obj, identity_obj) == false) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private boolean compare_value(Object entry_obj, Object identity_obj) {
//		if(entry_obj == null && identity_obj == null) {
//			return true;
//		}
//		else if(entry_obj != null && identity_obj != null) {
//			return entry_obj.equals(identity_obj);
//		}else {
//			return false;
//		}
//	}


}
