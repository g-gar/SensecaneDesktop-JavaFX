package com.magc.sensecane.model.domain;

import com.magc.sensecane.framework.model.BaseEntity;

public class Sensor extends BaseEntity {

	private final Integer id;
	private final Integer patient;
	private final String name;
	
	public Sensor(Integer id, Integer patient, String name) {
		super();
		this.id = id;
		this.patient = patient;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getPatient() {
		return this.patient;
	}
	
	public String getName() {
		return name;
	}
	
}
