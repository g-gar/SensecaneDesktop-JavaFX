package com.magc.sensecane.model.json;

import java.util.List;
import java.util.Map;

import com.magc.sensecane.model.AbstractJsonDeserializer;
import com.magc.sensecane.model.domain.Sensor;

public class SensorsDeserializer extends AbstractJsonDeserializer<List<Sensor>> {

	@Override
	public List<Sensor> deserialize(Map<String, Object> map) {
//		return map.values().stream().map(e -> new SensorDeserializer().deserialize(e, typeOfT, context));
		return null;
	}

}
