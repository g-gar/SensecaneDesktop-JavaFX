package com.magc.sensecane.component;

import java.net.URL;

public abstract class Builder {

	protected final URL fxml;
	protected final Class<? extends ComponentController> c;
	
	public Builder(Class<? extends ComponentController> c, URL fxml) {
		this.fxml = fxml;
		this.c = c;
	}
	
	public <T extends ComponentController> T build() {
		T controller = null;
		try {
			controller = (T) c.getDeclaredConstructor(URL.class).newInstance(this.fxml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return controller;
	}
}
