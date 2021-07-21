package com.raonsnc.scim.example;

import java.util.HashMap;
import com.raonsnc.scim.entity.ScimEntity;

public class SCIM_USER_ENTITY extends HashMap<String, Object> implements ScimEntity  {
	private static final long serialVersionUID = -6548176865339814559L;
  
    //   ;
    public static final String UPDATE_TIME = "update_time";
    
    //   ;
    public static final String PHON = "phon";
    
    //   ;
    public static final String JOIN_DATE = "join_date";
    
    //   ;
    public static final String RESIGN_DATE = "resign_date";
    
    //   ;
    public static final String NAME = "name";
    
    //   ;
    public static final String ID = "id";
    
}
