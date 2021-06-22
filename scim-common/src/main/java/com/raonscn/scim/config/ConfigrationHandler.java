package com.raonscn.scim.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import com.raonsnc.scim.ScimException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigrationHandler {
	static ConfigrationHandler instance;
	
	public static ConfigrationHandler getInstance() {
		if(instance == null) {
			instance = new ConfigrationHandler();
		}
		return instance;
	}
	
	
	public <T> T load(Class<T> clazz,String file_name) throws ScimException {
        try {
        	Yaml yaml = new Yaml(new Constructor(clazz));
			return yaml.load(new FileInputStream(file_name));
		} catch (FileNotFoundException e) {
			throw new ScimException(file_name,e);
		}
	}
	
	public void save(ScimConfig config, String file_name) throws IOException{
		try {
			DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(FlowStyle.BLOCK);
			options.setPrettyFlow(true);
			options.setAllowUnicode(true);
			
			Representer representer = new Representer() {
				@Override
			    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,Tag customTag) {
			        if (propertyValue == null) {
			            return null;
			        }  
			        else {
			            return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
			        }
			    }
			};
			
			Yaml yaml = new Yaml(representer,options);
			yaml.dump(config, new FileWriter(file_name));
			
		} catch (IOException e) {
			log.error(": {}",file_name, e);
		}
	}
}
