package scim.convt;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.convert.DataAccessEntityMaker;
import com.raonsnc.scim.repo.ScimRepository;
import com.raonsnc.scim.repo.ScimRepositoryAdapter;
import com.raonsnc.scim.repo.ScimStorage;
import com.raonsnc.scim.repo.ScimStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.rdb.ScimDataSourceBuilder;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConverterTest {

	String repository_config_file 	= "../config/maria_config.yaml";
	String repository_adatper_file	= "../config/maria_adapter.yaml";
	
	
	@BeforeEach
	public void initialize() {
		ScimStorageRegistry.getInstance().initialize();
	}
	
	@Test
	public void make_map_test() {
		
		DataAccessEntityMaker maker = new DataAccessEntityMaker();
		
		maker.setPackageName("com.test");
		
		maker.addPackage("java.util.HashMap");
		maker.addPackage("com.raonsecure.scim.entity.DataAccessEntity");
		
		maker.setInterfaceName("DataAccessEntity");
		maker.setClassName("NewDataAccessEntity");
		
		ScimRepository repository = null;
		try {
			DataSource data_source = new ScimDataSourceBuilder().build(ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file));
			ScimStorage stoage = ScimStorageRegistry.getInstance().create(data_source, ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file ));
			repository = new ScimRepositoryAdapter(data_source, stoage);
			
			if(repository.testConnect()) {
				log.info("connection : {}",true);
				List<String> schema_list = 	repository.getSchemaList();
				for (String schema_name : schema_list) {
					log.info(" -{}",schema_name);
					List<ScimResourceSchema> resource_list = repository.getResourceSchemaList(schema_name);
					for (ScimResourceSchema resource : resource_list) {
						log.info(" --{}",resource);
						
						repository.findAttributeSchema(resource);
						List<ScimAttributeSchema> attribute_list_1 = resource.getAttributes();
						for (ScimAttributeSchema attribute : attribute_list_1) {
							log.info(" ---{}",attribute);
						}
						
//						List<ScimAttributeSchema> attribute_list_2 = repository.getAttributeSchema(resource);
//						for (ScimAttributeSchema attribute : attribute_list_2) {
//							log.info(" ---{}",attribute);
//						}
						
						maker.setAttributes(attribute_list_1);
					}
				}
			}
			
			maker.make();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
