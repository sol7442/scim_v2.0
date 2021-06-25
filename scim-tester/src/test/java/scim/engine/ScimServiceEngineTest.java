package scim.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.GsonBuilder;
import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.engine.ScimRepositoryService;
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
import com.raonsnc.scim.service.ScimService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScimServiceEngineTest {

	static String repository_config_file 	= "../config/maria_config.yaml";
	static String repository_adatper_file	= "../config/maria_adapter.yaml";
	static String oacx_admin_resource_file	= "../out/oacx_admin_resource.json";
	
	static Map<String,ScimService> scim_engines = new HashMap<String, ScimService>();
	
	@BeforeAll
	static public void initialize() {
		try {
			ScimStorageRegistry.getInstance().initialize();
			ScimResourceSchema resource_schema = new GsonBuilder().create().fromJson(new FileReader(new File(oacx_admin_resource_file)), ScimResourceSchema.class);
			
			DataSource data_source = new ScimDataSourceBuilder().build(ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file));
			ScimStorage stoage = ScimStorageRegistry.getInstance().create(data_source, ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file ));
			
			ScimRepository repository = new ScimRepositoryAdapter(data_source, stoage);
			
			ScimService repository_service = new ScimRepositoryService(repository,resource_schema);
			scim_engines.put(resource_schema.getName(), repository_service);
			
			for (Entry<String, ScimService> scim_engine : scim_engines.entrySet()) {
				log.debug("service open : {}-{}", scim_engine.getKey(), scim_engine.getValue().open());
			}	
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		
	}
	@AfterAll
	static public void destroy() {
		try {
			for (Entry<String, ScimService> scim_engine : scim_engines.entrySet()) {
				log.debug("service close : {}-{}", scim_engine.getKey() );
				scim_engine.getValue().close();
			}	
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}
	@Test
	public void create_test() {
		try {
			ScimService servicer = scim_engines.get("key1");
			log.debug("servicer : {}", servicer);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			
		}
	}
	
	@Test
	public void read_test() {
		try {
			ScimService service = scim_engines.get("OACX_ADMIN");
			log.debug("service : {}", service.read("admin"));
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			
		}
	}
	
	@Test
	public void update_test() {
		try {
			ScimService servicer = scim_engines.get("key1");
			log.debug("servicer : {}", servicer);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			
		}
	}
	
	@Test
	public void delete_test() {
		try {
			ScimService servicer = scim_engines.get("key1");
			log.debug("servicer : {}", servicer);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			
		}
	}
}
