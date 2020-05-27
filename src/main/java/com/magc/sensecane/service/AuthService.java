package com.magc.sensecane.service;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.model.json.PreSerializedJson;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.model.httpexecutor.UserHttpAsyncMethodExecutor;

public class AuthService {

	public static void authenticate(String username, String password, Consumer<User> completed, Runnable error) {
		Configuration conf = Application.getInstance().get(Configuration.class);
		User user = (User) conf.get("user");
		String u = username, p = password;
		Object ua = new Object() {
			String username = u;
			String password = p;
		};
		BiConsumer<UserHttpAsyncMethodExecutor, HttpResponse> onComplete = (executor, response) -> {
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				completed.accept(executor.parseResponse(response));
				break;
			case 404:
				error.run();
				LoggerService.notifyError("Invalid username or password");
				break;
			default:
				LoggerService.notify(String.format("[%s]: %s", response.getStatusLine().getStatusCode(), response.getEntity().toString()));
			}
		};
		HttpPost post = new HttpService.POST()
				.addHeader(new BasicHeader("Content-Type", "application/json"))
				.build(String.format("%s/login/", (String) conf.get("serverUrl")), new PreSerializedJson<Object>(ua, "username", "password"));
		HttpService.request(post, UserHttpAsyncMethodExecutor.class, onComplete);
	}
}


