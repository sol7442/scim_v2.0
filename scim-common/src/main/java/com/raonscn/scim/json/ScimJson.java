package com.raonscn.scim.json;

import java.lang.reflect.Type;

import com.google.gson.GsonBuilder;

public class ScimJson {
	GsonBuilder builder = new GsonBuilder();
	public String toJson(Object object, Type typeOfSrc) {
		return builder.setPrettyPrinting().create().toJson(object, typeOfSrc);
	}
	public <T> T fromJson(String json, Class<T> classOfT) {
		return builder.create().fromJson(json, classOfT);
	}
}
