package scim.repo;



import java.io.IOException;

import com.raonscn.scim.config.ConfigrationHandler;
import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.repo.conf.StorageConfig;
import com.raonsnc.scim.repo.ScimRepositoryService;
import com.raonsnc.scim.repo.conf.DataSourceConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OarcleRepositoryTest extends AbstractRepositoryTest{
	String repository_config_file 	= "../config/oracle_config.yaml";
	String repository_adatper_file	= "../config/oracle_adapter.yaml";
	
	@Override
	public void make_config_file() {
		DataSourceConfig data_source = new DataSourceConfig();
		data_source.setJdbcUrl(		"jdbc:oracle:thin:@192.168.0.105:1521:xe");
		data_source.setUser(	"oacx");
		data_source.setPasswd(	"1q2w3e4r!@");
		
		
		StorageConfig adapter = new StorageConfig();
		adapter.setDriver("oracle.jdbc.driver.OracleDriver");
		adapter.setValidate("SELECT 1 FROM DUAL");
		adapter.setSchema("SELECT USERNAME FROM ALL_USERS WHERE USERNAME NOT IN ('ANONYMOUS','APEX_040000','APEX_PUBLIC_USER','CTXSYS','FLOWS_FILES','MDSYS','MIN','OUTLN','SYS','SYSTEM','HR','XDB','XS$NULL') ORDER  BY  USERNAME");
		
		adapter.setTable("SELECT TABLE_NAME , OWNER FROM ALL_TABLES WHERE OWNER =?");
		adapter.setView("SELECT VIEW_NAME , OWNER, TEXT AS REF_INFO FROM SYS.ALL_VIEWS WHERE OWNER =?");
		adapter.setSynonym("SELECT SYNONYM_NAME , TABLE_OWNER AS OWNER , TABLE_NAME AS REF_INFO FROM ALL_SYNONYMS WHERE OWNER =?");	
		
		adapter.setColumOfTable("SELECT * FROM ALL_TAB_COLUMNS WHERE TABLE_NAME=?");
		adapter.setColumnOfView("SELECT * FROM ALL_TAB_COLUMNS WHERE TABLE_NAME=?");
		adapter.setColumnOfSynonym("SELECT * FROM ALL_TAB_COLUMNS atc JOIN ALL_SYNONYMS als ON atc.table_name = als.table_name WHERE als.SYNONYM_NAME=?");
		
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
	public DataSourceConfig load_data_source_file() {
//		try {
//			return ConfigrationHandler.getInstance().load(DataSourceConfig.class, repository_config_file );
//		} catch (ScimException e) {
//			e.printStackTrace();
//		}
		return null;
	}
	@Override
	public StorageConfig load_adapter_config_file() {
//		try {
//			return ConfigrationHandler.getInstance().load(StorageConfig.class, repository_adatper_file );
//		} catch (ScimException e) {
//			e.printStackTrace();
//		}
		return null;
	}

	@Override
	public ScimRepositoryService getRepository() {
		// TODO Auto-generated method stub
		return null;
	}
}
