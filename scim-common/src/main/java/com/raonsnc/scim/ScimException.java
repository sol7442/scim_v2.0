package com.raonsnc.scim;

public class ScimException extends Exception {
	public ScimException(String msg) {
		super(msg);
	}
	public ScimException(Exception e) {
		super(e.getMessage(),e);
	}
	public ScimException(String msg, Exception e) {
		super(msg,e);
	}
	private static final long serialVersionUID = 3696014482152828436L;

}
