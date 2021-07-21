package com.raonsnc.scim.entity;


import com.raonsnc.scim.ScimException;

public interface ScimSimpleTransfer extends ScimTransfer {
	public void transOut(ScimEntity entity) throws ScimException;
	public ScimEntity transIn() throws ScimException;
}
