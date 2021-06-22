package com.raonsnc.scim.repo.rdb;

import javax.sql.DataSource;

import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimDataSourceBuilder {
	public DataSource build(DataSourceConfig config) {
		HikariConfig hikari_config = new HikariConfig();
		try {
			hikari_config.setJdbcUrl( config.getJdbcUrl());
			hikari_config.setUsername(config.getUser());
			hikari_config.setPassword(config.getPasswd());
			
		}catch (Exception e) {
			log.info("{}",hikari_config);
		}finally {
			
		}
		
		return new HikariDataSource(hikari_config);
	}
}
