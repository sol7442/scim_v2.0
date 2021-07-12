package com.test;

import com.raonsnc.scim.ScimException;
import com.raonscn.scim.util.DataConverter;
import java.util.StringTokenizer;
import java.util.HashMap;
import com.raonsnc.scim.entity.ScimIdentity;
import com.raonsnc.scim.entity.ScimEntity;

public class OacxAdminIdnetity extends HashMap<String, Object> implements ScimIdentity  {
	private static final long serialVersionUID = -7814805625903820940L;
  
    // 로그인 아이디  ;
    public static final String ID = "id";
    
  
  	@Override
	public void parse(String identity) throws ScimException{
  		put("id", DataConverter.toString(identity));
	}
	@Override
	public void parse(ScimEntity entity) throws ScimException {
   		put("id",entity.get("id")); 
	}
	
	@Override
	public String bind() throws ScimException{
  		return DataConverter.toString(get("id"));
	}
}
