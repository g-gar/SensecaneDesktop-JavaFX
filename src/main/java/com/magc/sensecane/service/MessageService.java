package com.magc.sensecane.service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;

import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.model.domain.Message;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.model.httpexecutor.MessagesHttpAsyncMethodExecutor;

public class MessageService {

	public static void getMessages(User from, User to, Consumer<List<Message>> completed) {
		BiConsumer<MessagesHttpAsyncMethodExecutor, HttpResponse> onComplete = (executor, response) -> {
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				completed.accept(executor.parseResponse(response));
				break;
			default:
				LoggerService.notify(String.format("[%s]: %s", response.getStatusLine().getStatusCode(), response.getEntity().toString()));
			}
		};
		HttpGet get = new HttpService.GET()
				.addHeader(new BasicHeader("Accept", "application/json"))
				.addHeader(new BasicHeader("Content-Type", "application/json"))
				.build(String.format("%s/users/%s/messages/", Application.getInstance().get(Configuration.class).get("serverUrl"), from.getId()));
		HttpService.request(get, MessagesHttpAsyncMethodExecutor.class, onComplete);
	}
	
	public static void sendMessage() {
		
	}
}
