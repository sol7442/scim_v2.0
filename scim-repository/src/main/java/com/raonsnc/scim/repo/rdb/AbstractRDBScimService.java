package com.raonsnc.scim.repo.rdb;

import java.lang.reflect.InvocationTargetException;
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
import com.raonsnc.scim.repo.ScimStorage;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimIdentitySchema;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.service.ScimService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRDBScimService implements ScimService {
	protected DataSource 	source;
	protected ScimStorage 	storage;
	ScimResourceSchema 		schema;

	public AbstractRDBScimService(DataSource source, ScimStorage storage) {
		this.source 	= source;
		this.storage 	= storage;
	}
	public void setResourcerSchema(ScimResourceSchema schema) {
		this.schema = schema;
	}
	public void create(ScimEntity entity) throws ScimException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result_set = null;
		int result = 0;
		String identity = null;
		try {

			//identity = entity.getId();

			connection = source.getConnection();

			StringBuffer query_buffer = new StringBuffer();
			StringBuffer colums_buffer = new StringBuffer();
			StringBuffer values_buffer = new StringBuffer();
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

			log.debug("query : {}", query_buffer.toString());
			statement = connection.prepareStatement(query_buffer.toString());
			int param_index = 1;
			for (Entry<String, Object> entry : entity.entrySet()) {
				ScimAttributeSchema attribute = schema.getAttribute(entry.getKey());
				statement.setObject(param_index, entry.getValue(), attribute.getDataType());
				param_index++;
			}

			result = statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			throw new ScimException(e.getMessage(), e);
		} finally {
			log.info("{}({})", identity, result == 1 ? true : false);
			close(connection, statement, result_set);
		}
	}

	
	@Override
	public ScimEntity read(String id) throws ScimException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result_set = null;
		boolean result = false;
		
		ScimIdentity identity 	= null;
		String		 sql_query 	= null;
		ScimEntity   read_entity= null;
		try {
			connection = source.getConnection();

			identity  = make_identity_object(id);
			sql_query = make_read_query(identity);
			
			statement = connection.prepareStatement(sql_query);
			
			set_query_params(statement, identity);

			result_set = statement.executeQuery();
			result = result_set.next();
			if(result) {
				read_entity = read_result_set(result_set, result_set.getMetaData());
			}
		} catch (SQLException e) {
			rollback(connection);
			throw new ScimException(e.getMessage(), e);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ScimException(e.getMessage(), e);
		} finally {
			log.info("({}){}",result, identity );
			close(connection, statement, result_set);
		}
		
		return read_entity;
	}
	private ScimEntity read_result_set(ResultSet result_set, ResultSetMetaData meta_data) throws ScimException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ScimEntity read_entity = make_entity_object();
		try {
			for(int i=1; i<=meta_data.getColumnCount(); i++) {
				String column_name = meta_data.getColumnName(i);
				ScimAttributeSchema attribute = schema .getAttributeByColumn(column_name);
				if(attribute != null) {
					Object object = result_set.getObject(column_name);
					if(object == null) {
						read_entity.put(attribute.getName(),attribute.getDefaultValue());	
					}else {
						read_entity.put(attribute.getName(),object );	
					}
				}else {
					log.trace("ignore value - {}:{}",column_name, result_set.getObject(column_name));
				}
			}	
		}catch (Exception e) {
			throw new ScimException(e.getMessage(),e);
		}
		
		return read_entity;
	}
	private ScimEntity make_entity_object()	throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ScimEntity entity_obj;
		entity_obj =   (ScimEntity) Class.forName(schema.getEntityClassName()).newInstance();
		return entity_obj;
	}
	private void set_query_params(PreparedStatement statement, ScimIdentity identity) throws SQLException {
		int param_index = 1;
		for (Entry<String, Object> entry : identity.entrySet()) {
			ScimAttributeSchema attribute = schema.getAttribute(entry.getKey());
			
			statement.setObject(param_index, entry.getValue(), attribute.getDataType());
			param_index++;
		}
	}
	private String make_read_query(ScimIdentity identity) throws ScimException {
		StringBuffer query_buffer = new StringBuffer();
		try {
			query_buffer.append("SELECT * FROM ").append(schema.getStorageName());
			query_buffer.append(" WHERE ");
			
			int append_count = 0;
			for (Entry<String, Object> entry : identity.entrySet()) {
				if (append_count > 0) {
					query_buffer.append(" and ");
				}
				
				ScimAttributeSchema attribute = schema.getAttribute(entry.getKey());
				query_buffer.append(attribute.getColumnName()).append("=");
				query_buffer.append("?");
			}
		}catch (Exception e) {
			throw new ScimException(e.getMessage(), e);
		}
		log.debug("query : {}", query_buffer.toString());
		return query_buffer.toString();
	}
	private ScimIdentity make_identity_object(String id, ScimEntity entity) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ScimException {
		
		ScimIdentity identity;
		try {
			
			Class<?>[] class_args = new Class[2];
			class_args[0] = ScimEntity.class;
			class_args[1] = ScimIdentitySchema.class;
			
			identity = (ScimIdentity) Class.forName(schema.getIdentityClassName()).getDeclaredConstructor(class_args).newInstance();
			
			if(schema.getIdentitySchema().size() == 1) {
				identity.put(id, identity);
			}else {
				
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return identity;
	}

	@Override
	public void update(ScimEntity entity) throws ScimException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result_set = null;
		int result = 0;
		String identity = null;
		try {
			//ScimResourceSchema resourceSchema = find_resourceSchema_by_entity_class(entity.getClass().getName());
			if (schema == null) {
				new ScimException(
						"Unmanaged Entity - entity schema not found : " + entity.getClass().getCanonicalName());
			}
			ScimIdentity id_obj = (ScimIdentity) Class.forName(schema.getIdentityClassName()).newInstance();
			//id_obj.fromString(entity.getId());
			
			connection = source.getConnection();

			StringBuffer query_buffer = new StringBuffer();
			query_buffer.append("UPDATE ").append(schema.getStorageName()).append(" ");
			query_buffer.append("SET ");
			
			int append_count = 0;
			for (Entry<String, Object> entry : entity.entrySet()) {
				if (append_count > 0) {
					query_buffer.append(",");
				}
				
				ScimAttributeSchema attribute = schema.getAttribute(entry.getKey());
				query_buffer.append(attribute.getColumnName());
				query_buffer.append("=?");

				append_count++;
			}
			query_buffer.append(" WHERE ");
		
			append_count = 0;
			for (Entry<String, Object> entry : id_obj.entrySet()) {
				if (append_count > 0) {
					query_buffer.append(" and ");
				}
				
				ScimAttributeSchema attribute = schema.getAttribute(entry.getKey());
				query_buffer.append(attribute.getColumnName()).append("=");
				query_buffer.append("?");
			}
			
			log.debug("query : {}", query_buffer.toString());
			statement = connection.prepareStatement(query_buffer.toString());
			int param_index = 1;
			for (Entry<String, Object> entry : entity.entrySet()) {
				ScimAttributeSchema attribute = schema.getAttribute(entry.getKey());
				
				log.debug("paramse - {}:{}",param_index, entry.getValue());
				statement.setObject(param_index, entry.getValue(), attribute.getDataType());
				param_index++;
			}
			
			for (Entry<String, Object> entry : id_obj.entrySet()) {
				ScimAttributeSchema attribute = schema.getAttribute(entry.getKey());
				
				log.debug("paramse - {}:{}",param_index, entry.getValue());
				statement.setObject(param_index, entry.getValue(), attribute.getDataType());
				param_index++;
			}
			
			result = statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollback(connection);
			throw new ScimException(e.getMessage(), e);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ScimException(e.getMessage(), e);
		} finally {
			log.info("{}({})", identity, result == 1 ? true : false);
			close(connection, statement, result_set);
		}
	}
	@Override
	public  void delete(String id) throws ScimException {
		
	}
//	private ScimResourceSchema find_resourceSchema_by_entity_class(String entity_class_name) {
//		for (Entry<String, ScimResourceSchema> entry : resources.entrySet()) {
//			if (entry.getValue().getEntityClassName().equals(entity_class_name)) {
//				return entry.getValue();
//			}
//		}
//		return null;
//	}

	private void rollback(Connection connection) {
		try {
			if (connection != null)
				connection.rollback();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void close(Connection connection, PreparedStatement statement, ResultSet result_set) {
		try {
			if (connection != null)
				connection.close();

			if (statement != null)
				statement.close();

			if (result_set != null)
				result_set.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
