package com.raonsnc.scim.repo.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.JdbcType;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.repo.RepositoryLogger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimSessionFactoryBuilder {
	
	DataSource dataSource;
	public SqlSessionFactory build(DataSource data_source) throws ScimException{
		SqlSessionFactory factory = null;
		try {
			
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			Environment environment = new Environment("scim", transactionFactory, data_source);
			
			Configuration mybatis_config = new Configuration();
			
			mybatis_config.setLogImpl(RepositoryLogger.class);
			mybatis_config.setJdbcTypeForNull(JdbcType.NULL);
		
			mybatis_config.setEnvironment(environment);
			mybatis_config.setCallSettersOnNulls(true);
			
			
			//xml_mapper_build(mybatis_config, mapperPath);
			
			factory = new SqlSessionFactoryBuilder().build(mybatis_config);
		}catch (Exception e) {
			throw new ScimException("SessionFactory create failed : ",e);
		}finally {
			log.info("{}",factory);
		}
		
		return factory;
	}
}
