package com.raonsnc.scim.repo.conf;

import com.raonscn.scim.config.ScimConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class DataSourceConfig extends ScimConfig {
	String jdbcUrl;
	String user;
	String passwd;
}

