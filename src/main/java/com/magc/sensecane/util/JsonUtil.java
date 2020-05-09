package com.magc.sensecane.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

	public static <K,V> String toJson(Map<K, V> parameters) {
		StringBuilder str = new StringBuilder("{");
		
		int size = parameters.entrySet().size();
		int i = 0;
		for (Entry<K, V> entry : parameters.entrySet()) {
			str.append("\"")
				.append(entry.getKey().toString())
				.append("\": \"")
				.append(entry.getValue().toString())
				.append("\"")
				.append(++i < size ? ", " : "");
		}
		
		return str.append("}").toString();
	}
	
	public static Map<String, String> fromStr(String str) {
		Map<String, String> p = new HashMap<String, String>();
		JsonElement el = new JsonParser().parse(str);
		JsonObject o = el.getAsJsonObject();
		for (String key : o.keySet()) {
			String value;
			value = o.get(key).getAsString();
			p.put(key, value);
		}
		return p;
	}
	
}
