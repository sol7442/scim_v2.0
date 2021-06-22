package scim.repo;



import java.io.IOException;

import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.ScimRepository;
import com.raonsnc.scim.repo.conf.DataSourceConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MariaRepositoryTest extends AbstractRepositoryTest {
	String repository_config_file 	= "../config/maria_config.yaml";
	String repository_adatper_file	= "../config/maria_adapter.yaml";
	
	
	@Override
	public void make_config_file() {
		DataSourceConfig data_source = new DataSourceConfig();
		data_source.setJdbcUrl(		"jdbc:mariadb://192.168.0.105:3306/oacx_stag");
		data_source.setUser(	"root");
		data_source.setPasswd(	"oacxpwd@1");
		
		
		StorageConfig adapter = new StorageConfig();
		adapter.setDriver("org.mariadb.jdbc.Driver");
		adapter.setValidate("SELECT 1");
		adapter.setSchema("SELECT DISTINCT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME NOT IN ('information_schema', 'performance_schema','mysql')");
		
		adapter.setTable("SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA = ?");
		adapter.setColumOfTable("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?");
		
		ConfigrationHandler config_handler = ConfigrationHandler.getInstance();
		try {
			config_handler.save(data_source, repository_config_file);
			config_handler.save(adapter, repository_adatper_file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.info("{}", data_source);
	}
	
	@Override
	public DataSourceConfig load_data_source_file(){
		DataSourceConfig config = null;
		try {
			config =  ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file );
			log.debug("{}",config);
		} catch (ScimException e) {
			e.printStackTrace();
		}
		return config;
	}
	@Override
	public StorageConfig load_adapter_config_file() {
		try {
			return ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file );
		} catch (ScimException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ScimRepository getRepository() {
		// TODO Auto-generated method stub
		return null;
	}
}
