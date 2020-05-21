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
import com.magc.sensecane.model.domain.Measurement;
import com.magc.sensecane.model.domain.Sensor;
import com.magc.sensecane.model.domain.User;

public class SensorService {

	public static void getSensors(User user, Consumer<List<Sensor>> consumer) {
		try {
			String url = String.format("%s/users/%s/sensors/",
					Application.getInstance().get(Configuration.class).get("serverUrl"), user.getId());
			HttpGet get = new HttpGet(url);
			get.setHeader("accept", "application/json");

			HttpAsyncMethodExecutor<List<Sensor>> executor = new HttpAsyncMethodExecutor<List<Sensor>>(consumer) {
				@Override
				public List<Sensor> parseResponse(HttpEntity entity) {
					List<Sensor> result = null;
					try {
						String str = EntityUtils.toString(entity);
						Type type = new TypeToken<Map<Object, Sensor>>() {
						}.getType();
						Map<Object, Sensor> map = new Gson().fromJson(str, type);
						result = new ArrayList<Sensor>();
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

	public static void getSensorData(Sensor sensor, Consumer<List<Measurement>> consumer) {
		try {
			String url = String.format("%s/users/%s/sensors/%s/data/",
					Application.getInstance().get(Configuration.class).get("serverUrl"), sensor.getPatient(), sensor.getId());
			HttpGet get = new HttpGet(url);
			get.setHeader("accept", "application/json");

			HttpAsyncMethodExecutor<List<Measurement>> executor = new HttpAsyncMethodExecutor<List<Measurement>>(consumer) {
				@Override
				public List<Measurement> parseResponse(HttpEntity entity) {
					List<Measurement> result = null;
					try {
						String str = EntityUtils.toString(entity);
						Type type = new TypeToken<Map<Object, Measurement>>() {
						}.getType();
						Map<Object, Measurement> map = new Gson().fromJson(str, type);
						result = new ArrayList<Measurement>();
						result.addAll(map.values());
					} catch (NullPointerException | ParseException | IOException e) {
						e.printStackTrace();
					}
					return result;
				}
			};

			executor.fetch(get);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
