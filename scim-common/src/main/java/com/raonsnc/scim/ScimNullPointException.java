package com.raonsnc.scim;

public class ScimNullPointException extends ScimException {
	private static final long serialVersionUID = 2116338854473516941L;

	public ScimNullPointException(String obj_name) {
		super(new StringBuffer().append(obj_name).append(" : ").append("IS NULL").toString());
	}

}
