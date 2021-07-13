package com.raonsnc.scim.example;

import java.util.HashMap;
import com.raonsnc.scim.entity.ScimEntity;

public class OACX_ADMIN_ENTITY extends HashMap<String, Object> implements ScimEntity  {
	private static final long serialVersionUID = -4697570536760720520L;
  
    // 전화번호  ;
    public static final String PHONE_NO = "phone_no";
    
    // 최초 등록자  ;
    public static final String CREATE_USER_ID = "create_user_id";
    
    // 알고리즘 블록암호  ;
    public static final String SEED = "seed";
    
    // 부서명  ;
    public static final String DEPT_NAME = "dept_name";
    
    // 비밀번호 변경일자  ;
    public static final String PASSWD_CHANGE_DATE = "passwd_change_date";
    
    // 최근수정일자  ;
    public static final String UPDATE_DATE = "update_date";
    
    // 최근 수정자  ;
    public static final String UPDATE_USER_ID = "update_user_id";
    
    // 비밀번호(HASH)  ;
    public static final String PASSWD = "passwd";
    
    // 사용자명  ;
    public static final String NAME = "name";
    
    // 로그인 아이디  ;
    public static final String ID = "id";
    
    // 상태코드  ;
    public static final String STATE_CODE = "state_code";
    
    // 최초등록일자  ;
    public static final String CREATE_DATE = "create_date";
    
    // 권한코드  ;
    public static final String AUTHORITY_CODE = "authority_code";
    
}
