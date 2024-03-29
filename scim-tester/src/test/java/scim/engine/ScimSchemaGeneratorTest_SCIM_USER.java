package scim.engine;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.omg.IOP.ENCODING_CDR_ENCAPS;

import com.raonscn.scim.config.ConfigrationHandler;
import com.raonscn.scim.json.ScimJson;
import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.engine.ScimClassMaker;
import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.entity.ScimMeta;
import com.raonsnc.scim.entity.ScimSimpleTransfer;
import com.raonsnc.scim.repo.ScimRepositoryService;
import com.raonsnc.scim.repo.ScimSimpleIdentity;
import com.raonsnc.scim.repo.ScimEntitySchema;
import com.raonsnc.scim.repo.DataStorage;
import com.raonsnc.scim.repo.DataStorageRegistry;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.impl.ScimDataSourceBuilder;
import com.raonsnc.scim.repo.impl.ScimRepositoryAdapter;
import com.raonsnc.scim.represent.ScimRepresentAttributeSchema;
import com.raonsnc.scim.represent.ScimRepresentResourceSchema;
import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimMetaSchema;
import com.raonsnc.scim.schema.ScimResourceSchema;
import com.raonsnc.scim.schema.ScimSimpleAttribute;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScimSchemaGeneratorTest_SCIM_USER {

	static String repository_config_file = "../config/maria_config.yaml";
	static String repository_adatper_file = "../config/maria_adapter.yaml";

	static String scim_user_workspace = "./src/test/java/";
	static String scim_user_package = "com.raonsnc.scim.example";

	static String scim_user_entity_schema_file = "../out/scim_user_entity_schema.json";
	static String scim_user_identity_file = "../out/scim_user_identity.json";
	static String scim_user_entity_class = "SCIM_USER_ENTITY";
	static String scim_user_identity_class = "SCIM_USER_IDENTITY";

	static String scim_user_schema_file = "../out/scim_user_schema.json";
	static String scim_user_transfer_class = "SCIM_USER_TRANSFER";

	// static String scim_user_represent_file = "../out/scim_user_represent.json";
	static String scim_user_meta_file = "../out/scim_user_meta.json";
	static String scim_user_meta_class = "SCIM_USER_META";

	static String scim_user_represent_class = "SCIM_USER_REPRESENT";

	static String scim_user_table_name  = "SCIM_USER";
	static String scim_user_schmea_name = "scim";

	static ScimMetaSchema meta;
	static ScimEntitySchema entity_schema;
	static ScimResourceSchema schema;
	static ScimRepositoryService repository;

	@BeforeAll
	static public void initialize() {
		try {
			DataStorageRegistry.getInstance().initialize();

			DataSource data_source = new ScimDataSourceBuilder()
					.build(ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file));
			DataStorage stoage = DataStorageRegistry.getInstance().create(data_source,
					ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file));
			repository = new ScimRepositoryAdapter("oacx", data_source, stoage);

			repository.open();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@AfterAll
	static public void destroy() {
		try {
			repository.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Test
	@Order(1)
	public void entity_schema_generate_test() {
		try {

			List<String> schema_list = repository.getSchemaList();
			for (String schema_name : schema_list) {
				log.info(" schema : {}", schema_name);
			}
			repository.getEntitySchema("","");
			
			
//				List<ScimEntitySchema> entity_list = repository.getEntitySchemaList(schema_name);
				
//				for (ScimEntitySchema entity : entity_list) {
//
//					if (scim_user_table_name.equals(entity.getStorageName()) && scim_user_schmea_name.equals(entity.getStorageSchema())) {
//						entity_schema = entity;//(ScimEntitySchema) res;
//
//						repository.findAttributeSchema(entity_schema);
//						
//						entity_schema.setId(UUID.randomUUID().toString());
//						entity_schema.setName("ScimUser");
//						entity_schema.setDescription("Scim User REPOSITORY SCHEMA");
//						
//						log.info(" --{}\n{}", entity_schema, entity_schema.toJson());
//
//						FileWriter writer = new FileWriter(new File(scim_user_entity_schema_file));
//						writer.write(new ScimJson().toJson(entity_schema, ScimEntitySchema.class));
//						writer.close();
//					}
//				}
//			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	//@Test
	@Order(2)
	public void scim_identity_schema_generate_test() {
		try {
			// mapper
			ScimSimpleIdentity identity = new ScimSimpleIdentity(entity_schema.getAttribute("id"));
			{
				identity.setName("id");
				identity.setDescription("identity");
				identity.setType(ScimTypeDefinition.DataType.String.getType());
			}

			log.info(" -?-{}\n{}", identity, identity.toJson());

			FileWriter writer = new FileWriter(new File(scim_user_identity_file));
			writer.write(new ScimJson().toJson(identity, ScimSimpleIdentity.class));
			writer.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	//@Test
	@Order(3)
	public void rdb_entity_class_generate_test() {
		try {
			List<ScimResourceAttribute> attribute_list = new ArrayList<ScimResourceAttribute>();
			for (Entry<String, ScimResourceAttribute> entry : entity_schema.getAttributes().entrySet()) {
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

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	//@Test
	@Order(4)
	public void rdb_idnetity_class_generate_test() {
		try {
			List<ScimResourceAttribute> attribute_list = new ArrayList<ScimResourceAttribute>();
			for (Entry<String, ScimResourceAttribute> entry : entity_schema.getAttributes().entrySet()) {
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

			List<ScimResourceAttribute> identity_attributes = new ArrayList<ScimResourceAttribute>();
			identity_attributes.add(entity_schema.getAttribute("id"));
			identity_class_maker.setAttributesSize(1);

			identity_class_maker.setAttributes(identity_attributes);

			identity_class_maker.checkFileDirectory();

			identity_class_maker.setTemplateFile("identity_template.tflh");
			identity_class_maker.make();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	//@Test
	@Order(5)
	public void scim_meta_schema_generate_test() {
		try {
			// mapper
			meta = new ScimMetaSchema();
			ScimSimpleAttribute created = new ScimSimpleAttribute();
			{
				created.setName("created");
				created.setDescription("created time");
				created.setType(ScimTypeDefinition.DataType.DateTime.getType());
				created.setMultiValued(false);
				created.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				created.setReturned(ScimTypeDefinition.Returned.DEFAULT.value());
				created.setUniqueness(ScimTypeDefinition.Uniqueness.none.name());

			}
			ScimSimpleAttribute lastModify = new ScimSimpleAttribute();
			{
				lastModify.setName("lastModify");
				lastModify.setDescription("modified time");
				lastModify.setType(ScimTypeDefinition.DataType.DateTime.getType());
				lastModify.setMultiValued(false);
				lastModify.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				lastModify.setReturned(ScimTypeDefinition.Returned.DEFAULT.value());
				lastModify.setUniqueness(ScimTypeDefinition.Uniqueness.none.name());
			}
			ScimSimpleAttribute resourceType = new ScimSimpleAttribute();
			{
				resourceType.setName("resourceType");
				resourceType.setDescription("resource type");
				resourceType.setType(ScimTypeDefinition.DataType.String.getType());
				resourceType.setMultiValued(false);
				resourceType.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				resourceType.setReturned(ScimTypeDefinition.Returned.ALWAYS.value());
				resourceType.setUniqueness(ScimTypeDefinition.Uniqueness.none.name());
			}
			ScimSimpleAttribute version = new ScimSimpleAttribute();
			{
				version.setName("version");
				version.setDescription("resource version");
				version.setType(ScimTypeDefinition.DataType.String.getType());
				version.setMultiValued(false);
				version.setMutability(ScimTypeDefinition.Mutability.readOnly.name());
				version.setReturned(ScimTypeDefinition.Returned.DEFAULT.value());
				version.setUniqueness(ScimTypeDefinition.Uniqueness.none.name());
			}
			ScimSimpleAttribute location = new ScimSimpleAttribute();
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

			log.info(" -?-{}\n{}", meta, meta.toJson());

			FileWriter writer = new FileWriter(new File(scim_user_meta_file));
			writer.write(new ScimJson().toJson(meta, ScimMetaSchema.class));
			writer.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	//@Test
	@Order(6)
	public void scim_resource_schema_generate_test() {
		try {
			schema = new ScimResourceSchema();
			for (Entry<String, ScimResourceAttribute> entry : entity_schema.getAttributes().entrySet()) {
				ScimResourceAttribute attribute = new ScimResourceAttribute(entry.getValue());

				ScimResourceAttribute represent_attribute = new ScimResourceAttribute(attribute);
				int index_ = attribute.getName().lastIndexOf("_");
				if (index_ > 0) {
					represent_attribute.setName(attribute.getName().toLowerCase().substring(0, index_));
				} else {
					represent_attribute.setName(attribute.getName().toLowerCase());
				}

				log.debug("{}", represent_attribute.getName());

				schema.addAttribute(represent_attribute);
			}
			schema.setMeta(meta);
			
			schema.setId("urn:raonsnc:scim:schemas:core:2.0:User");
			schema.setName("User");
			schema.setDescription("raon snc User Schema 2.0");

			log.info(" -3-{}\n{}", schema, schema.toJson());

			FileWriter writer = new FileWriter(new File(scim_user_schema_file));
			writer.write(new ScimJson().toJson(schema, ScimResourceSchema.class));
			writer.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	// @Test @Order(7)
	public void scim_resource_class_generate_test() {
		try {

			ScimClassMaker entity_class_maker = new ScimClassMaker();

			List<ScimResourceAttribute> attribute_list = new ArrayList<ScimResourceAttribute>();
			for (Entry<String, ScimResourceAttribute> entry : schema.getAttributes().entrySet()) {
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

			entity_class_maker.setTemplateFile("represent_template.tflh");
			entity_class_maker.make();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

//	@Test
//	@Order(6)
//	public void scim_meta_class_generate_test() {
//		try {
//
//			ScimClassMaker entity_class_maker = new ScimClassMaker();
//
//			ScimMetaSchema meta_schema = ScimMetaSchema.load(scim_user_meta_file);
//			
//			
//			List<ScimAttributeSchema> attribute_list = new ArrayList<ScimAttributeSchema>();
//			for (Entry<String, ScimAttributeSchema> entry : schema.getAttributes().entrySet()) {
//				ScimRepresentAttributeSchema attribute = new ScimRepresentAttributeSchema(entry.getValue());
//				attribute_list.add(attribute);
//			}
//
//			entity_class_maker.setWorkspace(scim_user_workspace);
//			entity_class_maker.setPackageName(scim_user_package);
//
//			entity_class_maker.addPackage("java.util.HashMap");
//			entity_class_maker.addPackage(ScimMeta.class.getCanonicalName());
//
//			entity_class_maker.setSuperClass("HashMap<String, Object>");
//			entity_class_maker.addInterface(ScimMeta.class.getSimpleName());
//			entity_class_maker.setClassName(scim_user_meta_class);
//
//			entity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
//			entity_class_maker.setAttributes(attribute_list);
//
//			entity_class_maker.checkFileDirectory();
//
//			entity_class_maker.setTemplateFile("meta_template.tflh");
//			entity_class_maker.make();
//
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//	}

	//@Test
	@Order(8)
	public void scim_transfer_class_generate_test() {
		try {

			ScimResourceSchema res_schema = ScimResourceSchema.load(scim_user_schema_file );
			ScimEntitySchema rdb_schema = ScimEntitySchema.load(scim_user_entity_schema_file);

			
			makeMetaClass(		res_schema,rdb_schema );
			makeTransferClass(	res_schema,rdb_schema);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	private void makeMetaClass(ScimResourceSchema res_schema, ScimEntitySchema rdb_schema) throws ScimException {
		
		log.debug("meta : {}", res_schema.getMeta()); 
		
		List<ScimResourceAttribute> attribute_list = res_schema.getMeta().getSubAttributes();
		
		ScimClassMaker entity_class_maker = new ScimClassMaker();
		entity_class_maker.setWorkspace(scim_user_workspace);
		entity_class_maker.setPackageName(scim_user_package);

		entity_class_maker.addPackage(HashMap.class.getCanonicalName());
		entity_class_maker.addPackage(ScimMeta.class.getCanonicalName());
		//entity_class_maker.addPackage(ScimEntity.class.getCanonicalName());
		//entity_class_maker.addPackage(ScimException.class.getCanonicalName());
		//entity_class_maker.addPackage(ScimSimpleTransfer.class.getCanonicalName());

		entity_class_maker.setSuperClass("HashMap<String, Object>");
		entity_class_maker.addInterface(ScimMeta.class.getSimpleName());
		entity_class_maker.setClassName(scim_user_meta_class);

		entity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
		entity_class_maker.setAttributes(attribute_list);

		entity_class_maker.checkFileDirectory();

		entity_class_maker.setTemplateFile("meta_template.tflh");
		entity_class_maker.make();
	}
	private void makeTransferClass(ScimResourceSchema res_schema, ScimEntitySchema rdb_schema) throws ScimException {
		
		List<ScimResourceAttribute> attribute_list = new ArrayList<ScimResourceAttribute>();
		for (Entry<String, ScimResourceAttribute> entry : res_schema.getAttributes().entrySet()) {
			ScimRepresentAttributeSchema attribute = new ScimRepresentAttributeSchema(entry.getValue());

			attribute_list.add(attribute);
		}
		
		ScimClassMaker entity_class_maker = new ScimClassMaker();
		entity_class_maker.setWorkspace(scim_user_workspace);
		entity_class_maker.setPackageName(scim_user_package);

		entity_class_maker.addPackage(HashMap.class.getCanonicalName());
		entity_class_maker.addPackage(ScimMeta.class.getCanonicalName());
		entity_class_maker.addPackage(ScimEntity.class.getCanonicalName());
		entity_class_maker.addPackage(ScimException.class.getCanonicalName());
		entity_class_maker.addPackage(ScimSimpleTransfer.class.getCanonicalName());

		entity_class_maker.setSuperClass("HashMap<String, Object>");
		entity_class_maker.addInterface(ScimSimpleTransfer.class.getSimpleName());
		entity_class_maker.setClassName(scim_user_transfer_class);

		entity_class_maker.setSerialVersion(UUID.randomUUID().getMostSignificantBits());
		entity_class_maker.setAttributes(attribute_list);

		entity_class_maker.checkFileDirectory();

		entity_class_maker.setTemplateFile("simple_template.tflh");
		entity_class_maker.make();
	}
}
