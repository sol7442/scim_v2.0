package com.raonsnc.scim.repo.conf;

import com.raonscn.scim.config.ScimConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConnectionPoolConfig extends ScimConfig{
	int maxSize;
	int minSize;
	int idleTime;
}
