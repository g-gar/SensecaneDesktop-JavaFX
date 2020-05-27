package com.magc.sensecane.model.domain;

import com.magc.sensecane.framework.model.BaseEntity;

public class User extends BaseEntity {

	private final Integer id;
	private final String username;
	private final String dni;
	private final String firstName;
	private final String lastName;
	private final String type;
	private final String token;
	
	public User(Integer id, String username, String dni, String firstName, String lastName, String type) {
		this(id, username, dni, firstName, lastName, type, null);
	}
	
	public User(Integer id, String username, String dni, String firstName, String lastName, String type, String token) {
		this.id = id;
		this.username = username;
		this.dni = dni;
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type;
		this.token = token;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getDni() {
		return dni;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getType() {
		return type;
	}
	
	public String getToken() {
		return token;
	}
}
