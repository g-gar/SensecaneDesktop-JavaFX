package com.magc.sensecane.model.httpexecutor;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.magc.sensecane.framework.http.HttpAsyncMethodExecutor;
import com.magc.sensecane.model.domain.User;

public class UserHttpAsyncMethodExecutor extends HttpAsyncMethodExecutor<User> {

	@Override
	public User parseResponse(HttpResponse response) {
		User result = null;
		try {
			String str = EntityUtils.toString(response.getEntity());
			Type type = new TypeToken<User>() {}.getType();
			result = new Gson().fromJson(str, type);
		} catch (NullPointerException | ParseException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
