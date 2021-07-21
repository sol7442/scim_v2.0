package com.raonsnc.scim.entity;

import com.raonsnc.scim.ScimException;

public interface ScimTransfer {
//	public void setIdentity(ScimEntity entity)throws ScimException;
//	public ScimIdentity getIdentity()throws ScimException;
	public ScimMeta getMeta() throws ScimException;
	public void setMeta(ScimEntity entity)throws ScimException;
}
