package com.raonsnc.scim.repo;


import com.raonsnc.scim.ScimException;

public interface DataStorage extends DataStorageService{
	public boolean testConnect()throws ScimException;
}
