package com.magc.sensecane.component;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

public abstract class ComponentController extends HBox {
	
	protected URL fxml;
	protected FXMLLoader loader;
	protected Scene scene;
	
	public ComponentController(URL fxml) {
		this.fxml = fxml;
		this.loader = new FXMLLoader(fxml);
		this.loader.setRoot(this);
		this.loader.setController(this);
		
		HBox root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.scene = new Scene(root);
		}
	}
	
	public Scene get() {
		return scene;
	}

	public abstract void start(Object... params);
	public abstract void destroy();
	
}
