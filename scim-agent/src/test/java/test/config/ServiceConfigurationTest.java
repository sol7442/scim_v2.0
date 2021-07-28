package test.config;

import org.junit.jupiter.api.Test;

import com.raonsnc.scim.object.ScimServiceInfo;
import com.raonsnc.scim.repo.conf.DataSourceConfig;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.service.ScimResourceReadServiceImpl;
import com.raonsnc.scim.service.ScimServiceConfig;

public class ServiceConfigurationTest {

	@Test
	public void create_configuration_test() {
		ScimServiceConfig config = new ScimServiceConfig();
		try {
			DataSourceConfig datasource = DataSourceConfig.load(DataSourceConfig.class,"../config/maria_config.yaml");
			StorageConfig 		storage = StorageConfig.load(StorageConfig.class,"../config/maria_adapter.yaml");
			
			ScimServiceInfo info = new ScimServiceInfo();
			
			info.setName(ScimResourceReadServiceImpl.NAME);
			info.setType("scim");
			info.setEntityClass(ScimResourceReadServiceImpl.class.getSimpleName());
			
			config.getServices().put(info.getName(), info);
			config.setDataSource(datasource);
			config.setDataStorage(storage);
			
			config.save("../config/service_config.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
