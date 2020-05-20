package com.magc.sensecane.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	public static void getUsers(Consumer<List<User>> consumer) {
		try {
			Configuration conf = Application.getInstance().get(Configuration.class);

			String url = (String) conf.get("serverUrl") + "/users/";
			HttpGet get = new HttpGet(url);
			get.setHeader("accept", "application/json");

			HttpAsyncMethodExecutor<List<User>> executor = new HttpAsyncMethodExecutor<List<User>>(consumer) {
				@Override
				public List<User> parseResponse(HttpEntity entity) {
					List<User> result = null;
					try {
						String str = EntityUtils.toString(entity);
						Type type = new TypeToken<Map<Object, User>>() {}.getType();
						Map<Object, User> map = new Gson().fromJson(str, type);
						result = new ArrayList<User>();
						result.addAll(map.values());
					} catch (NullPointerException | ParseException | IOException e) {
						e.printStackTrace();
					}
					return result;
				}
			};
			executor.fetch(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
