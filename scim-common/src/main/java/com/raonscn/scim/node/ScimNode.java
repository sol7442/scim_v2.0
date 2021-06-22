package com.raonscn.scim.node;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public abstract class ScimNode {
	
	String id;
	String name;
	ScimNodeType type;
	ScimNode 	parent;
	List<ScimNode> children;
	
	public ScimNode() {}
	public ScimNode(String name) {
		this.name = name;
	}
	public ScimNode(String name, ScimNodeType type) {
		this.name = name;
		this.type = type;
	}
	public void addChild(ScimNode node) {
		if(this.children == null) {
			this.children = new ArrayList<ScimNode>();
		}
		this.children.add(node);
	}
}
