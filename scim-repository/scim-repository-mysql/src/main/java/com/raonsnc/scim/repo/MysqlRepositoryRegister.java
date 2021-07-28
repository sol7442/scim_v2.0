package com.raonsnc.scim.repo;

public class MysqlRepositoryRegister implements DataStorageRegister {

	@Override
	public DataStorageFactory regist() {
		return new MysqlStorageFactory();
	}

}
