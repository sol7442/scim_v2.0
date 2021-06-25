package com.raonsnc.scim.schema;

public class ScimTypeDefinition {
	public enum Mutability {
		readWrite, readOnly, writeOnly,immutable
	}

	public enum DataType {
		String("string", 	"java.lang.String"), 
		Boolean("boolean",	"java.lang.Boolean"),
		Decimal("decimal",	"java.lang.Decimal"),
		Integer("integer",	"java.lang.Integer"),
		DateTime("datetime","java.lang.String"),
		Date("date",		"java.util.Date"),
		Binary("binary",	"java.lang.Binary"),
		Reference("reference",null),
		Complex("complex"	 ,null);
		
		private String type;
		private String packageName;
		private DataType(String type, String packageName) {
			this.type = type;
			this.packageName = packageName;
		}
		public String getType() {
			return this.type;
		}
		public String getPackageName() {
			return this.packageName;
		}
		public String getClassName() {
			return this.packageName.substring(this.packageName.lastIndexOf(".") + 1);
		}
		
		
		
	}

	public enum Returned{
		ALWAYS, NEVER, DEFAULT, REQUEST;
		
		public String value() {
			return name().toLowerCase();
		}
		public static Returned valueBy(String name){
			return valueOf(name.toUpperCase());
		}
	}
	
	public enum Uniqueness{
		none, server,global
	}
	
	public enum ReferenceType{
		USER, GROUP, EXTERNAL, URI 
	}
	
	public enum StorageType{
		TABLE,VIEW,SYNONIM
	}
}
