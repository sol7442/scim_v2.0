package scim.engine;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.raonscn.scim.config.ConfigrationHandler;
import com.raonscn.scim.json.ScimJson;
import com.raonsnc.scim.engine.ScimClassMaker;
import com.raonsnc.scim.repo.ScimRepositoryService;
import com.raonsnc.scim.repo.ScimRepositoryAdapter;
import com.raonsnc.scim.repo.ScimStorage;
import com.raonsnc.scim.repo.ScimStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.rdb.ScimDataSourceBuilder;
import com.raonsnc.scim.repo.rdb.ScimRdbResourceSchema;
import com.raonsnc.scim.represent.ScimRepresentAttributeSchema;
import com.raonsnc.scim.represent.ScimRepresentResourceSchema;
import com.raonsnc.scim.schema.ScimAttributeSchema;
import com.raonsnc.scim.schema.ScimMetaSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.schema.ScimSimpleAttributeSchema;
import com.raonsnc.scim.schema.ScimSimpleIdentitySchema;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScimSchemaGeneratorTest_SCIM_USER {

	static String repository_config_file 	= "../config/maria_config.yaml";
	static String repository_adatper_file	= "../config/maria_adapter.yaml";
	
	static String scim_user_workspace		= "./src/test/java/";
	static String scim_user_package		    = "com.raonsnc.scim.example";
	
	static String scim_user_repository_file	= "../out/scim_user_repository.json";
	static String scim_user_identity_file	= "../out/scim_user_identity.json";
	static String scim_user_entity_class   	= "SCIM_USER_ENTITY";
	static String scim_user_identity_class	= "SCIM_USER_IDENTITY";
	
	static String scim_user_mapping_file	= "../out/scim_user_mapping.json";
	static String scim_user_mapping_class   = "SCIM_USER_MAPPER";
	
	static String scim_user_represent_file	= "../out/scim_user_represent.json";
	static String scim_user_meta_file		= "../out/scim_user_meta.json";
	static String scim_user_represent_class = "SCIM_USER_REPRESENT";
	
	static String scim_user_table_name = "SCIM_USER";
	static String scim_owner_name      = "scim";
			
			
	
	static ScimRdbResourceSchema schema;
	static ScimRepresentResourceSchema represent;
	static ScimRepositoryService repository;
	@BeforeAll
	static public void initialize() {
		try {
			ScimStorageRegistry.getInstance().initialize();
			
			DataSource data_source = new ScimDataSourceBuilder().build(ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file));
			ScimStorage stoage = ScimStorageRegistry.getInstance().create(data_source, ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file ));
			repository = new ScimRepositoryAdapter("oacx",data_source, stoage);
			
			repository.open();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@AfterAll
	static public void destroy() {
		try {
			repository.close();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}
	
	@Test @Order(1)
	public void rdb_repository_schema_generate_test() {
		try {
			
			List<String> schema_list = 	repository.getSchemaList();
			for (String schema_name : schema_list) {
				log.info(" -{}",schema_name);
				List<ScimResourceSchema> resource_list = repository.getResourceSchemaList(schema_name);
				for (ScimResourceSchema res : resource_list) {
					ScimRdbResourceSchema resource = (ScimRdbResourceSchema)res;
					
					if(scim_user_table_name.equals(resource.getStorageName()) && scim_owner_name.equals(resource.getStorageOwner())) {
						schema = (ScimRdbResourceSchema)res;
						
						repository.findAttributeSchema(schema);
						
						log.info(" --{}\n{}",schema,schema.toJson());
						
						schema.setId(UUID.randomUUID().toString());
						schema.setName("ScimUser");
						schema.setDescription("Scim User REPOSITORY SCHEMA");
						
						FileWriter writer = new FileWriter(new File(scim_user_repository_file));
						writer.write(new ScimJson().toJson(schema,ScimRdbResourceSchema.class));
						writer.close();
					}
				}
			}
						
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	@Test @Order(2)
	public void scim_identity_schema_generate_test() {
		try {
			// mapper
			ScimSimpleIdentitySchema identity = new ScimSimpleIdentitySchema(schema.getAttribute("id"));
			{
				identity.setName("id");
				identity.setDescription("identity");
				identity.setType(ScimTypeDefinition.DataType.String.getType());
			}
			
			
			log.info(" -?-{}\n{}",identity,identity.toJson());
			
			FileWriter writer = new FileWriter(new File(scim_user_identity_file));
			writer.write(new ScimJson().toJson(identity,ScimSimpleIdentitySchema.class));
			writer.close();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	

	
	@Test @Order(3)
	public void rdb_entity_class_generate_test() {
		try {
			List<ScimAttributeSchema> attribute_list = new ArrayList<ScimAttributeSchema>();
			for (Entry<String, ScimAttributeSchema> entry : schema.getAttributes().entrySet()) {
				attribute_list.add(entry.getValue());
			}
			
			ScimClassMaker entity_class_maker = new ScimClassMaker();
			entity_class_maker.setWorkspace(scim_user_workspace);
			entity_class_maker.setPackageName(scim_user_package);
			
			entity_class_maker.addPackage("java.util.HashMap");
			entity_class_maker.addPackage("com.raonsnc.scim.entity.ScimEntity");
			
			entity_class_maker.setSuperClass("HashMap<String, Object>");
			entity_class_maker.addInterface("ScimEntity");
			entity_class_maker.setClassName(scim_user_entity_class);

			entity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
			entity_class_maker.setAttributes(attribute_list);
		
			entity_class_maker.checkFileDirectory();
			
			entity_class_maker.setTemplateFile("entity_template.tflh");
			entity_class_maker.make();
			
			ScimClassMaker meta_class_maker = new ScimClassMaker();
			meta_class_maker.setPackageName("com.test");
			meta_class_maker.setWorkspace("D:\\workspace\\.raon.git\\scim\\scim-tester\\src\\test\\java");
			
			meta_class_maker.addPackage("java.util.HashMap");
			meta_class_maker.addPackage("com.raonsnc.scim.entity.ScimMeta");
			
			meta_class_maker.setSuperClass("HashMap<String, Object>");
			meta_class_maker.addInterface("ScimMeta");
			meta_class_maker.setClassName(schema.getName()+ "Meta");
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	@Test @Order(4)
	public void rdb_idnetity_class_generate_test() {
		try {
			List<ScimAttributeSchema> attribute_list = new ArrayList<ScimAttributeSchema>();
			for (Entry<String, ScimAttributeSchema> entry : schema.getAttributes().entrySet()) {
				attribute_list.add(entry.getValue());
			}
			
			ScimClassMaker identity_class_maker = new ScimClassMaker();
			
			identity_class_maker.setWorkspace(scim_user_workspace);
			identity_class_maker.setPackageName(scim_user_package);
			
			identity_class_maker.addPackage("java.util.HashMap");
			identity_class_maker.addPackage("com.raonsnc.scim.entity.ScimEntity");
			identity_class_maker.addPackage("com.raonsnc.scim.entity.ScimIdentity");
			identity_class_maker.addPackage("com.raonsnc.scim.ScimException");
			identity_class_maker.addPackage("com.raonscn.scim.util.DataConverter");
			
			
			identity_class_maker.setSuperClass("HashMap<String, Object>");
			identity_class_maker.addInterface("ScimIdentity");
			identity_class_maker.setClassName(scim_user_identity_class);
			
			
			identity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
			
			List<ScimAttributeSchema> identity_attributes = new ArrayList<ScimAttributeSchema>();
			identity_attributes.add(schema.getAttribute("id"));
			identity_class_maker.setAttributesSize(1);
			
			identity_class_maker.setAttributes(identity_attributes);
			
			identity_class_maker.checkFileDirectory();
			
			identity_class_maker.setTemplateFile("identity_template.tflh");
			identity_class_maker.make();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@Test @Order(5)
	public void scim_resource_schema_generate_test() {
		try {
			represent = new ScimRepresentResourceSchema();

			for (Entry<String, ScimAttributeSchema> entry : schema.getAttributes().entrySet()) {
				ScimAttributeSchema attribute = new ScimAttributeSchema(entry.getValue());
				
				ScimAttributeSchema represent_attribute = new ScimAttributeSchema(attribute);
				int index_ = attribute.getName().lastIndexOf("_");
				if(index_ > 0) {
					represent_attribute.setName(attribute.getName().toLowerCase().substring(0,index_));
				}else {
					represent_attribute.setName(attribute.getName().toLowerCase());
				}
				
				log.debug("{}",represent_attribute.getName());
				
				represent.addAttribute(represent_attribute);
			}
			
			represent.setId(scim_user_meta_file);
			represent.setName(scim_user_meta_file);
			represent.setDescription(scim_user_meta_file);
			
			log.info(" -3-{}\n{}",represent,represent.toJson());
			
			FileWriter writer = new FileWriter(new File(scim_user_represent_file));
			writer.write(new ScimJson().toJson(represent,ScimRepresentResourceSchema.class));
			writer.close();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	
	@Test @Order(6)
	public void scim_meta_schema_generate_test() {
		try {
			// mapper
			ScimMetaSchema meta = new ScimMetaSchema();
			ScimSimpleAttributeSchema created      = new ScimSimpleAttributeSchema();
			{
				created.setName("created");
				created.setDescription("created time");
				created.setType(ScimTypeDefinition.DataType.DateTime.getType());
				created.setMultiValued(false);
				created.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				created.setReturned(ScimTypeDefinition.Returned.DEFAULT.value());
				created.setUniqueness(ScimTypeDefinition.Uniqueness.none.name());
				
			}
			ScimSimpleAttributeSchema lastModify   = new ScimSimpleAttributeSchema();
			{
				lastModify.setName("lastModify");
				lastModify.setDescription("modified time");
				lastModify.setType(ScimTypeDefinition.DataType.DateTime.getType());
				lastModify.setMultiValued(false);
				lastModify.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				lastModify.setReturned(ScimTypeDefinition.Returned.DEFAULT.value());
				lastModify.setUniqueness(ScimTypeDefinition.Uniqueness.none.name());
			}
			ScimSimpleAttributeSchema resourceType = new ScimSimpleAttributeSchema();
			{
				resourceType.setName("resourceType");
				resourceType.setDescription("resource type");
				resourceType.setType(ScimTypeDefinition.DataType.String.getType());
				resourceType.setMultiValued(false);
				resourceType.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				resourceType.setReturned(ScimTypeDefinition.Returned.ALWAYS.value());
				resourceType.setUniqueness(ScimTypeDefinition.Uniqueness.none.name());
			}
			ScimSimpleAttributeSchema version 	 = new ScimSimpleAttributeSchema();
			{
				version.setName("version");
				version.setDescription("resource version");
				version.setType(ScimTypeDefinition.DataType.String.getType());
				version.setMultiValued(false);
				version.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				version.setReturned(ScimTypeDefinition.Returned.DEFAULT.value());
				version.setUniqueness(ScimTypeDefinition.Uniqueness.none.name());
			}
			ScimSimpleAttributeSchema location 	 = new ScimSimpleAttributeSchema();
			{
				location.setName("location");
				location.setDescription("resource http url");
				location.setType(ScimTypeDefinition.DataType.String.getType());
				location.setMultiValued(false);
				location.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				location.setReturned(ScimTypeDefinition.Returned.DEFAULT.value());
				location.setUniqueness(ScimTypeDefinition.Uniqueness.global.name());
			}
			meta.addAttribute(created);
			meta.addAttribute(lastModify);
			meta.addAttribute(resourceType);
			meta.addAttribute(version);
			meta.addAttribute(location);
			meta.setName("meta");
			meta.setDescription("resource meta");
			
			log.info(" -?-{}\n{}",meta,meta.toJson());
			
			FileWriter writer = new FileWriter(new File(scim_user_meta_file));
			writer.write(new ScimJson().toJson(meta,ScimMetaSchema.class));
			writer.close();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	@Test @Order(7)
	public void scim_resource_class_generate_test() {
		try {

			ScimClassMaker entity_class_maker = new ScimClassMaker();
			
			List<ScimAttributeSchema> attribute_list = new ArrayList<ScimAttributeSchema>();
			for (Entry<String, ScimAttributeSchema> entry : represent.getAttributes().entrySet()) {
				ScimRepresentAttributeSchema attribute = new ScimRepresentAttributeSchema(entry.getValue());
				attribute_list.add(attribute);
			}
			
			entity_class_maker.setWorkspace(scim_user_workspace);
			entity_class_maker.setPackageName(scim_user_package);
			
			entity_class_maker.addPackage("java.util.HashMap");
			entity_class_maker.addPackage("com.raonsnc.scim.entity.ScimEntity");
			
			entity_class_maker.setSuperClass("HashMap<String, Object>");
			entity_class_maker.addInterface("ScimEntity");
			entity_class_maker.setClassName(scim_user_represent_class);
			
			entity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
			entity_class_maker.setAttributes(attribute_list);
		
			entity_class_maker.checkFileDirectory();
			
			entity_class_maker.setTemplateFile("transfer_template.tflh");
			entity_class_maker.make();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@Test @Order(8)
	public void scim_mapper_class_generate_test() {
		try {

			ScimClassMaker entity_class_maker = new ScimClassMaker();
			
			List<ScimAttributeSchema> attribute_list = new ArrayList<ScimAttributeSchema>();
			for (Entry<String, ScimAttributeSchema> entry : represent.getAttributes().entrySet()) {
				ScimRepresentAttributeSchema attribute = new ScimRepresentAttributeSchema(entry.getValue());
				attribute_list.add(attribute);
			}
			
			entity_class_maker.setWorkspace(scim_user_workspace);
			entity_class_maker.setPackageName(scim_user_package);
			
			entity_class_maker.addPackage("com.raonsnc.scim.entity.ScimEntity");
			entity_class_maker.addPackage("com.raonsnc.scim.entity.ScimMapper");
			
			entity_class_maker.addInterface("ScimMapper");
			entity_class_maker.setClassName(scim_user_mapping_class);
			
			entity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
			entity_class_maker.setAttributes(attribute_list);
		
			entity_class_maker.checkFileDirectory();
			
			entity_class_maker.setTemplateFile("mapper_template.tflh");
			entity_class_maker.make();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}