package com.magc.sensecane.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.GsonBuilder;

public class HttpUtil {
	
	public static <K,V> String addParametersToUrl(String url, Map<K,V> parameters) {
		String str = url;
		try {
			final URIBuilder builder = new URIBuilder(url);
			parameters.entrySet().forEach(entry -> builder.addParameter(entry.getKey().toString(), entry.getValue().toString()));
			str = builder.build().toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static <K,V> String jsonify(Map<K, V> parameters) {
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
	
	public static <K,V> String jsonify(Object object) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(object).replaceAll("^[a-zA-Z@.]*", "");
	}
	
	public static <K,V> String doPut(String url, Map<K,V> parameters) throws IOException {
		String str = null;
		
		HttpPut put = new HttpPut(url);
		put.setHeader("Accept", "application/json");
		put.setHeader("Content-type", "application/json");
		put.setEntity(new StringEntity(JsonUtil.toJson(parameters)));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(put)) {
        	str = EntityUtils.toString(response.getEntity());
        }
		
		return str;
	}
	
	public static <K,V> String doDelete(String url, Map<K,V> parameters) throws IOException {
		HttpDelete delete = new HttpDelete(addParametersToUrl(url, parameters));
		delete.setHeader("Accept", "application/json");
		delete.setHeader("Content-type", "application/json");
		
		String str = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(delete)) {
        	str = EntityUtils.toString(response.getEntity());
        }
		return str;
	}
}
