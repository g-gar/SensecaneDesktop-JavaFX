package com.magc.sensecane.service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.model.domain.Measurement;
import com.magc.sensecane.model.domain.Sensor;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.model.httpexecutor.MeasurementsHttpAsyncMethodExecutor;
import com.magc.sensecane.model.httpexecutor.SensorsHttpAsyncMethodExecutor;

public class SensorService {

	public static void getSensors(User user, Consumer<List<Sensor>> completed) {
		BiConsumer<SensorsHttpAsyncMethodExecutor, HttpResponse> onComplete = (executor, response) -> {
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				completed.accept(executor.parseResponse(response));
				break;
			default:
				LoggerService.notify(String.format("[%s]: %s", response.getStatusLine().getStatusCode(), response.getEntity().toString()));
			}
		};
		HttpGet get = new HttpService.GET()
				.addHeader(new BasicHeader("Content-Type", "application/json"))
				.build(String.format("%s/users/%s/sensors/", Application.getInstance().get(Configuration.class).get("serverUrl"), user.getId()));
		HttpService.request(get, SensorsHttpAsyncMethodExecutor.class, onComplete);
	}

	public static void getSensorData(Sensor sensor, Consumer<List<Measurement>> completed) {
		BiConsumer<MeasurementsHttpAsyncMethodExecutor, HttpResponse> onComplete = (executor, response) -> {
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				completed.accept(executor.parseResponse(response));
				break;
			default:
				LoggerService.notify(String.format("[%s]: %s", response.getStatusLine().getStatusCode(), response.getEntity().toString()));
			}
		};
		HttpGet get = new HttpService.GET()
				.addHeader(new BasicHeader("Content-Type", "application/json"))
				.build(String.format("%s/users/%s/sensors/%s/data/", Application.getInstance().get(Configuration.class).get("serverUrl"), sensor.getPatient(), sensor.getId()));
		HttpService.request(get, MeasurementsHttpAsyncMethodExecutor.class, onComplete);
	}
}
