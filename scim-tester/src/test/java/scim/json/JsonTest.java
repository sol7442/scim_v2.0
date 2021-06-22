package scim.json;

import org.junit.jupiter.api.Test;

import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonTest {
	@Test
	public void toJson_test() {
		DataTrasnferEntity entity = new DataTrasnferEntity();
		entity.setId("entity_id");
		entity.setName("entity_name");
		
		entity.put("id", "test_id");
		entity.put("com.test.out", "testobject");
		
		log.info("{}",new GsonBuilder().setPrettyPrinting().create().toJson(entity)); 
		
	}
	
	@Test
	public void fromJson_test() {
		
	}
}
