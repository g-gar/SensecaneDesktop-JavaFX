package com.magc.sensecane.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.magc.sensecane.framework.http.HttpAsyncMethodExecutor;

public class HttpService {

	public static class GET {
		
		public static <T> HttpGet build(String url, Header...headers) {
//			Configuration conf = Application.getInstance().get(Configuration.class);
			HttpGet get = new HttpGet(url);
			Arrays.asList(headers).forEach(header->get.setHeader(header));
			return get;
		}
		
	}
	
	public static <T> void request(HttpRequestBase http, Consumer<T> consumer) {
		try {
			HttpAsyncMethodExecutor<T> executor = new HttpAsyncMethodExecutor<T>(consumer) {
				@Override
				public T parseResponse(HttpEntity entity) {
					T result = null;
					try {
						String str = EntityUtils.toString(entity);
						Type type = new TypeToken<T>() {}.getType();
						result = new Gson().fromJson(str, type);
					} catch (NullPointerException | ParseException | IOException e) {
						e.printStackTrace();
					}
					return result;
				}
			};
			executor.fetch(http);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> void requestList(HttpRequestBase http, TypeToken<Map<Object, T>> type, Consumer<List<T>> consumer) {
		try {
			HttpAsyncMethodExecutor<List<T>> executor = new HttpAsyncMethodExecutor<List<T>>(consumer) {
				@Override
				public List<T> parseResponse(HttpEntity entity) {
					List<T> result = null;
					try {
						String str = EntityUtils.toString(entity);
//						Type type = new TypeToken<Map<Object, T>>() {}.getType();
						Map<Object, T> map = new Gson().fromJson(str, type.getType());
						System.out.println(map);
						result = new ArrayList<T>();
						result.addAll(map.values());
						System.out.println(result);
					} catch (NullPointerException | ParseException | IOException e) {
						e.printStackTrace();
					}
					return result;
				}
			};
			executor.fetch(http);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
