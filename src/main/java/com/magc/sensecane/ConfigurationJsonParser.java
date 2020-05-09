package com.magc.sensecane;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.container.DefaultContainer;
import com.magc.sensecane.framework.database.connection.factory.ConnectionFactory;
import com.magc.sensecane.framework.database.connection.pool.ConnectionPool;
import com.magc.sensecane.framework.database.connection.properties.ConnectionProperties;
import com.magc.sensecane.framework.javafx.controller.Controller;
import com.magc.sensecane.framework.javafx.controller.ControllerContainer;
import com.magc.sensecane.framework.utils.LoadResource;

public class ConfigurationJsonParser implements JsonDeserializer<Container>{
	
	private final Container container;
	
	public ConfigurationJsonParser() {
		this.container = new DefaultContainer() {};
	}
	
	public ConfigurationJsonParser(Container container) {
		this.container = container;
	}

	@Override
	public Container deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		
		if (obj.has("ui")) {
			JsonObject ui = obj.getAsJsonObject("ui");
			ControllerContainer controllerContainer = container.register(ControllerContainer.class, new ControllerContainer());
			
			for (String key : ui.keySet()) {
				try {
					Class<Controller> controller = (Class<Controller>) Class.forName(key);
					JsonObject temp = ui.getAsJsonObject(key);
					if (temp.has("implementation") && temp.has("fxml")) {
						URL url = container.get(LoadResource.class).execute(temp.get("fxml").getAsString()).toURL();
						Class impl = Class.forName(temp.get("implementation").getAsString());
						Constructor constructor = impl.getDeclaredConstructor(URL.class);
						controllerContainer.put(controller, (Controller) constructor.newInstance(url));
					}
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		if (obj.has("server")) {
			Configuration config = this.container.get(Configuration.class);
			config.put("serverUrl", obj.get("server").getAsString());
		}
		
		return container;
	}

}
