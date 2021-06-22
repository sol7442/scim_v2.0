package com.raonsnc.scim.schema;

public class ScimType {
	public enum Mutability {
		readWrite, readOnly, writeOnly,immutable
	}

	public enum DataType {
		String("string"), 
		Boolean("boolean"),
		Decimal("decimal"),
		Integer("integer"),
		DateTime("datetime"),
		Date("date"),
		Binary("binary"),
		Reference("reference"),
		Complex("complex");
		
		private String type;
		private DataType(String type) {
			this.type = type;
		}
		public String getType() {
			return this.type;
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
	
	public enum ResouceType{
		TABLE,VIEW,SYNONIM
	}
}
