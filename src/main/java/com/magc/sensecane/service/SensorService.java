package com.magc.sensecane.service;

import java.io.IOException;
import java.lang.reflect.Type;
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

public class SensorService {

	public static void getSensors(User user, Consumer<List<Sensor>> consumer) {
		String url = String.format("%s/users/%s/sensors/", Application.getInstance().get(Configuration.class).get("serverUrl"), user.getId());
		HttpGet get = new HttpGet(url);
		get.setHeader("accept", "application/json");

		HttpAsyncMethodExecutor<List<Sensor>> executor = new HttpAsyncMethodExecutor<List<Sensor>>(consumer) {
			@Override
			public List<Sensor> parseResponse(HttpEntity entity) {
				List<Sensor> result = null;
				try {
					String str = EntityUtils.toString(entity);
					System.out.println(str);
					Type type = new TypeToken<Map<?, Sensor>>() {}.getType();
					result = (List<Sensor>) ((Map<?, Sensor>) new Gson().fromJson(str, type)).values();
					System.out.println(result);
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
