package com.raonscn.scim.node;

public class ScimServerNode extends ScimNode {
	public ScimServerNode(String name) {
		this.name = name;
		this.type = ScimNodeType.server;
	}
}
