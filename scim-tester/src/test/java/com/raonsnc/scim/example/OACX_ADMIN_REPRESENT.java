package com.raonsnc.scim.example;

import java.util.HashMap;
import com.raonsnc.scim.entity.ScimEntity;

public class OACX_ADMIN_REPRESENT extends HashMap<String, Object> implements ScimEntity  {
	private static final long serialVersionUID = -531648014588884621L;
  
    // 알고리즘 블록암호  ;
    public static final String SEED = "seed";
    
    // 최근수정일자  ;
    public static final String UPDATE = "update";
    
    // 부서명  ;
    public static final String DEPT = "dept";
    
    // 최근 수정자  ;
    public static final String UPDATE_USER = "update_user";
    
    // 전화번호  ;
    public static final String PHONE = "phone";
    
    // 비밀번호(HASH)  ;
    public static final String PASSWD = "passwd";
    
    // 권한코드  ;
    public static final String AUTHORITY = "authority";
    
    // 사용자명  ;
    public static final String NAME = "name";
    
    // 비밀번호 변경일자  ;
    public static final String PASSWD_CHANGE = "passwd_change";
    
    // 최초등록일자  ;
    public static final String CREATE = "create";
    
    // 최초 등록자  ;
    public static final String CREATE_USER = "create_user";
    
    // 로그인 아이디  ;
    public static final String ID = "id";
    
    // 상태코드  ;
    public static final String STATE = "state";
    
}
