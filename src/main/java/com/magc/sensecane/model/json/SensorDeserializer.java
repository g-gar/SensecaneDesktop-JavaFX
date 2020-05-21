package com.magc.sensecane.model.json;

import java.util.Map;

import com.magc.sensecane.model.AbstractJsonDeserializer;
import com.magc.sensecane.model.domain.Sensor;

public class SensorDeserializer extends AbstractJsonDeserializer<Sensor> {

	@Override
	public Sensor deserialize(Map<String, Object> map) {
		return new Sensor(
			this.<Integer>get(map, "id"),
			this.get(map, "patient"),
			this.get(map, "name")
		);
	}

}
