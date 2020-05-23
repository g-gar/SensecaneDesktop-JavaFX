package com.magc.sensecane;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.magc.sensecane.component.BuilderContainer;
import com.magc.sensecane.controller.LoginController;
import com.magc.sensecane.controller.component.MessageComponent;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.javafx.JavaFxApplication;
import com.magc.sensecane.framework.utils.LoadResource;
import com.magc.sensecane.util.ChangeView;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Application extends JavaFxApplication {
	
	private static Application instance;
	public static Stage stage;
	
	public static Application getInstance() {
		if (Application.instance == null) {
			Application.instance = new Application();
		}
		return Application.instance;
	}
	
	public Application() {
		try {
			Application.this.register(new Configuration());
			
			LoadResource loadresource = new LoadResource();
			Application.this.register(loadresource);
			
			new GsonBuilder().setPrettyPrinting()
					.registerTypeAdapter(Container.class, new ConfigurationJsonParser(Application.this)).create()
					.fromJson(new FileReader(loadresource.execute("json/sensecane.json")), Container.class);
			
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		this.stage.getIcons().add(new Image("file:" + get(LoadResource.class).execute("img/Logo.png").toString()));
		primaryStage.setTitle("MAGC - Sensecane");
		ChangeView.execute(LoginController.class);
		
	}

	public static void main(String[] args) {
		Application app = Application.getInstance();
		app.launch(args);
	}

	@Override
	public void stop() throws Exception {
		System.exit(0);
	}
	
}
