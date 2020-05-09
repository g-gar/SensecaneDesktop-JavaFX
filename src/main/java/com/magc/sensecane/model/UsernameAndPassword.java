package com.magc.sensecane.model;

public class UsernameAndPassword {
	private final String username;
	private final String password;
	
	public UsernameAndPassword(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
