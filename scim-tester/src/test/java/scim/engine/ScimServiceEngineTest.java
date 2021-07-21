package scim.engine;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.google.gson.GsonBuilder;
import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.repo.ScimRepositoryService;
import com.raonsnc.scim.repo.ScimRepositoryAdapter;
import com.raonsnc.scim.repo.ScimStorage;
import com.raonsnc.scim.repo.ScimStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.rdb.ScimDataSourceBuilder;
import com.raonsnc.scim.repo.rdb.ScimRdbResourceSchema;
import com.raonsnc.scim.service.ScimEntityAllService;
import com.test.NewDataAccessEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScimServiceEngineTest {

	static String repository_config_file 	= "../config/maria_config.yaml";
	static String repository_adatper_file	= "../config/maria_adapter.yaml";
	static String oacx_admin_resource_file	= "../out/oacx_admin_resource.json";
	
	static Map<String,ScimEntityAllService> scim_engines = new HashMap<String, ScimEntityAllService>();
	static ScimRdbResourceSchema schema = null;
	static ScimEntityAllService service = null;
	
	@BeforeAll
	static public void initialize() {
		try {
			ScimStorageRegistry.getInstance().initialize();
			
			schema = new GsonBuilder().create().fromJson(new FileReader(new File(oacx_admin_resource_file)), ScimRdbResourceSchema.class);
			DataSource data_source = new ScimDataSourceBuilder().build(ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file));
			ScimStorage stoage = ScimStorageRegistry.getInstance().create(data_source, ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file ));
			
			ScimRepositoryService repository = new ScimRepositoryAdapter("oacx",data_source, stoage);
			repository.open();
			//service = new ScimRepositoryService(repository,schema);
			scim_engines.put(schema.getName(), service);
			
			for (Entry<String, ScimEntityAllService> scim_engine : scim_engines.entrySet()) {
				String name = scim_engine.getKey();
				ScimRepositoryService repository_service = 	(ScimRepositoryService)	scim_engine.getValue();
				
				log.debug("service open : {}-{}", name, repository_service.getSchemaList());
			}	
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		
	}
	@AfterAll
	static public void destroy() {
		try {
			for (Entry<String, ScimEntityAllService> scim_engine : scim_engines.entrySet()) {
				String name = scim_engine.getKey();
				ScimRepositoryService repository_service = 	(ScimRepositoryService)	scim_engine.getValue();
				
				log.debug("service open : {}-{}", name, repository_service.getSchemaList());
				repository_service.close();
			}	
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}
	@Test @Order(1)
	public void create_test() {
		ScimEntity  entity_object = null;
		try {
			entity_object = (ScimEntity) Class.forName(schema.getEntityClassName()).newInstance();
			
			entity_object.put(NewDataAccessEntity.ID				,"engine_tester1");
			entity_object.put(NewDataAccessEntity.NAME				,"service_tester1");
			entity_object.put(NewDataAccessEntity.PASSWD			,"password");
			entity_object.put(NewDataAccessEntity.SEED				,"seed");
			entity_object.put(NewDataAccessEntity.PASSWD_CHANGE_DATE,new Timestamp(System.currentTimeMillis()));
			entity_object.put(NewDataAccessEntity.STATE_CODE		,"ENABLE");
			entity_object.put(NewDataAccessEntity.AUTHORITY_CODE	,"ADMIN");
			entity_object.put(NewDataAccessEntity.DEPT_NAME			,"DEV");
			entity_object.put(NewDataAccessEntity.PHONE_NO			,"017-1111-1234");
			entity_object.put(NewDataAccessEntity.CREATE_DATE		,new Timestamp(System.currentTimeMillis()));
			entity_object.put(NewDataAccessEntity.CREATE_USER_ID	,null);
			entity_object.put(NewDataAccessEntity.UPDATE_DATE		,new Timestamp(System.currentTimeMillis()));
			
			service.create(entity_object);
			
			log.debug("entity : {}", entity_object);
		}catch (Exception e) {
			log.error(e.getMessage() + ": {}", entity_object);
		}finally {
			
		}
	}
	
	@Test @Order(2)
	public void read_test() {
		ScimEntity  entity_object = null;
		try {
			entity_object = service.read("engine_tester1");
			
			log.debug("entity : {}", entity_object);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			
		}
	}
	
	@Test @Order(3)
	public void update_test() {
		try {
			ScimEntity  entity_object = service.read("engine_tester1");
			
			entity_object.put(NewDataAccessEntity.PASSWD			,"chanedpassword");
			entity_object.put(NewDataAccessEntity.SEED				,"seed");
			entity_object.put(NewDataAccessEntity.PASSWD_CHANGE_DATE,new Timestamp(System.currentTimeMillis()));
			entity_object.put(NewDataAccessEntity.STATE_CODE		,null);
			entity_object.put(NewDataAccessEntity.CREATE_DATE		,new Timestamp(System.currentTimeMillis()));
			entity_object.put(NewDataAccessEntity.CREATE_USER_ID	,null);
			entity_object.put(NewDataAccessEntity.UPDATE_DATE		,new Timestamp(System.currentTimeMillis()));
			
			ScimEntity old_object = service.update(entity_object);

			log.debug("entity : {}>{}", old_object,entity_object);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			
		}
	}
	
	@Test @Order(4)
	public void search_test() {
		
	}
	
	@Test @Order(5)
	public void delete_test() {
		try {
			boolean result = service.delete("engine_tester1");
			
			log.debug("entity : {}:{}", "engine_tester1" ,result );
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			
		}
	}
	
	@Test @Order(6)
	public void query_test() {
		
	}
	
	@Test @Order(7)
	public void call_test() {
		
	}
}
