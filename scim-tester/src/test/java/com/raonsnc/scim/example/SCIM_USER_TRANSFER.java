package com.raonsnc.scim.example;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimSimpleTransfer;
import java.util.HashMap;
import com.raonsnc.scim.entity.ScimMeta;
import com.raonsnc.scim.entity.ScimEntity;

public class SCIM_USER_TRANSFER extends HashMap<String, Object> implements ScimSimpleTransfer  {
	private static final long serialVersionUID = -7285044779332055514L;
  
    //   ;
    public static final String PHON = "phon";
    
    // resource meta  ;
    public static final String META = "meta";
    
    //   ;
    public static final String NAME = "name";
    
    //   ;
    public static final String UPDATE = "update";
    
    //   ;
    public static final String JOIN = "join";
    
    //   ;
    public static final String ID = "id";
    
    //   ;
    public static final String RESIGN = "resign";
    
  
	@Override
	public void transOut(ScimEntity entity) throws ScimException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScimEntity transIn() throws ScimException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScimMeta getMeta() throws ScimException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMeta(ScimEntity entity) throws ScimException {
		// TODO Auto-generated method stub
		
	}  
}
