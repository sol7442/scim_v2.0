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
import com.raonscn.scim.json.ScimJson;
import com.raonsnc.scim.engine.ScimClassMaker;
import com.raonsnc.scim.repo.ScimRepositoryService;
import com.raonsnc.scim.repo.ScimEntitySchema;
import com.raonsnc.scim.repo.DataStorage;
import com.raonsnc.scim.repo.DataStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.impl.ScimDataSourceBuilder;
import com.raonsnc.scim.repo.impl.ScimRepositoryAdapter;
import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimResourceSchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConverterTest {

	String repository_config_file 	= "../config/maria_config.yaml";
	String repository_adatper_file	= "../config/maria_adapter.yaml";
	String oacx_admin_resource_file	= "../out/oacx_admin_resource.json";
	
	@BeforeEach
	public void initialize() {
		DataStorageRegistry.getInstance().initialize();
	}
	
	@Test
	public void make_map_test() {
		
		ScimRepositoryService repository = null;
		try {
			DataSource data_source = new ScimDataSourceBuilder().build(ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file));
			DataStorage stoage = DataStorageRegistry.getInstance().create(data_source, ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file ));
			repository = new ScimRepositoryAdapter("test",data_source, stoage);
			
			if(repository.open()) {
				log.info("connection : {}",true);
				List<String> schema_list = 	repository.getSchemaList();
				for (String schema_name : schema_list) {
					log.info(" -{}",schema_name);
					List<ScimResourceSchema> resource_list = repository.getResourceSchemaList(schema_name);
					for (ScimResourceSchema res : resource_list) {
						ScimEntitySchema resource = (ScimEntitySchema)res;
						
						
						log.info(" --{}",resource);
						if("OACX_ADMIN".equals(resource.getStorageName())) {
							repository.findAttributeSchema(resource);
							
							List<ScimResourceAttribute> attribute_list_1 = new ArrayList<ScimResourceAttribute>();
							Map<String,ScimResourceAttribute> attributes_1 = resource.getAttributes();
							for (Entry<String, ScimResourceAttribute> entry : attributes_1.entrySet()) {
								log.info(" ---{}:{}",entry.getKey(),entry.getValue());
								attribute_list_1.add(entry.getValue());
							}

							ScimClassMaker entity_class_maker = new ScimClassMaker();
							entity_class_maker.setPackageName("com.test");
							entity_class_maker.setWorkspace("D:\\workspace\\.raon.git\\scim\\scim-tester\\src\\test\\java");
							
							entity_class_maker.addPackage("java.util.HashMap");
							entity_class_maker.addPackage("com.raonsnc.scim.entity.ScimEntity");
							
							entity_class_maker.setSuperClass("HashMap<String, Object>");
							entity_class_maker.addInterface("ScimEntity");
							entity_class_maker.setClassName("NewDataAccessEntity");
							
							entity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
							entity_class_maker.setAttributes(attribute_list_1);
						
							entity_class_maker.checkFileDirectory();
							
							entity_class_maker.setTemplateFile("entity_template.tflh");
							entity_class_maker.make();
							
							ScimClassMaker identity_class_maker = new ScimClassMaker();
							
							
							
							identity_class_maker.setPackageName("com.test");
							identity_class_maker.setWorkspace("D:\\workspace\\.raon.git\\scim\\scim-tester\\src\\test\\java");
							
							identity_class_maker.addPackage("java.util.HashMap");
							identity_class_maker.addPackage("java.util.StringTokenizer");
							identity_class_maker.addPackage("com.raonsnc.scim.entity.ScimEntity");
							identity_class_maker.addPackage("com.raonsnc.scim.entity.ScimIdentity");
							identity_class_maker.addPackage("com.raonsnc.scim.ScimException");
							identity_class_maker.addPackage("com.raonscn.scim.util.DataConverter");
							
							
							
							identity_class_maker.setSuperClass("HashMap<String, Object>");
							identity_class_maker.addInterface("ScimIdentity");
							identity_class_maker.setClassName("NewIdentity");
							
							
							identity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
							
							List<ScimResourceAttribute> identity_attributes = new ArrayList<ScimResourceAttribute>();
							identity_attributes.add(attributes_1.get("id"));
							//identity_attributes.add(attributes_1.get("name"));
							identity_class_maker.setAttributesSize(1);
							
							identity_class_maker.setAttributes(identity_attributes);
							
							
							identity_class_maker.checkFileDirectory();
							
							identity_class_maker.setTemplateFile("identity_template.tflh");
							identity_class_maker.make();
							
							resource.setEntityClassName(entity_class_maker.getCanonicalName());
							resource.setIdentityClassName(identity_class_maker.getCanonicalName());
							resource.setVersion(UUID.randomUUID().toString());
							//resource.setManaged(true);
							
							
//							log.info("{}",new ScimJson().toJson(resource,ScimRdbResourceSchema.class));
//							log.info("{}","--------------------------------------------------------------");
//							log.info("{}",new ScimJson().toJson(resource,ScimResourceSchema.class));

							log.info("{}",new ScimJson().toJson(resource,ScimEntitySchema.class));
							log.info("{}","--------------------------------------------------------------");
							log.info("{}",new ScimJson().toJson(resource,ScimResourceSchema.class));
							
							FileWriter writer = new FileWriter(new File(oacx_admin_resource_file));
							writer.write(new ScimJson().toJson(resource,ScimEntitySchema.class));
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
