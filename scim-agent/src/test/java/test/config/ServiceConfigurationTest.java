package test.config;

import org.junit.jupiter.api.Test;

import com.raonsnc.scim.config.ScimAgentConfig;
import com.raonsnc.scim.config.ScimServiceConfig;
import com.raonsnc.scim.object.ScimServiceInfo;
import com.raonsnc.scim.service.entity.ScimEntityReadResourceService;

public class ServiceConfigurationTest {

	@Test
	public void create_configuration_test() {
		ScimServiceConfig config = new ScimServiceConfig();
		try {
			ScimServiceInfo info = new ScimServiceInfo();
			
			
			info.setName(ScimEntityReadResourceService.NAME);
			info.setType("entity");
			info.setEntityClass(ScimEntityReadResourceService.class.getSimpleName());
			
			config.getServices().put(info.getName(), info);
			
			config.save("../config/service_config.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
