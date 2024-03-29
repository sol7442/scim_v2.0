package com.raonsnc.scim.repo.conf;

import com.raonscn.scim.config.ScimConfiguration;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class DataSourceConfig extends ScimConfiguration {
	String jdbcUrl;
	String user;
	String passwd;
}

