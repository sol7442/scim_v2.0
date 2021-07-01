package scim.core;

import org.junit.jupiter.api.Test;

import com.raonsnc.scim.engine.ScimClassMaker;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TypeTest {
	@Test
	public void type_test() {
		log.debug("object   : {}",ScimTypeDefinition.Returned.DEFAULT);
		log.debug("name 	: {}",ScimTypeDefinition.Returned.DEFAULT.name());
		log.debug("value 	: {}",ScimTypeDefinition.Returned.DEFAULT.value());
		log.debug("toString : {}",ScimTypeDefinition.Returned.DEFAULT.toString());
		
		log.debug("valueOf(DEFAULT)  : {}",ScimTypeDefinition.Returned.valueOf("DEFAULT"));
		log.debug("valueOf(default)  : {}",ScimTypeDefinition.Returned.valueBy("default"));
	}
	
	
	public void DataAccessEntity_Test() {
		ScimClassMaker maker = new ScimClassMaker();
		
		
		
		
	}
}
