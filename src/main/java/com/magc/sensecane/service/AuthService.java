package com.magc.sensecane.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.magc.sensecane.Application;
import com.magc.sensecane.Configuration;
import com.magc.sensecane.framework.http.HttpAsyncMethodExecutor;
import com.magc.sensecane.model.UsernameAndPassword;
import com.magc.sensecane.model.domain.User;
import com.magc.sensecane.util.HttpUtil;

public class AuthService {

	public static void authenticate(String username, String password, Consumer<User> onComplete) {
		User result = null;
		try {
			Configuration conf = Application.getInstance().get(Configuration.class);

			String url = (String) conf.get("serverUrl") + "/login/";
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-Type", "application/json");
			post.setEntity(new StringEntity(HttpUtil.jsonify( new UsernameAndPassword(username, password) )));
			
			HttpAsyncMethodExecutor<User> executor = new HttpAsyncMethodExecutor<User>(onComplete) {
				@Override
				public User parseResponse(HttpEntity entity) {
					User result = null;
					try {
						String str = EntityUtils.toString(entity);
						Type type = new TypeToken<User>() {}.getType();
						result = new Gson().fromJson(str, type);
					} catch (NullPointerException | ParseException | IOException e) {
						e.printStackTrace();
					}
					return result;
				}
			};
			executor.fetch(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


