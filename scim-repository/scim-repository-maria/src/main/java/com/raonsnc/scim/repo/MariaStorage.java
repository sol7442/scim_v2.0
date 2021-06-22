package com.raonsnc.scim.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.raonscn.scim.node.ScimNodeType;
import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.schema.ScimType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MariaStorage implements ScimStorage {
	DataSource dataSource;
	StorageConfig config;
	
	public MariaStorage(DataSource data_source, StorageConfig config) {
		this.dataSource = data_source;
		this.config = config;
	}

	@Override
	public boolean testConnect() throws ScimException {
		boolean result = false;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			result = connection.createStatement().execute(config.getValidate());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			close_connection(connection,null, null);
		}
		return result;
	}

	@Override
	public List<String> getSchemaList() throws ScimException {
		List<String> schema_list = new ArrayList<String>();
		
		Connection connection = null;
		ResultSet result_set  = null;
		try {
			connection = dataSource.getConnection();
			result_set = connection.createStatement().executeQuery(this.config.getSchema());
			while(result_set.next()) {
				schema_list.add(result_set.getString(1));
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			close_connection(connection,null,result_set);
		}
		
		return schema_list;
	}
	
	
	private void close_connection(Connection connection, PreparedStatement statement, ResultSet result_set) {
		try {
			if(connection != null)
				connection.close();			
			
			if( statement != null)
				statement.close();
			
			if(result_set != null)
				result_set.close();
			
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public List<ScimResourceSchema> getResourceSchemaList(String schema) throws ScimException {
		List<ScimResourceSchema> resource_list = new ArrayList<ScimResourceSchema>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result_set  = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(this.config.getTable());
			statement.setString(1, schema);
			
			result_set = statement.executeQuery();
			while(result_set.next()) {

				ScimType.ResouceType res_type = find_resource_type(result_set);
				
				ScimResourceSchema resource = ScimResourceSchema.builder()
						.name(result_set.getString("TABLE_NAME"))
						.owner(result_set.getString("TABLE_SCHEMA"))
						.type(res_type)
						.attributes(new ArrayList<ScimAttributeSchema>())
						.schemas(new ArrayList<String>())
						.build();
				
				resource_list.add(resource);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			close_connection(connection, statement, result_set );
		}
		
		return resource_list;
	}

	


	private ScimType.DataType find_data_type(ResultSet result_set) throws SQLException {
		ScimType.DataType type = null;
		String data_type = result_set.getString("DATA_TYPE");
		
		switch (data_type.toLowerCase()) {
		case "varchar":
		case "bigint":
			type = ScimType.DataType.String;
			break;
		case "binary":
		case "bit":
		case "blob":
			type = ScimType.DataType.Binary;
			break;
		case "date":
			type = ScimType.DataType.Date;
			break;
		case "datetime":
			type = ScimType.DataType.DateTime;
			break;
		case "decimal":
		case "dobule":
			type = ScimType.DataType.Decimal;
			break;
		default:
			type = ScimType.DataType.String;
			break;
		}
		return type;
	}

	private boolean find_nullable(ResultSet result_set) throws SQLException {
		boolean is_null_able = false;
		String null_able = result_set.getString("IS_NULLABLE");
		if("YES".equals(null_able.toUpperCase())) {
			is_null_able = true;
		}
		return is_null_able;
	}
	
	@Override
	public void findAttributeSchema(ScimResourceSchema resource) throws ScimException {
		resource.setAttributes(getAttributeSchema(resource));
	}

	@Override
	public List<ScimAttributeSchema> getAttributeSchema(ScimResourceSchema resource) throws ScimException {
		List<ScimAttributeSchema> attribute_list = new ArrayList<ScimAttributeSchema>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result_set  = null;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(this.config.getColumOfTable());
			statement.setString(1, resource.getOwner());
			statement.setString(2, resource.getName());
						
			result_set = statement.executeQuery();
			while(result_set.next()) {

				boolean is_null_able = find_nullable(result_set);
				ScimType.DataType data_type = find_data_type(result_set);
				
				String default_value = result_set.getString("COLUMN_DEFAULT");
				if(default_value == null || default_value.toUpperCase().equals("NULL")) {
					default_value = null;
				}
				
				
				ScimAttributeSchema attribute = ScimAttributeSchema.builder()
						.name(result_set.getString("COLUMN_NAME").toLowerCase())
						.defaultValue(default_value)
						.description(result_set.getString("COLUMN_COMMENT"))
						.nullAble(is_null_able)
						.type(data_type)
						.build();
				
				attribute_list.add(attribute);
			}
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally {
			close_connection(connection, statement, result_set );
		}
		
		return attribute_list;
	}

	
	private ScimType.ResouceType find_resource_type(ResultSet result_set) throws SQLException {
		ScimType.ResouceType res_type;
		String table_type = result_set.getString("TABLE_TYPE");
		if("VIEW".equals(table_type.toUpperCase())) {
			res_type = ScimType.ResouceType.VIEW;
		}else if("BASE TABLE".equals(table_type.toUpperCase())) {
			res_type = ScimType.ResouceType.TABLE;
		}else {
			res_type = ScimType.ResouceType.TABLE;
		}
		return res_type;
	}
}
