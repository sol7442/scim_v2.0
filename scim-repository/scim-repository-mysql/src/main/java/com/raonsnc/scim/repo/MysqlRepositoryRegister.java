package com.raonsnc.scim.repo;

public class MysqlRepositoryRegister implements ScimStorageRegister {

	@Override
	public ScimStorageFactory regist() {
		return new MysqlStorageFactory();
	}

}
