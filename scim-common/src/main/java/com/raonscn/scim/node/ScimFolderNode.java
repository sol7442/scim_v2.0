package com.raonscn.scim.node;

public class ScimFolderNode extends ScimNode {
	public ScimFolderNode(String name) {
		this.name = name;
		this.type = ScimNodeType.folder;
	}
}
