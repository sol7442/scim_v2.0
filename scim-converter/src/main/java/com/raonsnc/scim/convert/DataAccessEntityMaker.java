package com.raonsnc.scim.convert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimAttributeSchema;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DataAccessEntityMaker {
	String packageName;
	String className;
	String interfaceName;
	String workspace;
	boolean identity_modify = false;
//	String identity_get;
//	String identity_set;
	String identity_query;
	
	long serialVersion;
	
	Set<String> packages = new HashSet<String>();
	List<ScimAttributeSchema> attributes;	
	
	public void addPackage(String packageName) {
		this.packages.add(packageName);
	}
	public String getCanonicalName() {
		return new StringBuffer().append(this.packageName).append(".").append(this.className).toString();
	}
	public boolean validate() throws ScimException {
		Path out_path = Paths.get(get_out_path());
		
		try {
			Files.createDirectories(out_path);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			log.debug("{}:{}",File.separator, out_path);
		}
		return false;
	}
	private String get_out_path() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.workspace);
		buffer.append(File.separator);
		buffer.append(packageName.replace(".",File.separator));
		return buffer.toString();
	}
	public String get_out_file() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(get_out_path());
		buffer.append(File.separator);
		buffer.append(this.className);
		buffer.append(".java");
		return buffer.toString();
		 
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
			
			Template data_access_template =  cfg.getTemplate("data_entity_template.tflh");
			
			Writer file_writer = new FileWriter(new File(get_out_file()));
			
			for (ScimAttributeSchema attr : attributes) {
				String method_name	 = make_method_name(attr.getColumnName());
				attr.setMethodName(method_name);
				log.debug("method_name : {}" , method_name);
				
				this.packages.add(attr.getType().getPackageName());
			}
			
			
			
			
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
	
	private String make_method_name(String column_name) {
		StringBuffer buffer = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(column_name, "_");
		while(tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken();
			
			buffer.append(token.substring(0,1).toUpperCase());
			buffer.append(token.substring(1).toLowerCase());
		}
		return buffer.toString();
	}
}
