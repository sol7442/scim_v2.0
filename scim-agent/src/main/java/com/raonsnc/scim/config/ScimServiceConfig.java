package com.raonsnc.scim.config;



import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.raonsnc.scim.object.ScimServiceInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ScimServiceConfig {
	Map<String,ScimServiceInfo> services = new HashMap<String, ScimServiceInfo>();
	
	public void save(String fileName) throws Exception {
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(fileName));
			new GsonBuilder().setPrettyPrinting().create().toJson(this, writer);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			if(writer != null)
				writer.close();
		}
	}
	
	public static ScimServiceConfig load(String fileName) throws Exception {
		FileReader reader = null;
		try {
			reader = new FileReader(new File(fileName));
			return new GsonBuilder().create().fromJson(reader, ScimServiceConfig.class);
		}finally {
			log.info("service config : {}", fileName);
			
			if(reader != null)
				reader.close();
		}
	}
}
