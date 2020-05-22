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
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.http.HttpAsyncMethodExecutor;
import com.magc.sensecane.model.domain.Message;
import com.magc.sensecane.model.domain.User;

public class MessageService {

	public static void getMessages(User from, User to, Consumer<List<Message>> consumer) {
		try {
			String url = String.format("%s/users/%s/messages/", Application.getInstance().get(Configuration.class).get("serverUrl"), from.getId());
			HttpGet get = HttpService.GET.build(url, new BasicHeader("Accept", "application/json"));
			
			HttpAsyncMethodExecutor<List<Message>> executor = new HttpAsyncMethodExecutor<List<Message>>(consumer) {
				@Override
				public List<Message> parseResponse(HttpEntity entity) {
					List<Message> result = null;
					try {
						String str = EntityUtils.toString(entity);
						Type type = new TypeToken<Map<Object, Message>>() {}.getType();
						Map<Object, Message> map = new Gson().fromJson(str, type);
						result = new ArrayList<Message>();
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
