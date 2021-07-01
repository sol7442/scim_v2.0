package com.raonsnc.scim.schema;

public class ScimTypeDefinition {
	public enum Mutability {
		readWrite, readOnly, writeOnly,immutable
	}

	public enum DataType {
		String	("string", 	"java.lang.String"			), 
		Boolean	("boolean",	"java.lang.Boolean"			),
		Decimal	("decimal",	"java.lang.Decimal"			),
		Integer	("integer",	"java.lang.Integer"			),
		DateTime("dateTime","java.time.LocalDateTime"),
		
		Binary	("binary"		,"com.raonsnc.util.Binary"),
		Reference("reference"	,"com.raonsnc.util.Reference"),
		Complex	("complex"	 	,null);
		
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
		public static DataType valueBy(String name) {
			StringBuilder builder = new StringBuilder();
			builder.append(name.substring(0,1).toUpperCase());
			builder.append(name.substring(1));
			return valueOf(builder.toString());
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
	
	public enum Status{
		TemporaryRedirect("Temporary Redirect", 307,null,null),
		PermanentRedirect("Permanent Redirect", 307,null,null),
		BadRequest_invalidFilter("Bad Request", 400,"invalidFilter"	,"The specified filter syntax was invalid"),
		BadRequest_tooMany		("Bad Request", 400,"tooMany"		,"The specified filter yields many more results than the server is willing to calculate or process"),
		BadRequest_uniqueness	("Bad Request", 400,"uniqueness"	,"One or more of the attribute values are already in use or are reserved"),
		BadRequest_mutability	("Bad Request", 400,"mutability"	,"The attempted modification is not compatible with the target attribute's mutability"),
		BadRequest_invalidSyntax("Bad Request", 400,"invalidSyntax"	,"The request body message structure was invalid or did not conform to the request schema"),
		BadRequest_invalidPath	("Bad Request", 400,"invalidPath"	,"The \"path\" attribute was invalid or malformed"),
		BadRequest_noTarget		("Bad Request", 400,"noTarget"		,"The specified \"path\" did not yield an attribute or attribute value that could be operated on"),
		BadRequest_invalidValue	("Bad Request", 400,"invalidValue"	,"A required value was missing, or the value specified was not compatible with the operation or attribute type"),
		BadRequest_invalidVers	("Bad Request", 400,"invalidVers"	,"The specified SCIM protocol version is not supported"),
		BadRequest_sensitive	("Bad Request", 400,"sensitive"		,"The specified request cannot be completed, due to the passing of sensitive"),
		; 
		   
		private String status;
		private int code;
		private String type;
		private String description;
		private Status(String status, int code, String type, String description ) {
			this.status 		= status;
			this.code 			= code;
			this.type 			= type;
			this.description  	= description; 
		}
		
		public String getStatus() {
			return this.status;
		}
		public int getCode() {
			return this.code;
		}
		public String getType() {
			return this.type;
		}
		public String getDescription() {
			return this.description;
		}
	}
}
