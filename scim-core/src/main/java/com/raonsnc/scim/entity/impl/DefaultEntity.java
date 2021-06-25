package com.raonsnc.scim.entity.impl;

import java.util.HashMap;

import com.raonsnc.scim.entity.ScimEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DefaultEntity extends HashMap<String, Object> implements ScimEntity {
	private static final long serialVersionUID = -4110925840590183428L;
	
}
