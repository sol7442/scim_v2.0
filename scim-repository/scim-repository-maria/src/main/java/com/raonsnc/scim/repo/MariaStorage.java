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

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MariaStorage implements DataStorage {
	DataSource dataSource;
	StorageConfig config;

	Map<String, Integer> metaData = new HashMap<String, Integer>();

	public MariaStorage(DataSource data_source, StorageConfig config) {
		this.dataSource = data_source;
		this.config = config;
	}

	private void close_connection(Connection connection, PreparedStatement statement, ResultSet result_set) {
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

	@Override
	public boolean testConnect() throws ScimException {
		boolean result = false;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			result = connection.createStatement().execute(config.getValidate());
			if (result) {
				ResultSet result_set = connection.getMetaData().getTypeInfo();
				while (result_set.next()) {
					metaData.put(result_set.getString("TYPE_NAME").toLowerCase(), result_set.getInt("DATA_TYPE"));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			close_connection(connection, null, null);
		}
		return result;
	}

	@Override
	public List<String> getSchemaList() throws ScimException {
		List<String> schema_list = new ArrayList<String>();

		Connection connection = null;
		ResultSet result_set = null;
		try {
			connection = dataSource.getConnection();
			result_set = connection.createStatement().executeQuery(this.config.getSchema());
			while (result_set.next()) {
				schema_list.add(result_set.getString(1));
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			close_connection(connection, null, result_set);
		}

		return schema_list;
	}

	@Override
	public List<ScimEntitySchema> getEntitySchemaList(String schema) throws ScimException {
		List<ScimEntitySchema> entity_list = new ArrayList<ScimEntitySchema>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result_set = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(this.config.getTable());
			statement.setString(1, schema);

			result_set = statement.executeQuery();
			while (result_set.next()) {

				ScimTypeDefinition.StorageType storage_type = find_resource_type(result_set);

				ScimEntitySchema entity = new ScimEntitySchema();
				entity.setStorageName(result_set.getString("TABLE_NAME"));
				entity.setStorageSchema(result_set.getString("TABLE_SCHEMA"));
				entity.setStorageType(storage_type);

				entity_list.add(entity);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			close_connection(connection, statement, result_set);
		}

		return entity_list;
	}

	private ScimTypeDefinition.DataType find_scim_type(String sql_data_type) throws SQLException {
		ScimTypeDefinition.DataType scim_data_type = null;
		if (sql_data_type != null) {
			switch (sql_data_type.toLowerCase()) {
			case "varchar":
			case "bigint":
				scim_data_type = ScimTypeDefinition.DataType.String;
				break;
			case "binary":
			case "bit":
			case "blob":
				scim_data_type = ScimTypeDefinition.DataType.Binary;
				break;
			case "date":
				scim_data_type = ScimTypeDefinition.DataType.DateTime;
				break;
			case "datetime":
				scim_data_type = ScimTypeDefinition.DataType.DateTime;
				break;
			case "decimal":
			case "dobule":
				scim_data_type = ScimTypeDefinition.DataType.Decimal;
				break;
			default:
				scim_data_type = ScimTypeDefinition.DataType.String;
				break;
			}
		}
		return scim_data_type;
	}

	@Override
	public ScimEntitySchema getEntitySchema(String owner, String name) throws ScimException {
		ScimEntitySchema entity = new ScimEntitySchema();
//		if (resource instanceof ScimEntitySchema) {
//			ScimEntitySchema rdb_resource = (ScimEntitySchema) resource;
//			rdb_resource.setAttributes(getAttributeSchema(resource));
//		}
		
		return entity;
	}

	@Override
	public Map<String, ScimResourceAttribute> getAttributeSchema(ScimResourceSchema resource) throws ScimException {
		Map<String, ScimResourceAttribute> attributes = new HashMap<String, ScimResourceAttribute>();
		ScimEntitySchema rdb_resource = (ScimEntitySchema) resource;

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result_set = null;
		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(this.config.getColumOfTable());
			statement.setString(1, rdb_resource.getStorageSchema());
			statement.setString(2, rdb_resource.getStorageName());

			result_set = statement.executeQuery();
			while (result_set.next()) {

				String column_name = result_set.getString("COLUMN_NAME");
				int index = result_set.getInt("ORDINAL_POSITION");
				String column_default = result_set.getString("COLUMN_DEFAULT");
				String nullable = result_set.getString("IS_NULLABLE");
				String type_name = result_set.getString("DATA_TYPE");
				int length = result_set.getInt("CHARACTER_MAXIMUM_LENGTH");
				String description = result_set.getString("COLUMN_COMMENT");

				if (column_default != null && column_default.toUpperCase().equals("NULL")) {
					column_default = null;
				}
				boolean is_null_able = true;
				if (nullable != null && nullable.equals("NO")) {
					is_null_able = false;
				}
				ScimTypeDefinition.DataType scim_type = find_scim_type(type_name);
				int data_type = this.metaData.get(type_name);
				
				ScimEntityAttribute attribute = new ScimEntityAttribute(
						column_name, scim_type, type_name,	data_type, length,
						description, index, column_default, is_null_able);

				log.debug("{}->{}({}):{}", attribute.getName(), type_name,data_type, scim_type);
				
				attributes.put(attribute.getName(), attribute);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			close_connection(connection, statement, result_set);
		}

		return attributes;
	}

	private ScimTypeDefinition.StorageType find_resource_type(ResultSet result_set) throws SQLException {
		ScimTypeDefinition.StorageType res_type;
		String table_type = result_set.getString("TABLE_TYPE");
		if ("VIEW".equals(table_type.toUpperCase())) {
			res_type = ScimTypeDefinition.StorageType.VIEW;
		} else if ("BASE TABLE".equals(table_type)) {
			res_type = ScimTypeDefinition.StorageType.TABLE;
		} else {
			res_type = ScimTypeDefinition.StorageType.TABLE;
		}
		return res_type;
	}
}
