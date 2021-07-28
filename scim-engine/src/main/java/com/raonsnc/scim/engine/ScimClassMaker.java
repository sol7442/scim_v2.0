package com.raonsnc.scim.engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.raonsnc.scim.ScimException;
import com.raonsnc.scim.schema.ScimResourceAttribute;
import com.raonsnc.scim.schema.ScimTypeDefinition;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ScimClassMaker {
	String workspace;
	String packageName;

	Set<String> packages ;
	
	String className;
	String superClass;
	List<String> interfaces;
	List<String> exceptions;

	long serialVersion;
	
	List<ScimResourceAttribute> attributes;
	int attributesSize;
	String templateFile;
	
	public void addPackage(String packageName) {
		if(this.packages == null) {
			this.packages = new HashSet<String>();
		}
		this.packages.add(packageName);
	}
	public void addInterface(String interfaceName) {
		if(this.interfaces == null) {
			this.interfaces = new ArrayList<String>();
		}
		this.interfaces.add(interfaceName);
	}
	public void addException(String exceptionName) {
		if(this.exceptions == null) {
			this.exceptions  =new ArrayList<String>();
		}
		this.exceptions.add(exceptionName);
	}
	public String getCanonicalName() {
		return new StringBuilder().append(this.packageName).append(".").append(this.className).toString();
	}

	public void checkFileDirectory() {
		Path dirPath = null;
		try {
			
			StringBuilder builder = new StringBuilder();
			builder.append(this.workspace);
			builder.append(File.separator);
			builder.append(packageName.replace(".",File.separator));
			
			dirPath = Paths.get(builder.toString());
			if(Files.exists(dirPath) == false) {
				Files.createDirectories(dirPath);
				log.debug("make directory : {}",dirPath);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			log.debug("directory : {}",dirPath);
		}
	}
	public File getClassFile() {
		File classFile = null;
		try {
			
			StringBuilder builder = new StringBuilder();
			builder.append(this.workspace);
			builder.append(File.separator);
			builder.append(packageName.replace(".",File.separator));
			builder.append(File.separator);
			builder.append(this.className).append(".").append("java");
			
			classFile = new File(builder.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		return classFile;
	}
	

	public void make() throws ScimException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setFallbackOnNullLoopVariable(true);
		
		try 
		(Writer file_writer = new FileWriter(getClassFile()))
		{
			URL templete_url = getClass().getClassLoader().getResource("class_template");
			if (templete_url != null) {
				cfg.setDirectoryForTemplateLoading(new File(templete_url.toURI()));
			}
			
			Template class_template =  cfg.getTemplate(templateFile);
//			for (ScimAttributeSchema attr : attributes) {
//				if(attr.getType() != null) {
//					ScimTypeDefinition.DataType type = ScimTypeDefinition.DataType.valueBy(attr.getType());
//					this.packages.add(type.getPackageName());					
//				}
//			}
			
			class_template.process(this, file_writer);
			
		} catch (IOException | URISyntaxException e) {
			log.error(e.getMessage(), e);
		} catch (TemplateException e) {
			log.error(e.getMessage(), e);
		}
	}

	public boolean complie() throws ScimException {

		return false;
	}
	
}
