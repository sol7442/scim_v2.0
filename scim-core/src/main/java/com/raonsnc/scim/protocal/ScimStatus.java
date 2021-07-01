package com.raonsnc.scim.protocal;

import lombok.Data;

@Data
public class ScimStatus {
	String status;
	String scimType;
	String detail;
}
