package com.raonsnc.scim.repo.conf;

import com.raonscn.scim.config.ScimConfiguration;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConnectionConfig extends ScimConfiguration{
	int maxSize;
	int minSize;
	int idleTime;
}
