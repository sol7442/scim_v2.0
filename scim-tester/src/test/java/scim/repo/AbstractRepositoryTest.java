package scim.repo;



import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.repo.ScimRepositoryService;
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
public abstract class AbstractRepositoryTest {
	
	
	@BeforeEach
	public void initialize() {
		make_config_file();
		DataStorageRegistry.getInstance().initialize();
	}
	
	public abstract void  make_config_file();
	public abstract DataSourceConfig load_data_source_file();
	public abstract StorageConfig load_adapter_config_file();
	public abstract ScimRepositoryService getRepository();// load_adapter_config_file();
	
	@Test
	public void repository_test() throws ScimException {
		ScimRepositoryService repository = null;
		try {

			DataSource data_source = new ScimDataSourceBuilder().build(load_data_source_file());
			DataStorage stoage = DataStorageRegistry.getInstance().create(data_source, load_adapter_config_file());
			repository = new ScimRepositoryAdapter("oacx",data_source, stoage);
			
			if(repository.open()) {
				log.info("connection : {}",true);
				List<String> schema_list = 	repository.getSchemaList();
				for (String schema_name : schema_list) {
					log.info(" -{}",schema_name);
					List<ScimResourceSchema> resource_list = repository.getResourceSchemaList(schema_name);
					for (ScimResourceSchema resource : resource_list) {
						log.info(" --{}",resource);
						
						repository.findAttributeSchema(resource);
						Map<String,ScimResourceAttribute> attributes_1 = resource.getAttributes();
						for (Entry<String, ScimResourceAttribute> entry : attributes_1.entrySet()) {
							log.info(" ---{}:{}",entry.getKey(),entry.getValue());
						}
						
						Map<String,ScimResourceAttribute> attributes_2 = repository.getAttributeSchema(resource);
						for (Entry<String, ScimResourceAttribute> entry : attributes_2.entrySet()) {
							log.info(" ---{}:{}",entry.getKey(),entry.getValue());
						}
						
					}
				}
			}
			
			
			
			
//			
//			
//			if(repository.testConnect()) {
//				List<String> schema_list = 	repository.getSchemList();
//				
//				for (String schema_name : schema_list) {
//					log.info(" - {}",schema_name);
//					List<ScimResourceSchema> table_list =  repository.getTableList(schema_name);
//					for (ScimResourceSchema table : table_list) {
//						log.info(" -- {}",table);
//						repository.findColumns(table);
//
//						Map<String,ScimAttributeSchema> column_map = table.getAttributes();
//						for (Entry<String,ScimAttributeSchema> entry : column_map.entrySet()) {
//							log.info(" --- {} : {}",entry.getKey(), entry.getValue());
//						}
//					}
//					
//					List<ScimResourceSchema> view_list =  repository.getViewList(schema_name);
//					for (ScimResourceSchema table : view_list) {
//						log.info(" -- {}",table);
//						repository.findColumns(table);
//
//						Map<String,ScimAttributeSchema> column_map = table.getAttributes();
//						for (Entry<String,ScimAttributeSchema> entry : column_map.entrySet()) {
//							log.info(" --- {} : {}",entry.getKey(), entry.getValue());
//						}
//					}
//					
//					List<ScimResourceSchema> synonym_list =  repository.getSynonymList(schema_name);
//					for (ScimResourceSchema table : synonym_list) {
//						log.info(" -- {}",table);
//						Map<String,ScimAttributeSchema> column_map =  repository.getColumns(table);
//						for (Entry<String,ScimAttributeSchema> entry : column_map.entrySet()) {
//							log.info(" --- {} : {}",entry.getKey(), entry.getValue());
//						}
//					}
//				}
//				

			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			if(repository != null) {
				repository.close();
			}
		}
	}
}
