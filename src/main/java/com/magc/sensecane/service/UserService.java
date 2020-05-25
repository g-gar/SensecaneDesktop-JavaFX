package com.magc.sensecane.service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.model.json.PreSerializedJson;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.model.httpexecutor.UserHttpAsyncMethodExecutor;
import com.magc.sensecane.model.httpexecutor.UsersHttpAsyncMethodExecutor;

public class UserService {

	public static void getUsers(Consumer<List<User>> completed) {
		BiConsumer<UsersHttpAsyncMethodExecutor, HttpResponse> onComplete = (executor, response) -> {
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
				.build(String.format("%s/users/", (String) Application.getInstance().get(Configuration.class).get("serverUrl")));
		HttpService.request(get, UsersHttpAsyncMethodExecutor.class, onComplete);
	}

	public static void getRelatedUsers(User user, Consumer<List<User>> completed) {
		BiConsumer<UsersHttpAsyncMethodExecutor, HttpResponse> onComplete = (executor, response) -> {
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
				.build(String.format("%s/users/%s/related/", (String) Application.getInstance().get(Configuration.class).get("serverUrl"), user.getId()));
		HttpService.request(get, UsersHttpAsyncMethodExecutor.class, onComplete);
	}
	
	public static void createUser(User user, String password, Consumer<User> completed, Runnable error) {
		UserService.createUser(user.getUsername(), password, user.getDni(), user.getFirstName(), user.getLastName(), user.getType(), completed, error);
	}
	
	public static void createUser(String username, String password, String dni, String firstName, String lastName, String type, Consumer<User> completed, Runnable error) {
		Configuration conf = Application.getInstance().get(Configuration.class);
		String u = username, p = password, d = dni, f = firstName, l = lastName, t = type;
		Object ua = new Object() {
			String username = u;
			String password = p;
			String dni = d;
			String firstName = f;
			String lastName = l;
			String type = t;
		};
		PreSerializedJson<Object> data = new PreSerializedJson<Object>(ua, "username", "password", "dni", "firstName", "lastName", "type");
		BiConsumer<UserHttpAsyncMethodExecutor, HttpResponse> onComplete = (executor, response) -> {
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				completed.accept(executor.parseResponse(response));
				break;
			case 404:
				error.run();
				LoggerService.notifyError("Invalid information");
				break;
			default:
				LoggerService.notify(String.format("[%s]: %s", response.getStatusLine().getStatusCode(), response.getEntity().toString()));
			}
		};
		HttpPost post = new HttpService.POST()
				.addHeader(new BasicHeader("Content-Type", "application/json"))
				.build(String.format("%s/users/", (String) conf.get("serverUrl")), data);
		HttpService.request(post, UserHttpAsyncMethodExecutor.class, onComplete);
	}
	
	public static void updateUser(User user, String password, Consumer<User> completed, Runnable error) {
		UserService.updateUser(user.getId(), user.getUsername(), password, user.getDni(), user.getFirstName(), user.getLastName(), user.getType(), completed, error);
	}
	
	public static void updateUser(Integer id, String username, String password, String dni, String firstName, String lastName, String type, Consumer<User> completed, Runnable error) {
		Configuration conf = Application.getInstance().get(Configuration.class);
		Integer i = id;
		String u = username, p = password, d = dni, f = firstName, l = lastName, t = type;
		Object ua = new Object() {
			Integer id = i;
			String username = u;
			String password = p;
			String dni = d;
			String firstName = f;
			String lastName = l;
			String type = t;
		};
		PreSerializedJson<Object> data = new PreSerializedJson<Object>(ua, "id", "username", "password", "dni", "firstName", "lastName", "type");
		BiConsumer<UserHttpAsyncMethodExecutor, HttpResponse> onComplete = (executor, response) -> {
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				completed.accept(executor.parseResponse(response));
				break;
			case 404:
				error.run();
				LoggerService.notifyError("Invalid information");
				break;
			default:
				LoggerService.notify(String.format("[%s]: %s", response.getStatusLine().getStatusCode(), response.getEntity().toString()));
			}
		};
		HttpPost post = new HttpService.POST()
				.addHeader(new BasicHeader("Content-Type", "application/json"))
				.build(String.format("%s/users/", (String) conf.get("serverUrl")), data);
		HttpService.request(post, UserHttpAsyncMethodExecutor.class, onComplete);
	}
	
	public static void relateUserWith(Integer who, Integer with, Consumer<User> complete) {
		
	}
}
