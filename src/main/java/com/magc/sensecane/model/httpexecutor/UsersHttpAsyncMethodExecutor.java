package com.magc.sensecane.model.httpexecutor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.magc.sensecane.framework.http.HttpAsyncMethodExecutor;
import com.magc.sensecane.model.domain.User;

public class UsersHttpAsyncMethodExecutor extends HttpAsyncMethodExecutor<List<User>> {

	@Override
	public List<User> parseResponse(HttpResponse response) {
		List<User> result = null;
		try {
			String str = EntityUtils.toString(response.getEntity());
			Type type = new TypeToken<Map<Object, User>>() {}.getType();
			Map<Object, User> map = new Gson().fromJson(str, type);
			result = new ArrayList<User>();
			result.addAll(map.values());
		} catch (NullPointerException | ParseException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
