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
import com.magc.sensecane.model.domain.Message;

public class MessagesHttpAsyncMethodExecutor extends HttpAsyncMethodExecutor<List<Message>> {

	@Override
	public List<Message> parseResponse(HttpResponse response) {
		List<Message> result = null;
		try {
			String str = EntityUtils.toString(response.getEntity());
			Type type = new TypeToken<Map<Object, Message>>() {}.getType();
			Map<Object, Message> map = new Gson().fromJson(str, type);
			result = new ArrayList<Message>();
			result.addAll(map.values());
		} catch (NullPointerException | ParseException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
