package com.raonsnc.scim.repo;


public class MariaRepositoryRegister implements ScimStorageRegister{

	@Override
	public ScimStorageFactory regist() {
		return new MariaStorageFactory();
	}

}
