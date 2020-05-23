package com.magc.sensecane.component;

import java.net.URL;

public abstract class Builder {

	protected final URL fxml;
	protected final Class c;
	
	public Builder(Class c, URL fxml) {
		this.fxml = fxml;
		this.c = c;
	}
	
	public <T> T build() {
		T controller = null;
		try {
			controller = (T) c.getDeclaredConstructor(URL.class).newInstance(this.fxml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return controller;
	}

	public URL getFxml() {
		return fxml;
	}

	public Class<? extends ComponentController> getC() {
		return c;
	}
	
}
