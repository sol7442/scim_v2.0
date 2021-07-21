package com.raonsnc.scim.repo.conf;

import com.raonscn.scim.config.ScimConfiguration;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StorageConfig extends ScimConfiguration {
	String driver;
	String validate;
	String schema;
	String table;
	String view;
	String synonym;
	String columOfTable;
	String columnOfView;
	String columnOfSynonym;
}
