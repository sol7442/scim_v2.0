package com.raonsnc.scim.repo;


public class MariaRegister implements DataStorageRegister{

	@Override
	public DataStorageFactory regist() {
		return new MariaFactory();
	}

}
