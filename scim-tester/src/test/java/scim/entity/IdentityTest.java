package scim.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.raonsnc.scim.entity.ScimEntity;
import com.raonsnc.scim.entity.ScimIdentity;
import com.raonsnc.scim.entity.impl.DefaultEntity;
import com.raonsnc.scim.entity.impl.DefaultIdentity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdentityTest {

	@Test
	public void identity_string_parser_test() {
		try {
			ScimIdentity identity = new DefaultIdentity();
			identity.setScimIdentitySchema(null);
			
			identity.parse("test");
			
			log.debug("{}", identity);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Test
	public void identity_entity_parser_test() {
		try {
			ScimIdentity identity = new DefaultIdentity();
			identity.setScimIdentitySchema(null);
			
			ScimEntity entity = new DefaultEntity();
			entity.put("key", "key_id");
			identity.parse(entity);
			
			log.debug("{}", identity);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
