package com.raonsnc.scim.repo;


import com.raonsnc.scim.ScimException;

public interface ScimStorage extends ScimStorageService{
	public boolean testConnect()throws ScimException;
}
