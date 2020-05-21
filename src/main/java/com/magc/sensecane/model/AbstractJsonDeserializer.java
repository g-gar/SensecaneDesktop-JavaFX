package com.magc.sensecane.model;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public abstract class AbstractJsonDeserializer<T> implements JsonDeserializer<T> {
	
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		Map<String, Object> temp = new TreeMap<String, Object>();
		obj.entrySet().stream().forEach(e -> temp.put(e.getKey(), e.getValue().getAsJsonObject()));
		return this.<T>deserialize(temp);
	}
	
	public <T> T get(Map<String, Object> map, String field) {
		T result = null;
		if (map.containsKey(field)) {
			result = (T) map.get(field);
		} else {
			System.out.printf("Field %s not found", field);
		}
		return result;
	}

	public abstract T deserialize(Map<String, Object> map);
}
