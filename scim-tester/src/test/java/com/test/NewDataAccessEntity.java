package com.test;

import java.util.HashMap;
import java.lang.String;
import com.raonsnc.scim.entity.ScimEntity;

public class NewDataAccessEntity extends HashMap<String,Object> implements ScimEntity {
	private static final long serialVersionUID = 6727303427950069685L;
  
    // 전화번호  ;
    public static final String PHONE_NO = "phone_no";
    public String getPhoneNo() {
    	return (String) get(PHONE_NO);
    } 
    public void setPhoneNo(String value){
    	put(PHONE_NO,value);
    }
    
    // 최초 등록자  ;
    public static final String CREATE_USER_ID = "create_user_id";
    public String getCreateUserId() {
    	return (String) get(CREATE_USER_ID);
    } 
    public void setCreateUserId(String value){
    	put(CREATE_USER_ID,value);
    }
    
    // 알고리즘 블록암호  ;
    public static final String SEED = "seed";
    public String getSeed() {
    	return (String) get(SEED);
    } 
    public void setSeed(String value){
    	put(SEED,value);
    }
    
    // 부서명  ;
    public static final String DEPT_NAME = "dept_name";
    public String getDeptName() {
    	return (String) get(DEPT_NAME);
    } 
    public void setDeptName(String value){
    	put(DEPT_NAME,value);
    }
    
    // 비밀번호 변경일자  ;
    public static final String PASSWD_CHANGE_DATE = "passwd_change_date";
    public String getPasswdChangeDate() {
    	return (String) get(PASSWD_CHANGE_DATE);
    } 
    public void setPasswdChangeDate(String value){
    	put(PASSWD_CHANGE_DATE,value);
    }
    
    // 최근수정일자  ;
    public static final String UPDATE_DATE = "update_date";
    public String getUpdateDate() {
    	return (String) get(UPDATE_DATE);
    } 
    public void setUpdateDate(String value){
    	put(UPDATE_DATE,value);
    }
    
    // 최근 수정자  ;
    public static final String UPDATE_USER_ID = "update_user_id";
    public String getUpdateUserId() {
    	return (String) get(UPDATE_USER_ID);
    } 
    public void setUpdateUserId(String value){
    	put(UPDATE_USER_ID,value);
    }
    
    // 비밀번호(HASH)  ;
    public static final String PASSWD = "passwd";
    public String getPasswd() {
    	return (String) get(PASSWD);
    } 
    public void setPasswd(String value){
    	put(PASSWD,value);
    }
    
    // 사용자명  ;
    public static final String NAME = "name";
    public String getName() {
    	return (String) get(NAME);
    } 
    public void setName(String value){
    	put(NAME,value);
    }
    
    // 로그인 아이디  ;
    public static final String ID = "id";
    public String getId() {
    	return (String) get(ID);
    } 
    public void setId(String value){
    	put(ID,value);
    }
    
    // 상태코드  ;
    public static final String STATE_CODE = "state_code";
    public String getStateCode() {
    	return (String) get(STATE_CODE);
    } 
    public void setStateCode(String value){
    	put(STATE_CODE,value);
    }
    
    // 최초등록일자  ;
    public static final String CREATE_DATE = "create_date";
    public String getCreateDate() {
    	return (String) get(CREATE_DATE);
    } 
    public void setCreateDate(String value){
    	put(CREATE_DATE,value);
    }
    
    // 권한코드  ;
    public static final String AUTHORITY_CODE = "authority_code";
    public String getAuthorityCode() {
    	return (String) get(AUTHORITY_CODE);
    } 
    public void setAuthorityCode(String value){
    	put(AUTHORITY_CODE,value);
    }
    
}
