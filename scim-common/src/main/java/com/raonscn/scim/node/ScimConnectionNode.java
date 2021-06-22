package com.raonscn.scim.node;

public class ScimConnectionNode extends ScimNode {
	public ScimConnectionNode(String name) {
		this.name = name;
		this.type = ScimNodeType.connection;
	}
}
