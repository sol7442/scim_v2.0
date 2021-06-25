package scim.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.GsonBuilder;
import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.entity.ScimIdentity;
import com.raonsnc.scim.repo.ScimRepository;
import com.raonsnc.scim.repo.ScimRepositoryAdapter;
import com.raonsnc.scim.repo.ScimStorage;
import com.raonsnc.scim.repo.ScimStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.rdb.ScimDataSourceBuilder;
import com.raonsnc.scim.schema.ScimResourceSchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimEngineTest {

	String repository_config_file 	= "../config/maria_config.yaml";
	String repository_adatper_file	= "../config/maria_adapter.yaml";
	String oacx_admin_resource_file	= "../out/oacx_admin_resource.json";
	@BeforeEach
	public void initialize() {
		ScimStorageRegistry.getInstance().initialize();
	}
	
	//@Test
	public void create_entity_object() {
		ScimRepository repository = null;
		try {
			ScimResourceSchema resource_schema = load_resource_schema();
			repository = load_repository(resource_schema);
			
			@SuppressWarnings("unchecked")
			Class<ScimEntity> entity_class =  (Class<ScimEntity>) Class.forName(resource_schema.getEntityClassName());
					
			ScimEntity entity = entity_class.newInstance();
			entity.setId("test_id");
			entity.put("name","test_name");
			
			repository.create(entity);
			
			log.info("{}:{}",entity.getClass().getCanonicalName(), entity);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			repository.distroy();
		}
	}
	
	@Test
	public void read_entity_object() {
		ScimRepository repository = null;
		try {
			ScimResourceSchema resource_schema = load_resource_schema();
			repository = load_repository(resource_schema);
			
			@SuppressWarnings("unchecked")
			Class<ScimEntity> 	entity_class   =  (Class<ScimEntity>) Class.forName(resource_schema.getEntityClassName());
			@SuppressWarnings("unchecked")
			Class<ScimIdentity> identity_class =  (Class<ScimIdentity>) Class.forName(resource_schema.getIdentityClassName());
			
			
			
			ScimEntity read_entity = repository.read("admin",entity_class );
			
			ScimIdentity identity_obj = identity_class.newInstance();
			for(String key : resource_schema.getIdentitySchema()) {
				identity_obj.put(key, read_entity.get(key));
			}
			
			
			log.info("{}:{}","admin", read_entity);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			repository.distroy();
		}
	}
	
	//@Test
	public void update_entity_object() {
		ScimRepository repository = null;
		try {
			ScimResourceSchema resource_schema = load_resource_schema();
			repository = load_repository(resource_schema);
			
			@SuppressWarnings("unchecked")
			Class<ScimEntity> entity_class =  (Class<ScimEntity>) Class.forName(resource_schema.getEntityClassName());
					
			ScimEntity entity = entity_class.newInstance();
			entity.setId("test_id");
			entity.put("name","test_name");
			entity.put("seed","12345");
			entity.put("create_date","2021-03-16 09:08:19");
			entity.put("passwd",null);
			
			
			
			repository.update(entity);
			
			log.info("{}:{}",entity.getClass().getCanonicalName(), entity);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			repository.distroy();
		}
	}
	
	//@Test
	public void delete_entity_object() {
		ScimRepository repository = null;
		try {
			ScimResourceSchema resource_schema = load_resource_schema();
			repository = load_repository(resource_schema);
			
			@SuppressWarnings("unchecked")
			Class<ScimEntity> entity_class =  (Class<ScimEntity>) Class.forName(resource_schema.getEntityClassName());
					
			repository.delete("test_id",entity_class );
			
			log.info("{}:{}","test_id", true);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			repository.distroy();
		}
	}

	private ScimResourceSchema load_resource_schema() throws FileNotFoundException {
		ScimResourceSchema resource_schema = new GsonBuilder().create().fromJson(new FileReader(new File(oacx_admin_resource_file)), ScimResourceSchema.class);
		return resource_schema;
	}

	private ScimRepository load_repository(ScimResourceSchema resource_schema) throws ScimException {
		ScimRepository repository;
		DataSource data_source = new ScimDataSourceBuilder().build(ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file));
		ScimStorage stoage = ScimStorageRegistry.getInstance().create(data_source, ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file ));
		repository = new ScimRepositoryAdapter(data_source, stoage);
		repository.addResourcerSchema(resource_schema);
		return repository;
	}
}
