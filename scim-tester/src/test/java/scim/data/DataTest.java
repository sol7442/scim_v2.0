package scim.data;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataTest {
	@Test
	public void data_convert_test() {
		Integer int_val 	= new Integer(1234);
		Integer null_val 	= null;
		
		log.info("{}:{}",int_val );
		log.info("{}:{}",int_val.toString() );
		log.info("{}:{}",null_val);
	}
}
