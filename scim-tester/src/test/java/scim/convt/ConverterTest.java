package scim.convt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.sql.DataSource;

import java.io.File;
import java.io.FileWriter;
import java.lang.String;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.convert.DataAccessEntityMaker;
import com.raonsnc.scim.entity.impl.DefaultEntity;
import com.raonsnc.scim.entity.impl.DefaultIdentity;
import com.raonsnc.scim.repo.ScimRepository;
import com.raonsnc.scim.repo.ScimRepositoryAdapter;
import com.raonsnc.scim.repo.ScimStorage;
import com.raonsnc.scim.repo.ScimStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.rdb.ScimDataSourceBuilder;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimIdentitySchema;
import com.raonsnc.scim.schema.ScimResourceSchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConverterTest {

	String repository_config_file 	= "../config/maria_config.yaml";
	String repository_adatper_file	= "../config/maria_adapter.yaml";
	String oacx_admin_resource_file	= "../out/oacx_admin_resource.json";
	
	@BeforeEach
	public void initialize() {
		ScimStorageRegistry.getInstance().initialize();
	}
	
	@Test
	public void make_map_test() {
		
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
						if("OACX_ADMIN".equals(resource.getName())) {
							repository.findAttributeSchema(resource);
							
							List<ScimAttributeSchema> attribute_list_1 = new ArrayList<ScimAttributeSchema>();
							Map<String,ScimAttributeSchema> attributes_1 = resource.getAttributes();
							for (Entry<String, ScimAttributeSchema> entry : attributes_1.entrySet()) {
								log.info(" ---{}:{}",entry.getKey(),entry.getValue());
								attribute_list_1.add(entry.getValue());
							}

//							DataAccessEntityMaker maker = new DataAccessEntityMaker();
//							
//							maker.setPackageName("com.test");
//							maker.setWorkspace("D:\\workspace\\.raon.git\\scim\\scim-tester\\src\\test\\java");
//							
//							maker.addPackage("java.util.HashMap");
//							maker.addPackage("com.raonsnc.scim.entity.ScimEntity");
//							
//							maker.setInterfaceName("ScimEntity");
//							maker.setClassName("NewDataAccessEntity");
//							
////							maker.setIdentity_set("put(\"id\",id);");
////							maker.setIdentity_get("get(\"id\");");
//							
//							maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
//							
//							maker.validate();
//							maker.setAttributes(attribute_list_1);
//							maker.make();
//							
							ScimIdentitySchema identity = new ScimIdentitySchema();
							identity.add("id");
							resource.setIdentitySchema(identity);
							resource.setEntityClassName(DefaultEntity.class.getCanonicalName());
							resource.setIdentityClassName(DefaultIdentity.class.getCanonicalName());
							resource.setVersion(UUID.randomUUID().toString());
							resource.setManaged(true);
							
							
							log.info("{}",resource.toJson());
							FileWriter writer = new FileWriter(new File(oacx_admin_resource_file));
							writer.write(resource.toJson());
							writer.close();
							
							//repository.addResourceType(scim_resource_type);
						}
					}
				}
				
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
