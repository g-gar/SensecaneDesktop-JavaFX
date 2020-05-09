package com.magc.sensecane.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.http.HttpAsyncMethodExecutor;
import com.magc.sensecane.model.domain.Sensor;
import com.magc.sensecane.model.domain.User;

public class UserService {

	public static void getUserList(Consumer<List<User>> consumer) {
		
		Configuration conf = Application.getInstance().get(Configuration.class);
		
		String url = (String) conf.get("serverUrl") + "/users/";
		HttpGet get = new HttpGet(url);
		get.setHeader("accept", "application/json");
		
		HttpAsyncMethodExecutor<List<User>> executor = new HttpAsyncMethodExecutor<List<User>>(consumer) {
			@Override public List<User> parseResponse(HttpEntity entity) {
				List<User> result = null;
				try {
					String str = EntityUtils.toString(entity);
					Type type = new TypeToken<List<User>>() {}.getType();
					result = new Gson().fromJson(str, type);
				} catch (NullPointerException | ParseException | IOException e) {
					e.printStackTrace();
				}
				return result;
			}
		};
		
		try {
			executor.fetch(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getSensors(User user, Consumer<List<Sensor>> consumer) {
		String url = String.format("%s/users/%s/sensors/", Application.getInstance().get(Configuration.class).get("serverUrl"), user.getId());
		HttpGet get = new HttpGet(url);
		get.setHeader("accept", "application/json");
		
		HttpAsyncMethodExecutor<List<Sensor>> executor = new HttpAsyncMethodExecutor<List<Sensor>>(consumer) {
			@Override public List<Sensor> parseResponse(HttpEntity entity) {
				List<Sensor> result = null;
				try {
					String str = EntityUtils.toString(entity);
					Type type = new TypeToken<List<Sensor>>() {}.getType();
					result = new Gson().fromJson(str, type);
				} catch (NullPointerException | ParseException | IOException e) {
					e.printStackTrace();
				}
				return result;
			}
		};
		
		try {
			executor.fetch(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
