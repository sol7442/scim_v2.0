package com.raonsnc.scim.example;

import com.raonsnc.scim.ScimException;
import com.raonscn.scim.util.DataConverter;
import java.util.HashMap;
import com.raonsnc.scim.entity.ScimIdentity;
import com.raonsnc.scim.entity.ScimEntity;

public class OACX_ADMIN_IDENTITY extends HashMap<String, Object> implements ScimIdentity  {
	private static final long serialVersionUID = -3665425467786443284L;
  
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
