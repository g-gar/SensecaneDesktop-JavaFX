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
import com.magc.sensecane.model.domain.Measurement;

public class MeasurementsHttpAsyncMethodExecutor extends HttpAsyncMethodExecutor<List<Measurement>> {

	@Override
	public List<Measurement> parseResponse(HttpResponse response) {
		List<Measurement> result = null;
		try {
			String str = EntityUtils.toString(response.getEntity());
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

}
