package scim.core;

import org.junit.jupiter.api.Test;

import com.raonsnc.scim.convert.DataAccessEntityMaker;
import com.raonsnc.scim.schema.ScimType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TypeTest {
	@Test
	public void type_test() {
		log.debug("object   : {}",ScimType.Returned.DEFAULT);
		log.debug("name 	: {}",ScimType.Returned.DEFAULT.name());
		log.debug("value 	: {}",ScimType.Returned.DEFAULT.value());
		log.debug("toString : {}",ScimType.Returned.DEFAULT.toString());
		
		log.debug("valueOf(DEFAULT)  : {}",ScimType.Returned.valueOf("DEFAULT"));
		log.debug("valueOf(default)  : {}",ScimType.Returned.valueBy("default"));
	}
	
	
	public void DataAccessEntity_Test() {
		DataAccessEntityMaker maker = new DataAccessEntityMaker();
		
		
		
		
	}
}
