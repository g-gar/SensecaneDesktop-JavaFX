package com.magc.sensecane.model.domain;

import com.magc.sensecane.framework.model.BaseEntity;

public class Measurement extends BaseEntity {

	private final Integer id;
	private final Integer sensor;
	private final Long timestamp;
	private final Double value;
	
	public Measurement(Integer id, Integer sensor, Long timestamp, Double value) {
		this.id = id;
		this.sensor = sensor;
		this.timestamp = timestamp;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public Integer getSensor() {
		return sensor;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public Double getValue() {
		return value;
	}
	
}
