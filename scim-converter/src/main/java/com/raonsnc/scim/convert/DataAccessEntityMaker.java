package com.raonsnc.scim.convert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimAttributeSchema;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;

@Data
public class DataAccessEntityMaker {
	String packageName;
	String className;
	String interfaceName;
	
	Set<String> packages = new HashSet<String>();
	List<ScimAttributeSchema> attributes;	
	
	public void addPackage(String packageName) {
		this.packages.add(packageName);
	}
	
	public void make() throws ScimException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setFallbackOnNullLoopVariable(true);
		
		try {
			URL templete_url = getClass().getClassLoader().getResource("class_template");
			if (templete_url != null) {
				cfg.setDirectoryForTemplateLoading(new File(templete_url.toURI()));
			}

			
			Template data_access_template =  cfg.getTemplate("data_access_entity_template.tflh");
			
			Writer file_writer = new FileWriter(new File("../out/test.java"));
			data_access_template.process(this, file_writer);
			
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	public boolean complie() throws ScimException {

		return false;
	}
}
