package com.raonsnc.scim.repo.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.entity.ScimIdentity;
import com.raonsnc.scim.repo.DataStorage;
import com.raonsnc.scim.repo.DataStorageService;
import com.raonsnc.scim.repo.ScimEntityAttribute;
import com.raonsnc.scim.repo.ScimEntitySchema;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.schema.ScimTypeDefinition;
import com.raonsnc.scim.service.resource.ScimResourceAllService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ScimRepositoryServiceImpl implements ScimResourceAllService, DataStorageService {
	protected DataSource 	source;
	protected DataStorage 	storage;
	ScimEntitySchema 	schema;
	String entityClassName;

	public ScimRepositoryServiceImpl(DataSource source, DataStorage storage) {
		this.source 	= source;
		this.storage 	= storage;
	}
	public void setResourcerSchema(ScimResourceSchema schema) {
		this.schema = (ScimEntitySchema) schema;
	}
	
	private ScimEntity makeEntityObject()	throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ScimEntity entity_obj;
		entity_obj =   (ScimEntity) Class.forName(entityClassName).newInstance();
		return entity_obj;
	}
	
	private ScimIdentity makeIdentityObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (ScimIdentity) Class.forName(entityClassName).newInstance();
	}
	
	@Override
	public void create(ScimEntity entity) throws ScimException {
		Connection connection = null;
		
		try {
			connection = source.getConnection();
			
			create(connection, entity);
			
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			throw new ScimException(e.getMessage(), e);
		} 
		finally {
			log.debug("{} : {} ", entity);
			close(connection);
		}
	}
	
	private void create(Connection connection, ScimEntity entity) throws  ScimException, SQLException {
		ScimIdentity identity;
		
		PreparedStatement statement = null;
		try {
			identity = makeIdentityObject();
			identity.parse(entity);
			
			ScimEntity alreay_entity = read(connection, identity.bind());
			if(alreay_entity != null) {
				throw new ScimException(ScimTypeDefinition.Status.BadRequest_uniqueness);
			}
			
			StringBuffer query_buffer = makeCreateQuery(entity);

			statement = connection.prepareStatement(query_buffer.toString());
			int param_index = 1;
			for (Entry<String, Object> entry : entity.entrySet()) {
				ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
				statement.setObject(param_index, entry.getValue(), attribute.getTypeCode());
				param_index++;
			}
			
			statement.executeUpdate();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ScimException(e.getMessage(), e);
		} finally {
			close(statement,null);
		}
	}
	private StringBuffer makeCreateQuery(ScimEntity entity) throws ScimException {
		StringBuffer query_buffer = new StringBuffer();
		StringBuffer colums_buffer = new StringBuffer();
		StringBuffer values_buffer = new StringBuffer();
		
		try {
			query_buffer.append("INSERT INTO").append(" ").append(schema.getStorageName()).append(" ");
			colums_buffer.append("(");
			values_buffer.append("(");

			int append_count = 0;
			for (Entry<String, Object> entry : entity.entrySet()) {
				if (append_count > 0) {
					colums_buffer.append(",");
					values_buffer.append(",");
				}

				colums_buffer.append(entry.getKey());
				values_buffer.append("?");

				append_count++;
			}
			values_buffer.append(")");
			colums_buffer.append(")");

			query_buffer.append(colums_buffer.toString()).append(" VALUES ").append(values_buffer.toString());
			
		}catch (Exception e) {
			throw new ScimException(e.getMessage(), e);
		}
		
		log.debug("query : {}", query_buffer.toString());
		return query_buffer;
	}

	
	@Override
	public ScimEntity read(String id) throws ScimException {
		Connection connection = null;
		ScimEntity   read_entity= null;
		try {
			connection = source.getConnection();
			read_entity = read(connection, id);
		} catch (SQLException e) {
			rollback(connection);
			throw new ScimException(e.getMessage(), e);
		} finally {
			log.debug("{} : {}", id ,read_entity);
			close(connection);
		}
		return read_entity;
	}
	private ScimEntity read(Connection connection, String id) throws  ScimException , SQLException {
		ScimEntity read_entity = null;
		
		PreparedStatement statement = null;
		ResultSet result_set = null;
		try {
			ScimIdentity identity  = makeIdentityObject();
			identity.parse(id);
			String sql_query = makeReadQuery(identity);
			
			statement = connection.prepareStatement(sql_query);
			
			int param_index = 1;
			for (Entry<String, Object> entry : identity.entrySet()) {
				ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
				
				statement.setObject(param_index, entry.getValue(), attribute.getTypeCode());
				param_index++;
			}
			
			result_set = statement.executeQuery();
			boolean result = result_set.next();
			if(result) {
				read_entity = readResultSet(result_set, result_set.getMetaData());
			}
		} catch (InstantiationException |IllegalAccessException | ClassNotFoundException e) {
			throw new ScimException(e.getMessage(), e);
		} catch (SQLException e) {
			throw e;
		} finally {
			close(statement,result_set);
		}
		
		return read_entity;
	}
	private ScimEntity readResultSet(ResultSet result_set, ResultSetMetaData meta_data) throws ScimException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ScimEntity read_entity = makeEntityObject();
		try {
			for(int i=1; i<=meta_data.getColumnCount(); i++) {
				String column_name = meta_data.getColumnName(i);
				ScimEntityAttribute attribute = schema .findAttributeByColumn(column_name);
				if(attribute != null) {
					read_entity.put(attribute.getName(),result_set.getObject(column_name) );
				}else {
					log.trace("ignore value - {}:{}",column_name, result_set.getObject(column_name));
				}
			}	
		}catch (Exception e) {
			throw new ScimException(e.getMessage(),e);
		}
		
		return read_entity;
	}

	private String makeReadQuery(ScimIdentity identity) throws ScimException {
		StringBuffer query_buffer = new StringBuffer();
		try {
			query_buffer.append("SELECT * FROM ").append(schema.getStorageName());
			query_buffer.append(" WHERE ");
			
			int append_count = 0;
			for (Entry<String, Object> entry : identity.entrySet()) {
				if (append_count > 0) {
					query_buffer.append(" and ");
				}
				
				ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
				query_buffer.append(attribute.getColumnName()).append("=");
				query_buffer.append("?");
			}
		}catch (Exception e) {
			throw new ScimException(e.getMessage(), e);
		}
		log.debug("query : {}", query_buffer.toString());
		return query_buffer.toString();
	}

	@Override
	public ScimEntity update(ScimEntity entity) throws ScimException {
		ScimEntity old_entity = null;
		Connection connection = null;
		try {
			connection = source.getConnection();
			
			old_entity = update(connection, entity);
			
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			throw new ScimException(e.getMessage(), e);
		} 
		finally {
			log.info("{}->{}", old_entity, entity);
			close(connection);
		}
		return old_entity;
	}
	private ScimEntity update(Connection connection, ScimEntity entity) throws ScimException , SQLException{
		ScimEntity old_entity = null;
		ScimIdentity identity = null;
		PreparedStatement statement = null;
		try {
			identity = makeIdentityObject();
			identity.parse(entity);
			
			old_entity = read(identity.bind());
			if(old_entity == null) {
				throw new ScimException(ScimTypeDefinition.Status.BadRequest_noTarget);
			}
			
			StringBuffer query_buffer = makeUpdateQuery(identity, entity);
			
			statement = connection.prepareStatement(query_buffer.toString());
			int param_index = 1;
			for (Entry<String, Object> entry : entity.entrySet()) {
				ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
				
				statement.setObject(param_index, entry.getValue(), attribute.getTypeCode());
				param_index++;
			}
			
			for (Entry<String, Object> entry : identity.entrySet()) {
				ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
				
				statement.setObject(param_index, entry.getValue(), attribute.getTypeCode());
				param_index++;
			}
			
			statement.executeUpdate();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ScimException( e.getMessage(), e);
		} finally {
			close(statement,null);
		}
		
		return old_entity;
	}
	private StringBuffer makeUpdateQuery(ScimIdentity identity, ScimEntity entity) {
		StringBuffer query_buffer = new StringBuffer();
		query_buffer.append("UPDATE ").append(schema.getStorageName()).append(" ");
		query_buffer.append("SET ");
		
		int append_count = 0;
		for (Entry<String, Object> entry : entity.entrySet()) {
			if (append_count > 0) {
				query_buffer.append(",");
			}
			
			ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
			query_buffer.append(attribute.getColumnName());
			query_buffer.append("=?");

			append_count++;
		}
		query_buffer.append(" WHERE ");

		append_count = 0;
		for (Entry<String, Object> entry : identity.entrySet()) {
			if (append_count > 0) {
				query_buffer.append(" and ");
			}
			
			ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
			query_buffer.append(attribute.getColumnName()).append("=");
			query_buffer.append("?");
		}
		
		log.debug("query : {}", query_buffer.toString());
		return query_buffer;
	}
	
	@Override
	public  boolean delete(String id) throws ScimException {
		Connection connection = null;
		boolean result = false;
		try {
			connection = source.getConnection();
			
			result = delete(connection, id);
			
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			throw new ScimException(e.getMessage(), e);
		} 
		finally {
			log.info("{}->{}", id, null);
			close(connection);
		}
		return result;
	}

	private boolean delete(Connection connection, String id) throws ScimException , SQLException{
		ScimEntity old_entity = null;
		ScimIdentity identity = null;
		PreparedStatement statement = null;
		int result = 0;
		try {
			identity = makeIdentityObject();
			identity.parse(id);
			
			old_entity = read(identity.bind());
			if(old_entity == null) {
				throw new ScimException(ScimTypeDefinition.Status.BadRequest_noTarget);
			}
			
			StringBuffer query_buffer = makeDeleteQuery(identity);
			
			statement = connection.prepareStatement(query_buffer.toString());
			
			int param_index = 1;
			for (Entry<String, Object> entry : identity.entrySet()) {
				ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
				
				statement.setObject(param_index, entry.getValue(), attribute.getTypeCode());
				param_index++;
			}
			
			result = statement.executeUpdate();
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ScimException( e.getMessage(), e);
		} finally {
			close(statement, null);
		}
		
		return result == 1 ? true:false;
	}
	private StringBuffer makeDeleteQuery(ScimIdentity identity) {
		StringBuffer query_buffer = new StringBuffer();
		query_buffer.append("DELETE FROM ").append(schema.getStorageName()).append(" ");
		query_buffer.append("WHERE ");

		int append_count = 0;
		for (Entry<String, Object> entry : identity.entrySet()) {
			if (append_count > 0) {
				query_buffer.append(" and ");
			}
			
			ScimEntityAttribute attribute = schema.getAttribute(entry.getKey());
			query_buffer.append(attribute.getColumnName()).append("=");
			query_buffer.append("?");
		}
		
		log.debug("query : {}", query_buffer.toString());
		return query_buffer;
	}
	
	private void rollback(Connection connection) {
		try {
			if (connection != null)
				connection.rollback();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private void close(PreparedStatement statement, ResultSet result_set) {
		try {
			if (statement != null)
				statement.close();

			if (result_set != null)
				result_set.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	private void close(Connection connection) {
		try {
			if (connection != null)
				connection.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
