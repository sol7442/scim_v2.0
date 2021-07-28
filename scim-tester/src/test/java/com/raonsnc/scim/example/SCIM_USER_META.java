package com.raonsnc.scim.example;

import java.util.HashMap;
import com.raonsnc.scim.entity.ScimMeta;

public class SCIM_USER_META extends HashMap<String, Object> implements ScimMeta  {
	private static final long serialVersionUID = 9203565867133717196L;
  
    // created time  ;
    public static final String CREATED = "created";
    
    // modified time  ;
    public static final String LASTMODIFY = "lastModify";
    
    // resource type  ;
    public static final String RESOURCETYPE = "resourceType";
    
    // resource version  ;
    public static final String VERSION = "version";
    
    // resource http url  ;
    public static final String LOCATION = "location";
    
}
