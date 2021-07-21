package test.config;

import org.junit.jupiter.api.Test;

import com.raonsnc.scim.config.ScimAgentConfig;
import com.raonsnc.scim.repo.conf.DataSourceConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgentConfigurationTest {

	@Test
	public void create_configuration_test() {
		ScimAgentConfig config = new ScimAgentConfig();
		config.setName("local.yong");
		
		try {
			DataSourceConfig data_souce = DataSourceConfig.load(DataSourceConfig.class, "../config/maria_config.yaml");
			log.debug("--> {}",data_souce);
			
			config.setDataSource(data_souce);
			config.getPaths().put("service","../config/service_config.json");
			
			config.save("../config/agent_config.yaml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
