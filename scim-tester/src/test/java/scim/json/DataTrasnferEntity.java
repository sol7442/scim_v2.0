package scim.json;


import java.util.HashMap;

import com.raonsnc.scim.entity.ScimEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataTrasnferEntity extends HashMap<String,Object> implements ScimEntity {
	private static final long serialVersionUID = -2403216115099620610L;

	String id;
	String name;
	
}
