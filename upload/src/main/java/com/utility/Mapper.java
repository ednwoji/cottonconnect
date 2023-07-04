package com.utility;

import java.util.Date;
import java.util.List;

import com.finallyz.oauth.service.domain.AuditableBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Mapper {

	public static <T> T map(Object object, Class<T> clazz) {
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(object);
		return gson.fromJson(json, clazz);
	}

	public static <T> List<T> mapList(Object object, List<T> type) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = gson.toJson(object);
		type = gson.fromJson(json, new TypeToken<List<T>>() {
		}.getType());
		return type;
	}

	public static <E> void setAuditable(E object, String user) {
		AuditableBase auditable = (AuditableBase) object;
		auditable.setCreatedDate(new Date());
		auditable.setUpdatedDate(new Date());
		auditable.setActive(true);
		auditable.setDeleted(false);
		auditable.setCreatedUser(user);
		auditable.setUpdatedUser(user);
	}


}
